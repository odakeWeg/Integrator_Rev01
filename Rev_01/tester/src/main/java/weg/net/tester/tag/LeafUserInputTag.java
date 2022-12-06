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
import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.utils.CompareUtil;
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
    protected int registerRef;
    protected int valueRef;
    protected String calculateBy;
    protected int tolerancy;
    protected int waitBefore;
    protected int waitAfter;

    /* 
    Check if works
    public LeafUserInputTag() {
        this.setTagName();
    }
    */

    @Override
    public void executeCommand() {
        this.template = TestMetaDataModel.template;
        inputRequest();
    }

    private void inputRequest() {
        responseFlag = false;

        //@Todo: change feedback
        this.template.convertAndSend("/feedback",  messageToDisplay);
        if (!waitConfirmation()) {
            checkInputValue();
        } else {
            testResult = FailureCodeUtil.INVALID_USER_INPUT;
            log = "Entrada do usuário invalida";
            TestMetaDataModel.isPositionEnabled[this.position-1] = false;
            TestMetaDataModel.testStep[this.position-1] = this.id;

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
        }
    }

    private void percentualCompare() {
        boolean upperLimit = inputValue <= valueRef + valueRef*tolerancy/100;
        boolean lowerLimit = inputValue >= valueRef - valueRef*tolerancy/100;
        if (upperLimit && lowerLimit) {
            testResult = FailureCodeUtil.OK;
            log = "Valor lido dentro da tolerancia: " +  (valueRef - valueRef*tolerancy/100) + " < Valor lido: " + inputValue + " < " + (valueRef + valueRef*tolerancy/100);
        } else {
            testResult = FailureCodeUtil.OUT_OF_TOLERANCY;
            log = "Valor lido fora da tolerancia: " +  (valueRef - valueRef*tolerancy/100) + " < Valor lido: " + inputValue + " < " + (valueRef + valueRef*tolerancy/100);
            TestMetaDataModel.isPositionEnabled[this.position-1] = false;
            TestMetaDataModel.testStep[this.position-1] = this.id;
        }
    }

    private void absoluteCompare() {
        boolean upperLimit = inputValue <= valueRef + tolerancy;
        boolean lowerLimit = inputValue >= valueRef - tolerancy;
        if (upperLimit && lowerLimit) {
            testResult = FailureCodeUtil.OK;
            log = "Valor lido dentro da tolerancia: " +  (valueRef - tolerancy) + " < Valor lido: " + inputValue + " < " + (valueRef + tolerancy);
        } else {
            testResult = FailureCodeUtil.OUT_OF_TOLERANCY;
            log = "Valor lido fora da tolerancia: " +  (valueRef - tolerancy) + " < Valor lido: " + inputValue + " < " + (valueRef + tolerancy);
            TestMetaDataModel.isPositionEnabled[this.position-1] = false;
            TestMetaDataModel.testStep[this.position-1] = this.id;
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
                testResult = FailureCodeUtil.FALHA_COMUNICACAO;
                log = "Falha na comunicação com " + communicationNameRef;
                TestMetaDataModel.isPositionEnabled[this.position-1] = false;
                TestMetaDataModel.testStep[this.position-1] = this.id;
                return false;
            }
            delayMilliseconds(waitAfter);
            return true;
        } catch (ObjectNotFoundException e) {
            testResult = FailureCodeUtil.OBJECT_NOT_FOUND;
            log = "Comunicação com nome " + communicationNameRef + " não foi encontrado, verificar se a rotina de teste está correta";
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

    @MessageMapping("/input")
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
