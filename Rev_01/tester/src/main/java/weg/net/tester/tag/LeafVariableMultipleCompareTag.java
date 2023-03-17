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
public class LeafVariableMultipleCompareTag extends NodeCompareTag {

    protected String[] variableName;
    protected int[] variableValue;
    protected String communicationNameOnTest;
    protected String registerNameOnTest;
    protected int registerOnTest;
    protected int[] valueOnTest;
    protected String calculateBy;
    protected int[] tolerancy;
    protected int quantityOfRegisters;
    protected int waitBefore;
    protected int waitAfter;

    @Override
    protected void executeCommand() {
        if (getVariableValue()) {
            checkInputValue();
        }
    }

    private boolean getVariableValue() {
        variableValue = new int[variableName.length];
        valueOnTest = new int[variableName.length]; 
        try {
            for(int i = 0; i < variableValue.length; i++) {
                variableValue[i] = Integer.parseInt(TestMetaDataModel.sapConnector.get(position-1).get(this.variableName[i]));
            }
            return true;
        } catch (Exception e) {

            log = "Falha ao ler as variáveis: [ " + this.variableName;
            for(int i = 0; i < variableValue.length; i++) {
                log += this.variableName[i] + " ";
            }
            log += "]";

            setFailureCommandLog(FailureCodeUtil.VARIABLE_READING_FAILURE, log);
            return false;
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
        int targetValue;
        int referenceValue;

        boolean upperLimit;
        boolean lowerLimit;

        int tolerancyPercentage;

        for(int i = 0; i < quantityOfRegisters; i++) {
            targetValue = variableValue[i];
            referenceValue = valueOnTest[i];
            tolerancyPercentage = tolerancy[i];

            upperLimit = targetValue*100 <= referenceValue*100 + referenceValue*tolerancyPercentage;
            lowerLimit = targetValue*100 >= referenceValue*100 - referenceValue*tolerancyPercentage;
            if (upperLimit && lowerLimit) {
                testResult = "OK";
                log = "Valor lido dentro da tolerancia: " +  (referenceValue - referenceValue*tolerancyPercentage/100) + " < Valor medido: " + targetValue + " < " + (referenceValue + referenceValue*tolerancyPercentage/100);
            } else {
                testResult = "Falha: Comparação dos registradores " + (registerOnTest+i) + " (" + communicationNameOnTest +")" + " e " + variableName[i] + " fora da tolerância";
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
            targetValue = variableValue[i];
            referenceValue = valueOnTest[i];
            tolerancyAbsolute = tolerancy[i];
        
            upperLimit = (targetValue <= referenceValue + tolerancyAbsolute);
            lowerLimit = (targetValue >= referenceValue - tolerancyAbsolute);
            if (upperLimit && lowerLimit) {
                testResult = "OK";
                log = "Valor lido dentro da tolerancia: " +  (referenceValue - tolerancyAbsolute) + " < Valor medido: " + targetValue + " < " + (referenceValue + tolerancyAbsolute);
            } else {
                testResult = "Falha: Comparação dos registradores " + (registerOnTest+i) + " (" + communicationNameOnTest +")" + " e " + variableName[i] + " fora da tolerância";
                log = "Valor lido fora da tolerancia: " +  (referenceValue - referenceValue*tolerancyAbsolute/100) + " < Valor medido: " + targetValue + " < " + (referenceValue + referenceValue*tolerancyAbsolute/100);
                return;
            }
        }
    }

    private boolean readRef() {
        try {
            BaseCommunication communicationRef = getCommunicationByName(communicationNameOnTest);

            delayMilliseconds(waitBefore);
            try {
                valueOnTest = communicationRef.readMultipleRegisters(registerOnTest, quantityOfRegisters);
                testResult = FailureCodeUtil.OK;
                log = "Valor de leitura igual a " + valueOnTest + " no registrador " + registerOnTest + " até " + (registerOnTest+quantityOfRegisters);
            } catch (CommunicationException e) {
                log = "Falha na comunicação com " + communicationNameOnTest;
                setFailureCommandLog(FailureCodeUtil.FALHA_COMUNICACAO, log);
                return false;
            }
            delayMilliseconds(waitAfter);
            return true;
        } catch (ObjectNotFoundException e) {
            log = "Comunicação com nome " + communicationNameOnTest + " não foi encontrado, verificar se a rotina de teste está correta";
            setFailureCommandLog(FailureCodeUtil.OBJECT_NOT_FOUND, log);
            return false;
        }
    }

    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.VARIABLE_MULTIPLE_COMPARE;
    }
    
}
