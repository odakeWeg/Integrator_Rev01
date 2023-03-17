package weg.net.tester.services;

import java.util.Arrays;
import java.util.HashMap;

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
import weg.net.tester.exception.SessionException;
import weg.net.tester.exception.TestSetupException;
import weg.net.tester.exception.TestUnmarshalingException;
import weg.net.tester.facade.DataCenter;
import weg.net.tester.helper.SessionHelper;
import weg.net.tester.models.web.CommandLog;
import weg.net.tester.models.web.ProductLog;
import weg.net.tester.models.web.ResultLog;
import weg.net.tester.tag.TagList;
import weg.net.tester.tag.TestMetaDataModel;
import weg.net.tester.utils.EndPointPathUtil;
import weg.net.tester.utils.FrontEndFeedbackUtil;
import weg.net.tester.utils.InlineFeddbackUtil;
import weg.net.tester.utils.SapCaracUtil;
import weg.net.tester.utils.TestStatusUtil;

@Getter
@Setter
@Configuration
@Controller
public class TestExecutor {
    //@Todo: Verificar se e pq aparece Erro inesperado no final dos testes (não parece atrapalhar nos logs)
    //@Todo: Exclude every hard coded value or String
    @Autowired
    private DataCenter dataCenter;
    private SimpMessagingTemplate template;
    private String[] barCode;
    private String[] result;
    private String[] log;
    TagList baseTagList;

    public void execute() {
        boolean initSetupFail = false;
        String initSetupStatus = initSetup();
        if(initSetupStatus.equals(FrontEndFeedbackUtil.OK)) {
            HashMap<String, String[]> map = startTestingRoutine();
            this.result = map.get("results");
            this.log = map.get("log");
            for (int i = 0; i < this.result.length; i++) {
                if(this.result[i].equals(FrontEndFeedbackUtil.OK)) {
                    sendFeedbackAfter(this.result[i], log[i], true, TestStatusUtil.OK,i+1);   
                } else {
                    sendFeedbackAfter(this.result[i], log[i], true, TestStatusUtil.FAULT,i+1);  
                }
            }
        } else {    //@Todo: Different feedback because positions werent even setted
            sendFeedbackAfter(initSetupStatus, "", false, TestStatusUtil.INITIALIZATION_FAULT, 0);
            
            //@Todo: Refactor this fillNull
            fillNullVariables();
            initSetupFail = true;
        }
        if(endSetup(initSetupFail) && !initSetupFail) {
            sendFeedbackAfter("", "", true, TestStatusUtil.FIM, 0);
        }
    }

    private void fillNullVariables() {
        this.result = new String[this.barCode.length];
        Arrays.fill(result, TestStatusUtil.INITIALIZATION_FAULT);
        //TestMetaDataModel.tagList = new TagList();
        //TestMetaDataModel.testStep = new int[this.barCode.length];
    }

    //@Todo: "/feedback2" must become "/feedback", the functionality will be implemented in the angular environment
    /* 
    private void sendFeedbackAfter(String result, boolean finished, int position) {
        CommandLog commandLog = new CommandLog(result, ActionCommandUtil.SHOW_FINAL_RESULT, finished, position);
        this.template.convertAndSend("/feedback2",  commandLog);
    }
    */   

    private void sendFeedbackAfter(String result, String log, boolean finished, String status, int position) {
        ResultLog resultLog = new ResultLog(result, log, finished, status, position);
        this.template.convertAndSend(EndPointPathUtil.CHANNEL,  resultLog);
    } 

    //@Todo: Maybe create sendFeedbackMiddle method
    /* 
    private void sendFeedbackBefore(String descricao, int position) {
        CommandLog commandLog = new CommandLog(descricao, ActionCommandUtil.STARTING_INFO, position);
        this.template.convertAndSend("/feedback2",  commandLog);
    }

    //@Todo: This function should be in another class
    private void sendProductDescriptionFeedback() {
        for (int position = 0; position < TestMetaDataModel.tagList.qntOfProductInTest(); position++) {
            sendFeedbackBefore("Serial: " + TestMetaDataModel.sapConnector.get(position).get(SapCaracUtil.SERIAL), position+1);
            sendFeedbackBefore("Material: " + TestMetaDataModel.sapConnector.get(position).get(SapCaracUtil.MATERIAL), position+1);
            sendFeedbackBefore("Produto: " + TestMetaDataModel.sapConnector.get(position).get(SapCaracUtil.REF_PRODUTO_AUTOMACAO), position+1);
            sendFeedbackBefore("Descricao: " + TestMetaDataModel.sapConnector.get(position).get(SapCaracUtil.SHORT_TEXT), position+1);
        }
    }
    */

    private void sendProductDescriptionFeedback() {
        ProductLog productLog;
        for (int position = 0; position < TestMetaDataModel.tagList.qntOfProductInTest(); position++) {
            productLog = new ProductLog();
            productLog.setSerial(TestMetaDataModel.sapConnector.get(position).get(SapCaracUtil.SERIAL));
            productLog.setMaterial(TestMetaDataModel.sapConnector.get(position).get(SapCaracUtil.MATERIAL));
            productLog.setProduto(TestMetaDataModel.sapConnector.get(position).get(SapCaracUtil.REF_PRODUTO_AUTOMACAO));
            productLog.setDescricao(TestMetaDataModel.sapConnector.get(position).get(SapCaracUtil.SHORT_TEXT));
            productLog.setPosition(position+1);

            this.template.convertAndSend(EndPointPathUtil.CHANNEL, productLog);

            ResultLog resultLog = new ResultLog(FrontEndFeedbackUtil.ONGOING, "@Todo", false, TestStatusUtil.ON_TEST, position+1); //@Todo: maybe make an Util for this "Em andamento"
            this.template.convertAndSend(EndPointPathUtil.CHANNEL,  resultLog);
        }
    }

    private String initSetup() {
        //@Todo: refactor into different functions with catches, this one just calls every other
        try {
            SessionHelper.initiateTest();
            //sendFeedbackBefore("Iniciando..."); //@Todo: Change this to a rolling circle to feedback the user, doesnt need a position
            //this.baseTagList = this.dataCenter.initiate(barCode);
            //this.setTestMetaDataModel();
            //sendProductDescriptionFeedback();
            switch(this.dataCenter.isTestAllowed(barCode)) {
                case InlineFeddbackUtil.ALLOWED:
                  this.baseTagList = this.dataCenter.initiate(barCode, true);
                  this.setTestMetaDataModel();
                  sendProductDescriptionFeedback();
                  return FrontEndFeedbackUtil.OK;
                case InlineFeddbackUtil.NOT_ALLOWED:
                  this.baseTagList = this.dataCenter.initiate(barCode, false);
                  this.setTestMetaDataModel();
                  sendProductDescriptionFeedback();
                  return FrontEndFeedbackUtil.TESTE_NAO_AUTORIZADO;
                case InlineFeddbackUtil.NOT_ENABLED:
                  this.baseTagList = this.dataCenter.initiate(barCode, false);
                  this.setTestMetaDataModel();
                  sendProductDescriptionFeedback();
                  return FrontEndFeedbackUtil.OK;
                default:
                  // code block
                  return null;
            }
            /* 
            if (this.dataCenter.getInlineConnector().isTestAllowed()) {
                this.baseTagList = this.dataCenter.initiate(barCode, true);
                this.setTestMetaDataModel();
                sendProductDescriptionFeedback();
                return FrontEndFeedbackUtil.OK;
            } else {
                this.baseTagList = this.dataCenter.initiate(barCode, false);
                this.setTestMetaDataModel();
                sendProductDescriptionFeedback();
                return FrontEndFeedbackUtil.TESTE_NAO_AUTORIZADO;
            }
            */
        } catch (SapException e) {
            return FrontEndFeedbackUtil.SAP_ERROR;
        } catch (InlineException e) {
            return FrontEndFeedbackUtil.INLINE_ERROR;
        } catch (SessionException e) {
            return FrontEndFeedbackUtil.SESSION_ERROR;
        } catch (TestUnmarshalingException e) {
            return FrontEndFeedbackUtil.ERRO_LOCALIZACAO_ROTINA;
        } catch (TestSetupException e) {
            return FrontEndFeedbackUtil.FALHA_SETUP_PRODUTO;
        } catch (Exception e) {
            return FrontEndFeedbackUtil.ERRO_INESPERADO;
        } finally {
            //@Todo: probably useless
        }
    }

    private void setTestMetaDataModel() {
        TestMetaDataModel refreshStaticVariable = new TestMetaDataModel(baseTagList.qntOfProductInTest());
        TestMetaDataModel.tagList = baseTagList; 
        TestMetaDataModel.template = this.template;
        TestMetaDataModel.sapConnector = this.dataCenter.getSapConnector().getSapDataMap();
    }

    private boolean endSetup(boolean initSetupFail) {
        //@Todo: Enhance this function
        if(initSetupFail || TestMetaDataModel.exitFlag) {
            if(initSetupFail) {
                try {
                    SessionHelper.endTest(result);
                    //this.dataCenter.saveMongo(result);
                    return true;
                //} catch (DataBaseException e) {
                //    sendFeedbackAfter(FrontEndFeedbackUtil.DATABASE_ERROR, "", false, TestStatusUtil.ENDING_SETUP_FAULT, 0);
                } catch (Exception e) {
                    sendFeedbackAfter(FrontEndFeedbackUtil.ERRO_INESPERADO, "", false, TestStatusUtil.ENDING_SETUP_FAULT, 0);
                }
            }
            if(TestMetaDataModel.exitFlag) {
                try {
                    SessionHelper.endTest(result);
                    this.dataCenter.saveMongo(result);
                    return true;
                } catch (DataBaseException e) {
                    sendFeedbackAfter(FrontEndFeedbackUtil.DATABASE_ERROR, "", false, TestStatusUtil.ENDING_SETUP_FAULT, 0);
                } catch (Exception e) {
                    sendFeedbackAfter(FrontEndFeedbackUtil.ERRO_INESPERADO, "", false, TestStatusUtil.ENDING_SETUP_FAULT, 0);
                }
            }
        } else {
            try {
                SessionHelper.endTest(result);
                this.dataCenter.end(result);
                return true;
            } catch (InlineException e) {
                sendFeedbackAfter(FrontEndFeedbackUtil.INLINE_ERROR, "", false, TestStatusUtil.ENDING_SETUP_FAULT, 0);
            } catch (DataBaseException e) {
                sendFeedbackAfter(FrontEndFeedbackUtil.DATABASE_ERROR, "", false, TestStatusUtil.ENDING_SETUP_FAULT, 0);
            } catch (EnsException e) {
                sendFeedbackAfter(FrontEndFeedbackUtil.ENS_ERROR, "", false, TestStatusUtil.ENDING_SETUP_FAULT, 0);
            } catch (Exception e) {
                sendFeedbackAfter(FrontEndFeedbackUtil.ERRO_INESPERADO, "", false, TestStatusUtil.ENDING_SETUP_FAULT, 0);
            }
        }

        return false;
    }

    private HashMap<String, String[]> startTestingRoutine() {    //@Todo: Reformulate this method, maybe put some for loop outside or so
        HashMap<String, String[]> map = new HashMap<String, String[]>();
        String[] results = new String[baseTagList.qntOfProductInTest()];
        String[] log = new String[baseTagList.qntOfProductInTest()];
        Arrays.fill(log, "");
        try {
            for (int i = 0; i < baseTagList.getList().size(); i++) {
                if (TestMetaDataModel.exitFlag) {
                    Arrays.fill(results, FrontEndFeedbackUtil.CANCELADO);
                    map.put("results", results);
                    map.put("log", log);
                    return map;
                } else {
                    CommandLog commandLog = baseTagList.getList().get(i).command();
                    this.template.convertAndSend(EndPointPathUtil.CHANNEL,  commandLog);
                }
            }
            for (int c = 0; c < baseTagList.qntOfProductInTest(); c++) {
                if (TestMetaDataModel.isPositionEnabled[c].get()) {
                    results[c] = FrontEndFeedbackUtil.OK;
                } else {
                    for (int i = 0; i < baseTagList.getList().size(); i++) {
                        if(!baseTagList.getList().get(i).getTestResult().equals(FrontEndFeedbackUtil.OK) && baseTagList.getList().get(i).getPosition()==(c+1)) {
                            results[c] = baseTagList.getList().get(i).getTestResult();
                            log[c] = baseTagList.getList().get(i).getLog();
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Arrays.fill(results, FrontEndFeedbackUtil.ERRO_INESPERADO);
        }
        map.put("results", results);
        map.put("log", log);
        return map;
    }

    @MessageMapping(EndPointPathUtil.STOP)
    public void stopRoutine() {
        TestMetaDataModel.exitFlag = true;
    }
}
