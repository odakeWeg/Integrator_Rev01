package weg.net.tester.tag;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.FailureCodeUtil;
import weg.net.tester.utils.TagNameUtil;

@Getter
@Setter
@Controller
public class LeafUserConfirmationTag extends NodeCompareTag {
    @JsonIgnore
    private SimpMessagingTemplate template;
    @JsonIgnore
    private static boolean confirmation;
    @JsonIgnore
    private static boolean responseFlag;

    protected String messageToDisplay;

    protected boolean confirmationValue;

    @Override
    public void executeCommand() {
        this.template = TestMetaDataModel.template;
        confirmationRequest();
    }

    //@Todo: If multithreaded, make this a Async func
    private void confirmationRequest() {
        responseFlag = false;

        //@Todo: change feedback
        this.template.convertAndSend("/feedback",  messageToDisplay);
        waitConfirmation();
        if(confirmationValue) {
            testResult = FailureCodeUtil.OK;
            log = "Confirmação do usuário captada com sucesso";
            //action = ActionCommandUtil.EXIBIT_VALUES;
        } else {
            testResult = FailureCodeUtil.CONFIRMACAO_NEGADA;
            log = "Confirmação do usuário negada";
            //action = ActionCommandUtil.EXIBIT_VALUES;
            TestMetaDataModel.isPositionEnabled[this.position-1] = false;
            TestMetaDataModel.testStep[this.position-1] = this.id;
        }
    }

    private void waitConfirmation() {
        while(!responseFlag) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
        }
        this.confirmationValue = confirmation;
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