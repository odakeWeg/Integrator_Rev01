package weg.net.tester.tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonIgnore;

import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.FailureCodeUtil;
import weg.net.tester.utils.TagNameUtil;

@Controller
public class LeafUserConfirmationTag extends NodeCompareTag {
    @JsonIgnore
    private SimpMessagingTemplate template;
    private static boolean confirmation;
    private static boolean responseFlag;

    protected String messageToDisplay;

    @Autowired
    void WebSocketController(SimpMessagingTemplate template) {
        this.template = template;
    }

    public LeafUserConfirmationTag() {
        this.setTagName();
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
            TestMetaDataModel.isPositionEnabled[this.position-1] = false;
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
        this.tagName = TagNameUtil.USER_CONFIRMATION;
    }
}