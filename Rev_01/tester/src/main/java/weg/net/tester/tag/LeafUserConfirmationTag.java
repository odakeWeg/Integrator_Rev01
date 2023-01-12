package weg.net.tester.tag;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.models.web.PopUpLog;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.EndPointPathUtil;
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

    private void confirmationRequest() {
        responseFlag = false;

        //@Todo: change feedback
        PopUpLog popUpLog = new PopUpLog(messageToDisplay, ActionCommandUtil.USER_CONFIRM, timeout);
        this.template.convertAndSend(EndPointPathUtil.CHANNEL,  popUpLog);
        waitConfirmation();
        if(confirmationValue) {
            testResult = FailureCodeUtil.OK;
            log = "Confirmação do usuário captada com sucesso";
        } else {
            log = "Confirmação do usuário negada";
            setFailureCommandLog(FailureCodeUtil.CONFIRMACAO_NEGADA, log);
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

    //@Todo: Create class to implement every web socket stuff
    @MessageMapping(EndPointPathUtil.CONFIRMATION)
    public void onReceivedMesage(boolean confirmation) {
        LeafUserConfirmationTag.confirmation = confirmation;
        LeafUserConfirmationTag.responseFlag = true;
    }

    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.USER_CONFIRMATION;
    }
}