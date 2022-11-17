package weg.net.tester.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.FailureCodeUtil;

@XmlRootElement(name = "userConfirmation")
@XmlAccessorType (XmlAccessType.FIELD)
@Controller
public class LeafUserConfirmationTag extends NodeCompareTag {
    @XmlTransient
    private SimpMessagingTemplate template;
    private static boolean confirmation;
    private static boolean responseFlag;

    protected String messageToDisplay;

    @Autowired
    void WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    public void executeCommand() {
        confirmationRequest();
    }

    //@Todo: If multithreaded, make this a Async func
    private void confirmationRequest() {
        responseFlag = false;

        //@Todo: change feedback
        this.template.convertAndSend("/feedback",  messageToDisplay);
        waitConfirmation();
        if(confirmation) {
            testResult = FailureCodeUtil.OK;
            log = "Confirmação do usuário captada com sucesso";
            action = ActionCommandUtil.EXIBIT_VALUES;
        } else {
            testResult = FailureCodeUtil.CONFIRMACAO_NEGADA;
            log = "Confirmação do usuário negada";
            action = ActionCommandUtil.EXIBIT_VALUES;
            testMetaData.getIsPositionEnabled()[this.position-1] = false;
        }
    }

    private void waitConfirmation() {
        while(!responseFlag) {
            //Waiting to receive data back
        }
    }

    @MessageMapping("/confirmation")
    public void onReceivedMesage(boolean confirmation) {
        LeafUserConfirmationTag.confirmation = confirmation;
        LeafUserConfirmationTag.responseFlag = true;
    }

    @Override
    public void setTagName() {
        this.tagName = "userConfirmation";
    }

    @Override
    public TestMetaDataModel getTestMetaData() {
        return this.testMetaData;
    }
}



        /* 
        ExecutorService executor = Executors.newCachedThreadPool();
        Callable<Boolean> task = new Callable<Boolean>() {
            public Boolean call() {
                LeafUserConfirmationTag.message = "asd";
                return true;
            }
        };
        Future<Boolean> future = executor.submit(task);
        try {
            future.get(timeout, TimeUnit.SECONDS); 
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
        } finally {
            future.cancel(true);
        }
        */