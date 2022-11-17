package weg.net.tester.services;

import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import weg.net.tester.facade.datacenter.DataCenter;
import weg.net.tester.models.CommandLog;
import weg.net.tester.models.TestingRoutine;
import weg.net.tester.tag.BaseTag;
import weg.net.tester.tag.ParentTag;
import weg.net.tester.utils.FilePathUtil;
import weg.net.tester.utils.FrontEndFeedbackUtil;
import weg.net.tester.utils.SapCaracUtil;

public class TestExecutor {
    private SimpMessagingTemplate template;
    private String barCode;
    private DataCenter dataCenter;
    private String result;

    public TestExecutor(String barCode, SimpMessagingTemplate template) {
        this.barCode = barCode;
        this.template = template;
    }

    public void execute() {

        this.dataCenter = new DataCenter(this.barCode);
        if(initSetup()) {
            this.result = startTestingRoutine(getList());
            //Send ok (end of the test) back to the front test end or stuff***
        } else {
            //@Todo: log completo do que pode ter acontecido usando uma função para nao poluir essa parte
        }
        endSetup();

    }

    private boolean initSetup() {
        //@Todo: this return is only for testing porpuses
        try {
            //this.dataCenter.getInlineConnector().saveInitialEvent();
            this.dataCenter.getMongoConnector().initialSetup();
            //return this.dataCenter.getInlineConnector().isTestAllowed();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void endSetup() {
        //closingTestSetup get error step and run a cancelling test 
        //routine (set log -> maybe changed to tags iself), save 
        //data and upload session and stuff
        // database and Session (increment from executed tests)
        this.dataCenter.getMongoConnector().endingSetup(result, ParentTag.testMetaData.getTestStep(), ParentTag.testMetaData.getTagList());

        //Last thing
        ParentTag.testMetaData.setTagList(null);
    }

    private String startTestingRoutine(TestingRoutine testingRoutine) {
        //@Todo
        List<BaseTag> baseTagList = testingRoutine.getRoutine();
        ParentTag.testMetaData.setTagList(baseTagList);
        try {
            //Execution - Just a for loop and stuff -> create an exit 

            for (int i = 0; i < baseTagList.size(); i++) {
                CommandLog commandLog = baseTagList.get(i).command();
                this.template.convertAndSend("/feedback",  commandLog);
            }
            //@Todo: Another for loop will be needed to check every position
            if (ParentTag.testMetaData.getIsPositionEnabled()[0]) {
                return FrontEndFeedbackUtil.OK;
            } else {
                return FrontEndFeedbackUtil.FALHA_NO_TESTE;
            }
        } catch (Exception e) {
            return FrontEndFeedbackUtil.ERRO_INESPERADO;
        }
    }

    private TestingRoutine getList() {
        String fileReferenceName = this.dataCenter.getSapConnector().getSapDataMap().get(SapCaracUtil.SHORT_TEXT);
        return new TestingRoutine(FilePathUtil.TEST_ROUTINE_PATH + fileReferenceName + ".xml");
    }
}
