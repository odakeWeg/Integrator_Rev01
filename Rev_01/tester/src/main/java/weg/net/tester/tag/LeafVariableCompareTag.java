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
public class LeafVariableCompareTag extends NodeCompareTag {

    protected String variableName;
    protected int variableValue;
    protected String communicationNameRef;
    protected int registerRef;
    protected int valueRef;
    protected String calculateBy;
    protected int tolerancy;
    protected int waitBefore;
    protected int waitAfter;

    @Override
    protected void executeCommand() {
        if (getVariableValue()) {
            checkInputValue();
        }
    }

    private boolean getVariableValue() {
        try {
            variableValue = Integer.parseInt(TestMetaDataModel.sapConnector.get(position).get(this.variableName));
            return true;
        } catch (Exception e) {
            log = "Falha ao ler a variável: " + this.variableName;
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
        boolean upperLimit = variableValue <= valueRef + valueRef*tolerancy/100;
        boolean lowerLimit = variableValue >= valueRef - valueRef*tolerancy/100;
        if (upperLimit && lowerLimit) {
            testResult = FailureCodeUtil.OK;
            log = "Valor lido dentro da tolerancia: " +  (valueRef - valueRef*tolerancy/100) + " < Valor lido: " + variableValue + " < " + (valueRef + valueRef*tolerancy/100);
        } else {
            log = "Valor lido fora da tolerancia: " +  (valueRef - valueRef*tolerancy/100) + " < Valor lido: " + variableValue + " < " + (valueRef + valueRef*tolerancy/100);
            setFailureCommandLog(FailureCodeUtil.OUT_OF_TOLERANCY, log);
        }
    }

    private void absoluteCompare() {
        boolean upperLimit = variableValue <= valueRef + tolerancy;
        boolean lowerLimit = variableValue >= valueRef - tolerancy;
        if (upperLimit && lowerLimit) {
            testResult = FailureCodeUtil.OK;
            log = "Valor lido dentro da tolerancia: " +  (valueRef - tolerancy) + " < Valor lido: " + variableValue + " < " + (valueRef + tolerancy);
        } else {
            log = "Valor lido fora da tolerancia: " +  (valueRef - tolerancy) + " < Valor lido: " + variableValue + " < " + (valueRef + tolerancy);
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

    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.VARIABLE_COMPARE;
    }
}
