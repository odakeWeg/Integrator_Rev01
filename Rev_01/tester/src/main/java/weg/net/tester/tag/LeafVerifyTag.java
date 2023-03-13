package weg.net.tester.tag;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.exception.CommunicationException;
import weg.net.tester.exception.ObjectNotFoundException;
import weg.net.tester.utils.CompareUtil;
import weg.net.tester.utils.FailureCodeUtil;
import weg.net.tester.utils.TagNameUtil;

@Getter
@Setter
public class LeafVerifyTag extends NodeCompareTag {

    protected int valueRef;
    protected String communicationNameOnTest;
    protected int registerOnTest;
    protected String registerNameOnTest;
    protected int valueOnTest;
    protected String calculateBy;
    protected int tolerancy;
    protected int waitBefore;
    protected int waitAfter;

    @Override
    protected void executeCommand() {
        if (readOnTest()) {
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

    protected void percentualCompare() {
        boolean upperLimit = valueOnTest <= valueRef + valueRef*tolerancy/100;
        boolean lowerLimit = valueOnTest >= valueRef - valueRef*tolerancy/100;

        ensTagConfiguration.setAcceptanceRange(valueRef + valueRef*tolerancy/100, valueRef - valueRef*tolerancy/100);

        if (upperLimit && lowerLimit) {
            testResult = FailureCodeUtil.OK;
            log = "Valor lido dentro da tolerancia: " +  (valueRef - valueRef*tolerancy/100) + " < Valor medido: " + valueOnTest + " < " + (valueRef + valueRef*tolerancy/100);
        } else {
            log = "Valor lido fora da tolerancia: " +  (valueRef - valueRef*tolerancy/100) + " < Valor medido: " + valueOnTest + " < " + (valueRef + valueRef*tolerancy/100);
            setFailureCommandLog(FailureCodeUtil.OUT_OF_TOLERANCY, log);
        }
    }

    protected void absoluteCompare() {
        boolean upperLimit = valueOnTest <= valueRef + tolerancy;
        boolean lowerLimit = valueOnTest >= valueRef - tolerancy;

        ensTagConfiguration.setAcceptanceRange(valueRef + tolerancy, valueRef - tolerancy);

        if (upperLimit && lowerLimit) {
            testResult = FailureCodeUtil.OK;
            log = "Valor lido dentro da tolerancia: " +  (valueRef - tolerancy) + " < Valor medido: " + valueOnTest + " < " + (valueRef + tolerancy);
        } else {
            log = "Valor lido fora da tolerancia: " +  (valueRef - tolerancy) + " < Valor medido: " + valueOnTest + " < " + (valueRef + tolerancy);
            setFailureCommandLog(FailureCodeUtil.OUT_OF_TOLERANCY, log);
        }
    }

    public boolean readOnTest() {
        try {
            BaseCommunication communicationOnTest = getCommunicationByName(communicationNameOnTest);

            delayMilliseconds(waitBefore);
            try {
                valueOnTest = communicationOnTest.readSingleRegister(registerOnTest);
                testResult = FailureCodeUtil.OK;
                log = "Valor de leitura igual a " + valueOnTest + " no registrador " + registerOnTest;
            } catch (CommunicationException e) {
                log = "Falha na comunicação com " + communicationNameOnTest;
                setFailureCommandLog(FailureCodeUtil.FALHA_COMUNICACAO, log);
                return false;
            }
            delayMilliseconds(waitAfter);
            return true;
        } catch (ObjectNotFoundException e) {
            testResult = FailureCodeUtil.OBJECT_NOT_FOUND;
            log = "Comunicação com nome " + communicationNameOnTest + " não foi encontrado, verificar se a rotina de teste está correta";
            return false;
        }
    }

    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.VERIFY;
    }
    
}
