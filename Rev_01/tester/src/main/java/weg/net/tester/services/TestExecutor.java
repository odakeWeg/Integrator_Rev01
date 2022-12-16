package weg.net.tester.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.exception.DataBaseException;
import weg.net.tester.exception.EnsException;
import weg.net.tester.exception.InlineException;
import weg.net.tester.exception.SapException;
import weg.net.tester.exception.TestSetupException;
import weg.net.tester.exception.TestUnmarshalingException;
import weg.net.tester.facade.DataCenter;
import weg.net.tester.models.web.CommandLog;
import weg.net.tester.tag.TagList;
import weg.net.tester.tag.TestMetaDataModel;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.FrontEndFeedbackUtil;
import weg.net.tester.utils.SapCaracUtil;
import weg.net.tester.utils.SessionUtil;

@Getter
@Setter
@Configuration
@Controller
public class TestExecutor {
    //@Todo: Exclude every hard coded value or String
    @Autowired
    private DataCenter dataCenter;
    private SimpMessagingTemplate template;
    private String[] barCode;
    private String[] result;
    TagList baseTagList;

    public void execute() {
        String initSetupStatus = initSetup();
        if(initSetupStatus.equals(FrontEndFeedbackUtil.OK)) {
            this.result = startTestingRoutine();
            for (int i = 1; i <= this.result.length; i++) {
                sendFeedbackAfter(this.result[0], true, i);   
            }
        } else {
            sendFeedbackAfter(initSetupStatus, false, 1);
        }
        endSetup();
    }

    //@Todo: "/feedback2" must become "/feedback", the functionality will be implemented in the angular environment
    private void sendFeedbackAfter(String result, boolean finished, int position) {
        CommandLog commandLog = new CommandLog(result, ActionCommandUtil.SHOW_FINAL_RESULT, finished, position);
        this.template.convertAndSend("/feedback2",  commandLog);
    }   

    //@Todo: Maybe create sendFeedbackMiddle method
    private void sendFeedbackBefore(String descricao, int position) {
        CommandLog commandLog = new CommandLog(descricao, ActionCommandUtil.STARTING_INFO, position);
        this.template.convertAndSend("/feedback2",  commandLog);
    }

    //@Todo: This function should be in another class
    private void sendProductDescriptionFeedback() {
        for (int position = 0; position < TestMetaDataModel.tagList.qntOfProductInTest(); position++) {
            sendFeedbackBefore("Serial: " + TestMetaDataModel.sapConnector.get(position).get(SapCaracUtil.SERIAL), position);
            sendFeedbackBefore("Material: " + TestMetaDataModel.sapConnector.get(position).get(SapCaracUtil.MATERIAL), position);
            sendFeedbackBefore("Produto: " + TestMetaDataModel.sapConnector.get(position).get(SapCaracUtil.REF_PRODUTO_AUTOMACAO), position);
            sendFeedbackBefore("Descricao: " + TestMetaDataModel.sapConnector.get(position).get(SapCaracUtil.SHORT_TEXT), position);
        }
    }

    private String initSetup() {
        //@Todo: refactor into different functions with catches, this one just calls every other
        try {
            SessionUtil.initiateTest();
            //sendFeedbackBefore("Iniciando..."); //@Todo: Change this to a rolling circle to feedback the user, doesnt need a position
            this.baseTagList = this.dataCenter.initiate(barCode);
            this.setTestMetaDataModel();
            sendProductDescriptionFeedback();
            if (this.dataCenter.getInlineConnector().isTestAllowed()) {
                return FrontEndFeedbackUtil.OK;
            } else {
                return FrontEndFeedbackUtil.TESTE_NAO_AUTORIZADO;
            }
        } catch (SapException e) {
            return FrontEndFeedbackUtil.SAP_ERROR;
        } catch (InlineException e) {
            return FrontEndFeedbackUtil.INLINE_ERROR;
        } catch (DataBaseException e) {
            return FrontEndFeedbackUtil.DATABASE_ERROR;
        } catch (TestUnmarshalingException e) {
            return FrontEndFeedbackUtil.ERRO_LOCALIZACAO_ROTINA;
        } catch (TestSetupException e) {
            return FrontEndFeedbackUtil.FALHA_SETUP_PRODUTO;
        } catch (Exception e) {
            return FrontEndFeedbackUtil.ERRO_INESPERADO;
        }
    }

    private void setTestMetaDataModel() {
        TestMetaDataModel refreshStaticVariable = new TestMetaDataModel(baseTagList.qntOfProductInTest());
        TestMetaDataModel.tagList = baseTagList; 
        TestMetaDataModel.template = this.template;
        TestMetaDataModel.sapConnector = this.dataCenter.getSapConnector().getSapDataMap();
    }

    private void endSetup() {
        try {
            SessionUtil.endTest(result);
            this.dataCenter.end(result);
        } catch (InlineException e) {
            sendFeedbackAfter(FrontEndFeedbackUtil.INLINE_ERROR, false, 1);
        } catch (DataBaseException e) {
            sendFeedbackAfter(FrontEndFeedbackUtil.DATABASE_ERROR, false, 1);
        } catch (EnsException e) {
            sendFeedbackAfter(FrontEndFeedbackUtil.ENS_ERROR, false, 1);
        } catch (Exception e) {
            sendFeedbackAfter(FrontEndFeedbackUtil.ERRO_INESPERADO, false, 1);
        }
    }

    private String[] startTestingRoutine() { 
        String[] results = new String[baseTagList.qntOfProductInTest()];
        try {
            for (int i = 0; i < baseTagList.getList().size(); i++) {
                if (TestMetaDataModel.exitFlag) {
                    Arrays.fill(results, FrontEndFeedbackUtil.CANCELADO);
                    return results;
                } else {
                    CommandLog commandLog = baseTagList.getList().get(i).command();
                    this.template.convertAndSend("/feedback2",  commandLog);
                }
            }
            for (int c = 0; c < baseTagList.qntOfProductInTest(); c++) {
                if (TestMetaDataModel.isPositionEnabled[c].get()) {
                    results[c] = FrontEndFeedbackUtil.OK;
                } else {
                    results[c] = FrontEndFeedbackUtil.FALHA_NO_TESTE;
                }
            }
        } catch (Exception e) {
            Arrays.fill(results, FrontEndFeedbackUtil.ERRO_INESPERADO);
        }
        return results;
    }

    @MessageMapping("/stop")
    public void stopRoutine() {
        TestMetaDataModel.exitFlag = true;
    }
}
