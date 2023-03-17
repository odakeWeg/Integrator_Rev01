package weg.net.tester.tag;

import lombok.Getter;
import lombok.Setter;
import net.weg.soa.serviceclient.sales.salesorderbyidquery.QuantityTypeCode;
import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.exception.CommunicationException;
import weg.net.tester.exception.ObjectNotFoundException;
import weg.net.tester.utils.CompareUtil;
import weg.net.tester.utils.FailureCodeUtil;
import weg.net.tester.utils.TagNameUtil;

@Getter
@Setter
public class LeafMultipleRegisterCompareTag extends NodeCompareTag {

    protected String communicationNameRef;
    protected int registerRef;
    protected String registerNameRef;
    protected int[] valueRef;
    protected String communicationNameOnTest;
    protected int registerOnTest;
    protected String registerNameOnTest;
    protected int[] valueOnTest;
    protected String calculateBy;
    protected int[] tolerancy;
    protected int quantityOfRegisters;
    protected int waitBefore;
    protected int waitAfter;

    @Override
    protected void executeCommand() {
        if (readRef() && readOnTest()) {
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

    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.REGISTER_MULTIPLE_COMPARE;
    }

    private void percentualCompare() {
        int targetValue;
        int referenceValue;

        boolean upperLimit;
        boolean lowerLimit;

        int tolerancyPercentage;

        for(int i = 0; i < quantityOfRegisters; i++) {
            targetValue = valueOnTest[i];
            referenceValue = valueRef[i];
            tolerancyPercentage = tolerancy[i];

            upperLimit = targetValue*100 <= referenceValue*100 + referenceValue*tolerancyPercentage;
            lowerLimit = targetValue*100 >= referenceValue*100 - referenceValue*tolerancyPercentage;
            if (upperLimit && lowerLimit) {
                testResult = "OK";
                log = "Valor lido dentro da tolerancia: " +  (referenceValue - referenceValue*tolerancyPercentage/100) + " < Valor medido: " + targetValue + " < " + (referenceValue + referenceValue*tolerancyPercentage/100);
            } else {
                testResult = "Falha: Comparação dos registradores " + registerRef+i + " (" + communicationNameRef +")" + " e " + registerOnTest+i + " (" + communicationNameOnTest + ") fora da tolerância";
                log = "Valor lido fora da tolerancia: " +  (referenceValue - referenceValue*tolerancyPercentage/100) + " < Valor medido: " + targetValue + " < " + (referenceValue + referenceValue*tolerancyPercentage/100);
                return;
            }
        }
    }

    private void absoluteCompare() {
        int targetValue;
        int referenceValue;

        boolean upperLimit;
        boolean lowerLimit;

        int tolerancyAbsolute;

        for(int i = 0; i < quantityOfRegisters; i++) {
            targetValue = valueOnTest[i];
            referenceValue = valueRef[i];
            tolerancyAbsolute = tolerancy[i];
        
            upperLimit = (targetValue <= referenceValue + tolerancyAbsolute);
            lowerLimit = (targetValue >= referenceValue - tolerancyAbsolute);
            if (upperLimit && lowerLimit) {
                testResult = "OK";
                log = "Valor lido dentro da tolerancia: " +  (referenceValue - tolerancyAbsolute) + " < Valor medido: " + targetValue + " < " + (referenceValue + tolerancyAbsolute);
            } else {
                testResult = "Falha: Comparação dos registradores " + (registerRef+i) + " (" + communicationNameRef +")" + " e " + (registerOnTest+i) + " (" + communicationNameOnTest + ") fora da tolerância";
                log = "Valor lido fora da tolerancia: " +  (referenceValue - referenceValue*tolerancyAbsolute/100) + " < Valor medido: " + targetValue + " < " + (referenceValue + referenceValue*tolerancyAbsolute/100);
                return;
            }
        }
    }

    public boolean readRef() {
        try {
            BaseCommunication communicationRef = getCommunicationByName(communicationNameRef);

            delayMilliseconds(waitBefore);
            try {
                valueRef = communicationRef.readMultipleRegisters(registerRef, quantityOfRegisters);
                testResult = FailureCodeUtil.OK;
                log = "Valor de leitura de ref. igual a " + valueRef + " no registrador " + registerRef + " até " + (registerRef+quantityOfRegisters);
            } catch (CommunicationException e) {
                log = "Falha na comunicação com " + communicationNameRef;
                setFailureCommandLog(FailureCodeUtil.FALHA_COMUNICACAO, log);
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

    public boolean readOnTest() {
        try {
            BaseCommunication communicationOnTest = getCommunicationByName(communicationNameOnTest);

            delayMilliseconds(waitBefore);
            try {
                valueOnTest = communicationOnTest.readMultipleRegisters(registerOnTest, quantityOfRegisters);
                testResult = FailureCodeUtil.OK;
                log += " | Valor de leitura de teste igual a " + valueOnTest.toString() + " no registrador " + registerOnTest + " até: " + (registerRef+quantityOfRegisters);
            } catch (CommunicationException e) {
                log += " | Falha na comunicação com " + communicationNameOnTest;
                setFailureCommandLog(FailureCodeUtil.FALHA_COMUNICACAO, log);
                return false;
            }
            delayMilliseconds(waitAfter);
            return true;
        } catch (ObjectNotFoundException e) {
            testResult = FailureCodeUtil.OBJECT_NOT_FOUND;
            log += " | Comunicação com nome " + communicationNameOnTest + " não foi encontrado, verificar se a rotina de teste está correta";
            return false;
        }
    }
}
