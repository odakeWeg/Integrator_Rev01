package weg.net.tester.services;

import java.util.List;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import weg.net.tester.facade.DataCenter;
import weg.net.tester.models.TestingRoutine;
import weg.net.tester.tag.BaseTag;
import weg.net.tester.utils.FilePathUtil;

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
            //Send ok back to the front
        } else {
            //@Todo: log completo do que pode ter acontecido usando uma função para nao poluir essa parte
        }
        endSetup();

        
        this.template.convertAndSend("/feedback",  this.barCode);
    }

    private boolean initSetup() {
        //@Todo: this return is only for testing porpuses
        return true;
    }

    private void endSetup() {

    }

    private String startTestingRoutine(TestingRoutine testingRoutine) {
        //@Todo
        List<BaseTag> baseTagList = testingRoutine.getRoutine();
        //Pass MetaDataObj to the command
        //Put on a loop (doesnt need a break)
        //return result
        return "//@TODO";
    }

    private TestingRoutine getList() {
        String referenceName = this.dataCenter.getSapConnector().getSapDataMap().get("//@Todo");
        String current = this.dataCenter.getSapConnector().getSapDataMap().get("//@Todo");
        return new TestingRoutine(FilePathUtil.TEST_ROUTINE_PATH + referenceName + current + ".xml");
    }
}
