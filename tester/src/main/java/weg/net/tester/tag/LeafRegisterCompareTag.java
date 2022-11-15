package weg.net.tester.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.exception.CommunicationException;
import weg.net.tester.utils.FailureCodeUtil;

@XmlRootElement(name = "registerCompare")
@XmlAccessorType (XmlAccessType.FIELD)
public class LeafRegisterCompareTag extends NodeCompareTag {

    private String communicationNameRef;
    private int registerRef;
    private String registerNameRef;
    private int valueRef;
    private String communicationNameOnTest;
    private int registerOnTest;
    private String registerNameOnTest;
    private int valueOnTest;
    private String calculateBy;
    private int tolerancy;
    private int waitBefore;
    private int waitAfter;
    

    @Override
    public void executeCommand() {
        if (readRef() || readOnTest()) {
            switch(calculateBy) {
                case "Percentage":
                    percentualCompare();
                break;
                case "Absolute":
                    absoluteCompare();
                break;
            }
        } else {
            return;
        }
    }

    private void percentualCompare() {
        boolean upperLimit = valueOnTest <= valueRef + valueRef*tolerancy/100;
        boolean lowerLimit = valueOnTest >= valueRef - valueRef*tolerancy/100;
        if (upperLimit && lowerLimit) {
            testResult = FailureCodeUtil.OK;
            log = "Valor lido dentro da tolerancia: " +  (valueRef - valueRef*tolerancy/100) + " < Valor medido: " + valueOnTest + " < " + (valueRef + valueRef*tolerancy/100);
        } else {
            testResult = FailureCodeUtil.OUT_OF_TOLERANCY;
            log = "Valor lido fora da tolerancia: " +  (valueRef - valueRef*tolerancy/100) + " < Valor medido: " + valueOnTest + " < " + (valueRef + valueRef*tolerancy/100);
            isPositionEnable[this.position-1] = false;
        }
    }

    private void absoluteCompare() {
        boolean upperLimit = valueOnTest <= valueRef + tolerancy;
        boolean lowerLimit = valueOnTest >= valueRef - tolerancy;
        if (upperLimit && lowerLimit) {
            testResult = FailureCodeUtil.OK;
            log = "Valor lido dentro da tolerancia: " +  (valueRef - tolerancy) + " < Valor medido: " + valueOnTest + " < " + (valueRef + tolerancy);
        } else {
            testResult = FailureCodeUtil.OUT_OF_TOLERANCY;
            log = "Valor lido fora da tolerancia: " +  (valueRef - tolerancy) + " < Valor medido: " + valueOnTest + " < " + (valueRef + tolerancy);
            isPositionEnable[this.position-1] = false;
        }
    }

    public boolean readRef() {
        delayMilliseconds(waitBefore);
        BaseCommunication communicationRef = getCommunicationByName(communicationNameRef);
        if(communicationRef == null) {
            testResult = FailureCodeUtil.OBJECT_NOT_FOUND;
            log = "Comunicação com nome " + communicationNameRef + " não foi encontrado, verificar se a rotina de teste está correta";
            return false;
        } else {
            try {
                valueRef = communicationRef.readSingleRegister(registerRef);
                testResult = FailureCodeUtil.OK;
                log = "Valor de leitura igual a " + valueRef + " no registrador " + registerRef;
            } catch (CommunicationException e) {
                testResult = FailureCodeUtil.FALHA_COMUNICACAO;
                log = "Falha na comunicação com " + communicationNameRef;
                isPositionEnable[this.position-1] = false;
                return false;
            }
        }
        delayMilliseconds(waitAfter);
        return true;
    }

    public boolean readOnTest() {
        delayMilliseconds(waitBefore);
        BaseCommunication communicationOnTest = getCommunicationByName(communicationNameOnTest);
        if(communicationOnTest == null) {
            testResult = FailureCodeUtil.OBJECT_NOT_FOUND;
            log = "Comunicação com nome " + communicationNameOnTest + " não foi encontrado, verificar se a rotina de teste está correta";
            return false;
        } else {
            try {
                valueOnTest = communicationOnTest.readSingleRegister(registerOnTest);
                testResult = FailureCodeUtil.OK;
                log = "Valor de leitura igual a " + valueOnTest + " no registrador " + registerOnTest;
            } catch (CommunicationException e) {
                testResult = FailureCodeUtil.FALHA_COMUNICACAO;
                log = "Falha na comunicação com " + communicationNameOnTest;
                isPositionEnable[this.position-1] = false;
                return false;
            }
        }
        delayMilliseconds(waitAfter);
        return true;
    }
    
}
