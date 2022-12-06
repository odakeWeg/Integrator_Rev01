package weg.net.tester.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.exception.DataBaseException;
import weg.net.tester.exception.InlineException;
import weg.net.tester.exception.SapException;
import weg.net.tester.exception.TestUnmarshalingException;
import weg.net.tester.facade.datacenter.DataCenter;
import weg.net.tester.models.CommandLog;
import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.models.TestingRoutine;
import weg.net.tester.tag.TagList;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.FilePathUtil;
import weg.net.tester.utils.FrontEndFeedbackUtil;
import weg.net.tester.utils.SapCaracUtil;
import weg.net.tester.utils.SessionUtil;

@Getter
@Setter
@Configuration
public class TestExecutor {
    @Autowired
    private DataCenter dataCenter;
    private SimpMessagingTemplate template;
    private String barCode;
    private String[] result;
    TagList baseTagList;

    //@Todo: Make it multithreaded transform in array (barcode, initialization, results, endSetup, etc)
    public void execute() {
        String initSetupStatus = initSetup();
        if(initSetupStatus.equals(FrontEndFeedbackUtil.OK)) {
            this.result = startTestingRoutine();
            sendFeedbackAfter(this.result[0], true);   
        } else {
            sendFeedbackAfter(initSetupStatus, false);
        }
        endSetup();
    }

    private void sendFeedbackAfter(String result, boolean finished) {
        CommandLog commandLog = new CommandLog(result, ActionCommandUtil.SHOW_FINAL_RESULT, finished);
        this.template.convertAndSend("/feedback2",  commandLog);
    }   

    //@Todo: Maybe create sendFeedbackMiddle method
    private void sendFeedbackBefore(String descricao) {
        CommandLog commandLog = new CommandLog(descricao, ActionCommandUtil.STARTING_INFO);
        this.template.convertAndSend("/feedback2",  commandLog);
    }

    private void sendProductDescriptionFeedback() {
        sendFeedbackBefore("Serial: " + TestMetaDataModel.sapConnector.get(SapCaracUtil.SERIAL));
        sendFeedbackBefore("Material: " + TestMetaDataModel.sapConnector.get(SapCaracUtil.MATERIAL));
        sendFeedbackBefore("Produto: " + TestMetaDataModel.sapConnector.get(SapCaracUtil.REF_PRODUTO_AUTOMACAO));
        sendFeedbackBefore("Descricao: " + TestMetaDataModel.sapConnector.get(SapCaracUtil.SHORT_TEXT));
    }

    //@Todo: This method should be uncommented for production
    private String initSetup() {
        //@Todo: refactor into different functions with catches, this one just calls every other
        try {
            SessionUtil.initiateTest();
            sendFeedbackBefore("Iniciando...");
            this.dataCenter.initiate(barCode);
            TestingRoutine testingRoutine = getList();
            this.baseTagList = testingRoutine.getRoutine();
            this.dataCenter.getMongoConnector().initialSetup();
            this.dataCenter.getInlineConnector().saveInitialEvent();
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
        } catch (Exception e) {
            return FrontEndFeedbackUtil.ERRO_INESPERADO;
        }
    }
    /* 
    private String initSetup() {
        try {
            sendFeedbackBefore("Iniciando...");
            this.dataCenter.initiate(barCode);
            TestingRoutine testingRoutine = getList();
            this.baseTagList = testingRoutine.getRoutine();
            this.dataCenter.getMongoConnector().initialSetup();
            this.setTestMetaDataModel();
            sendProductDescriptionFeedback();
            return FrontEndFeedbackUtil.OK;
        } catch (Exception e) {
            return FrontEndFeedbackUtil.ERRO_INESPERADO;
        }
    }
    */

    private void setTestMetaDataModel() {
        TestMetaDataModel refreshStaticVariable = new TestMetaDataModel(baseTagList.qntOfProductInTest());
        TestMetaDataModel.tagList = baseTagList; 
        TestMetaDataModel.template = this.template;
        TestMetaDataModel.sapConnector = this.dataCenter.getSapConnector().getSapDataMap();
    }

    private void endSetup() {
        //closingTestSetup get error step and run a cancelling test 
        //routine (set log -> maybe changed to tags iself), save 
        //data and upload session and stuff
        //database and Session (increment from executed tests)
        try {
            SessionUtil.endTest(result[0]);
            saveInlineEnd();
            this.dataCenter.getMongoConnector().endingSetup(result[0], TestMetaDataModel.testStep[0], TestMetaDataModel.tagList);
        } catch (/*JsonProcessingException*/ Exception e) {
            sendFeedbackAfter(FrontEndFeedbackUtil.FALHA_NA_FINALIZACAO, false);
        }
    }

    private void saveInlineEnd() throws InlineException {
        if (result[0].equals(FrontEndFeedbackUtil.OK)) {
            this.dataCenter.getInlineConnector().saveApprovalEvent();
        } else {
            if (result[0].equals(FrontEndFeedbackUtil.CANCELADO)) {
                this.dataCenter.getInlineConnector().saveCancelEvent(result[0]);
            } else {
                this.dataCenter.getInlineConnector().saveRepprovalEvent(result[0]);
            }
        }
    }

    /* 
    private void endSetup() {
        //closingTestSetup get error step and run a cancelling test 
        //routine (set log -> maybe changed to tags iself), save 
        //data and upload session and stuff
        //database and Session (increment from executed tests)
        try {
            SessionUtil.endTest(result[0]);
            this.dataCenter.getMongoConnector().endingSetup(result[0], TestMetaDataModel.testStep[0], TestMetaDataModel.tagList);
        } catch (Exception e) {
            sendFeedbackAfter(FrontEndFeedbackUtil.FALHA_NA_FINALIZACAO, false);
        }
    }
    */

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
                if (TestMetaDataModel.isPositionEnabled[c]) {
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

    private TestingRoutine getList() {
        String fileReferenceName = this.dataCenter.getSapConnector().getSapDataMap().get(SapCaracUtil.SHORT_TEXT);
        return new TestingRoutine(FilePathUtil.TEST_ROUTINE_PATH + fileReferenceName + ".json");
    }

    @MessageMapping("/stop")
    public void onReceivedMesage(boolean confirmation) {
        TestMetaDataModel.exitFlag = true;
    }
}
