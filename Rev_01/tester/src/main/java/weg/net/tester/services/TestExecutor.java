package weg.net.tester.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.facade.datacenter.DataCenter;
import weg.net.tester.models.CommandLog;
import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.models.TestingRoutine;
import weg.net.tester.tag.TagList;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.FilePathUtil;
import weg.net.tester.utils.FrontEndFeedbackUtil;
import weg.net.tester.utils.SapCaracUtil;

@Getter
@Setter
@Configuration
public class TestExecutor {
    private SimpMessagingTemplate template;
    private String barCode;
    @Autowired
    private DataCenter dataCenter;
    private String[] result;
    TagList baseTagList;

    public void execute() {
        String initSetupStatus = initSetup();
        if(initSetupStatus.equals(FrontEndFeedbackUtil.OK)) {
            this.result = startTestingRoutine();
            sendFeedbackToFrontEnd(this.result[0], true);   //@Todo: mandar todos os resultados
        } else {
            sendFeedbackToFrontEnd(initSetupStatus, false); //@Todo: log completo do que pode ter acontecido usando uma função para nao poluir essa parte -> esperar implementação inline ens sap
        }
        endSetup();
    }

    private void sendFeedbackToFrontEnd(String result, boolean finished) {
        CommandLog commandLog = new CommandLog(result, ActionCommandUtil.SHOW_FINAL_RESULT, finished);
        this.template.convertAndSend("/feedback2",  commandLog);
    }

    private String initSetup() {
        //@Todo: this return is only for testing porpuses
        try {
            this.dataCenter.initiate(barCode);
            TestingRoutine testingRoutine = getList();
            this.baseTagList = testingRoutine.getRoutine();
            TestMetaDataModel refreshStaticVariable = new TestMetaDataModel(baseTagList.qntOfProductInTest());
            TestMetaDataModel.tagList = baseTagList; 

            
            this.dataCenter.getMongoConnector().initialSetup();
            //this.dataCenter.getInlineConnector().saveInitialEvent();
            //return this.dataCenter.getInlineConnector().isTestAllowed();
            return FrontEndFeedbackUtil.OK;
        } catch (Exception e) { //@Todo: separar em todos os erros possíveis
            e.printStackTrace();
            return FrontEndFeedbackUtil.ERRO_INESPERADO;
            //return FrontEndFeedbackUtil.ERRO_LOCALIZACAO_ROTINA;
        }
    }

    private void endSetup() {
        //closingTestSetup get error step and run a cancelling test 
        //routine (set log -> maybe changed to tags iself), save 
        //data and upload session and stuff
        //database and Session (increment from executed tests)
        try {
            this.dataCenter.getMongoConnector().endingSetup(result[0], TestMetaDataModel.testStep[0], TestMetaDataModel.tagList);   //@Todo: mandar todos os resultados
        } catch (/*JsonProcessingException*/ Exception e) {
            sendFeedbackToFrontEnd(FrontEndFeedbackUtil.FALHA_NA_FINALIZACAO, false);
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
