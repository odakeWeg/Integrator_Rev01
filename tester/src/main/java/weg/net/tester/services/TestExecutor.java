package weg.net.tester.services;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import weg.net.tester.facade.DataCenter;
import weg.net.tester.models.TestingRoutine;

public class TestExecutor {
    private SimpMessagingTemplate template;
    private String barCode;

    public TestExecutor(String barCode, SimpMessagingTemplate template) {
        this.barCode = barCode;
        this.template = template;
    }

    public void execute() {
        private DataCenter dataCenter;
        private String result;

        dataCenter = new DataCenter(barCode);
        if(initSetup()) {
            result = startTestingRoutine(getList());
        } else {
            //@Todo: log completo do que pode ter acontecido usando uma função para nao poluir essa parte
        }
        endSetup();

        
        this.template.convertAndSend("/feedback",  barCode);
    }

    private boolean initSetup() {

    }

    private void endSetup() {

    }

    private String startTestingRoutine(TestingRoutine testingRoutine) {

    }

    private TestingRoutine getList() {

    }
    
}
