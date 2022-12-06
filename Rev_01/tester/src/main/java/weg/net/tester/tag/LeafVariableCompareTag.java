package weg.net.tester.tag;

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
            variableValue = Integer.parseInt(TestMetaDataModel.sapConnector.get(this.variableName));
            return true;
        } catch (Exception e) {
            testResult = FailureCodeUtil.VARIABLE_READING_FAILURE;
            log = "Falha ao ler a variável: " + this.variableName;
            TestMetaDataModel.isPositionEnabled[this.position-1] = false;
            TestMetaDataModel.testStep[this.position-1] = this.id;
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
            testResult = FailureCodeUtil.OUT_OF_TOLERANCY;
            log = "Valor lido fora da tolerancia: " +  (valueRef - valueRef*tolerancy/100) + " < Valor lido: " + variableValue + " < " + (valueRef + valueRef*tolerancy/100);
            TestMetaDataModel.isPositionEnabled[this.position-1] = false;
            TestMetaDataModel.testStep[this.position-1] = this.id;
        }
    }

    private void absoluteCompare() {
        boolean upperLimit = variableValue <= valueRef + tolerancy;
        boolean lowerLimit = variableValue >= valueRef - tolerancy;
        if (upperLimit && lowerLimit) {
            testResult = FailureCodeUtil.OK;
            log = "Valor lido dentro da tolerancia: " +  (valueRef - tolerancy) + " < Valor lido: " + variableValue + " < " + (valueRef + tolerancy);
        } else {
            testResult = FailureCodeUtil.OUT_OF_TOLERANCY;
            log = "Valor lido fora da tolerancia: " +  (valueRef - tolerancy) + " < Valor lido: " + variableValue + " < " + (valueRef + tolerancy);
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

    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.VARIABLE_COMPARE;
    }
}
