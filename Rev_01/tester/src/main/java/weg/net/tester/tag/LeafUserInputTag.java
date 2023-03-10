package weg.net.tester.tag;

import java.util.concurrent.TimeUnit;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.exception.CommunicationException;
import weg.net.tester.exception.ObjectNotFoundException;
import weg.net.tester.models.web.PopUpLog;
import weg.net.tester.models.web.ResultLog;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.CompareUtil;
import weg.net.tester.utils.EndPointPathUtil;
import weg.net.tester.utils.FailureCodeUtil;
import weg.net.tester.utils.TagNameUtil;

@Getter
@Setter
@Controller
public class LeafUserInputTag extends NodeCompareTag {

    @JsonIgnore
    private SimpMessagingTemplate template;
    @JsonIgnore
    private static int input;
    @JsonIgnore
    private static boolean responseFlag;
    @JsonIgnore
    private static boolean inputFailure;

    protected String messageToDisplay;
    
    protected boolean userInputFailure;
    protected int inputValue;
    protected String communicationNameRef;
    protected String registerName;
    protected int registerRef;
    protected int valueRef;
    protected String calculateBy;
    protected int tolerancy;
    protected int waitBefore;
    protected int waitAfter;

    @Override
    public void executeCommand() {
        this.template = TestMetaDataModel.template;
        inputRequest();
    }

    private void inputRequest() {
        responseFlag = false;

        //ResultLog resultLog = new ResultLog("result", "log", true, "status", 1);
        //this.template.convertAndSend(EndPointPathUtil.CHANNEL,  resultLog);
        //@Todo: change feedback
        PopUpLog popUpLog = new PopUpLog(messageToDisplay, ActionCommandUtil.USER_INPUT, timeout);
        this.template.convertAndSend(EndPointPathUtil.CHANNEL,  popUpLog);
        if (!waitConfirmation()) {
            checkInputValue();
        } else {
            log = "Entrada do usuário invalida";
            setFailureCommandLog(FailureCodeUtil.INVALID_USER_INPUT, log);
        }
    }

    private void checkInputValue() {
        if (readRef()) {
            switch(calculateBy) {
                case CompareUtil.ABSOLUTE:
                    absoluteCompare();
                break;
                case CompareUtil.PERCENTAGE:
                    percentualCompare();
                break;
            }
        } else {
            //@Todo: make feedback log or something
        }
    }

    private void percentualCompare() {
        boolean upperLimit = inputValue <= valueRef + valueRef*tolerancy/100;
        boolean lowerLimit = inputValue >= valueRef - valueRef*tolerancy/100;

        ensTagConfiguration.setAcceptanceRange(valueRef + valueRef*tolerancy/100, valueRef - valueRef*tolerancy/100);

        if (upperLimit && lowerLimit) {
            testResult = FailureCodeUtil.OK;
            log = "Valor lido dentro da tolerancia: " +  (valueRef - valueRef*tolerancy/100) + " < Valor lido: " + inputValue + " < " + (valueRef + valueRef*tolerancy/100);
        } else {
            log = "Valor lido fora da tolerancia: " +  (valueRef - valueRef*tolerancy/100) + " < Valor lido: " + inputValue + " < " + (valueRef + valueRef*tolerancy/100);
            setFailureCommandLog(FailureCodeUtil.OUT_OF_TOLERANCY, log);
        }
    }

    private void absoluteCompare() {
        boolean upperLimit = inputValue <= valueRef + tolerancy;
        boolean lowerLimit = inputValue >= valueRef - tolerancy;

        ensTagConfiguration.setAcceptanceRange(valueRef + tolerancy, valueRef - tolerancy);

        if (upperLimit && lowerLimit) {
            testResult = FailureCodeUtil.OK;
            log = "Valor lido dentro da tolerancia: " +  (valueRef - tolerancy) + " < Valor lido: " + inputValue + " < " + (valueRef + tolerancy);
        } else {
            log = "Valor lido fora da tolerancia: " +  (valueRef - tolerancy) + " < Valor lido: " + inputValue + " < " + (valueRef + tolerancy);
            setFailureCommandLog(FailureCodeUtil.OUT_OF_TOLERANCY, log);
        }
    }

    private boolean readRef() {
        try {
            BaseCommunication communicationRef = getCommunicationByName(communicationNameRef);

            delayMilliseconds(waitBefore);
            try {
                valueRef = communicationRef.readSingleRegister(registerRef);
                testResult = FailureCodeUtil.OK;
                log = "Valor de leitura igual a " + valueRef + " no registrador " + registerRef;
            } catch (CommunicationException e) {
                log = "Falha na comunicação com " + communicationNameRef;
                setFailureCommandLog(FailureCodeUtil.FALHA_COMUNICACAO, log);
                return false;
            }
            delayMilliseconds(waitAfter);
            return true;
        } catch (ObjectNotFoundException e) {
            log = "Comunicação com nome " + communicationNameRef + " não foi encontrado, verificar se a rotina de teste está correta";
            setFailureCommandLog(FailureCodeUtil.OBJECT_NOT_FOUND, log);
            return false;
        }
    }

    private boolean waitConfirmation() {
        while(!responseFlag) {
            try {
                TimeUnit.MILLISECONDS.sleep(1);
            } catch (InterruptedException e) {
            }
        }
        this.inputValue = input;
        this.userInputFailure = inputFailure;

        return this.userInputFailure;
    }

    @MessageMapping(EndPointPathUtil.INPUT)
    public void onReceivedMesage(String input) {
        try {
            LeafUserInputTag.input = Integer.parseInt(input);
            LeafUserInputTag.inputFailure = false;
        } catch (Exception e) {
            LeafUserInputTag.inputFailure = true;
        }
        
        LeafUserInputTag.responseFlag = true;
    }
    
    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.USER_INPUT;
    }
}
