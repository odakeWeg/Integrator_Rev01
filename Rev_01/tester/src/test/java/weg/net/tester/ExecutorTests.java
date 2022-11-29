package weg.net.tester;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import weg.net.tester.controllers.TestRoutineHandlerController;
import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.services.TestExecutor;

@SpringBootTest
public class ExecutorTests {
    //@Todo
    
    /* 
    private SimpMessagingTemplate template;
    
    @Autowired
    void WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }
    
    
    @Test
    public void routineExecution() {
        String cod = "017890355940471 211033926936 10 911108232984";
        TestExecutor testExecutor = new TestExecutor(cod, this.template);
        testExecutor.execute();
        
        for (int i = 0; i < TestMetaDataModel.isPositionEnabled.length; i++) {
            Assert.assertEquals(TestMetaDataModel.isPositionEnabled[i], true);
        }
    }

    
    @Test
    public void sendFeedbackToFrontEnd() {
        template = new SimpMessagingTemplate(null);
        String commandLog = "chegou";
        this.template.convertAndSend("/feedback",  commandLog);
    }
    */
}
