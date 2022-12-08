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
public class LeafRegisterCompareTag extends NodeCompareTag {

    protected String communicationNameRef;
    protected int registerRef;
    protected String registerNameRef;
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
    public void executeCommand() {
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

    protected void percentualCompare() {
        boolean upperLimit = valueOnTest <= valueRef + valueRef*tolerancy/100;
        boolean lowerLimit = valueOnTest >= valueRef - valueRef*tolerancy/100;
        if (upperLimit && lowerLimit) {
            testResult = FailureCodeUtil.OK;
            log = "Valor lido dentro da tolerancia: " +  (valueRef - valueRef*tolerancy/100) + " < Valor medido: " + valueOnTest + " < " + (valueRef + valueRef*tolerancy/100);
        } else {
            //testResult = FailureCodeUtil.OUT_OF_TOLERANCY;
            log = "Valor lido fora da tolerancia: " +  (valueRef - valueRef*tolerancy/100) + " < Valor medido: " + valueOnTest + " < " + (valueRef + valueRef*tolerancy/100);
            //TestMetaDataModel.isPositionEnabled[this.position-1] = false;
            //TestMetaDataModel.testStep[this.position-1] = this.id;
            //@Todo: takeOut
            setFailureCommandLog(FailureCodeUtil.OUT_OF_TOLERANCY, log);
        }
    }

    protected void absoluteCompare() {
        boolean upperLimit = valueOnTest <= valueRef + tolerancy;
        boolean lowerLimit = valueOnTest >= valueRef - tolerancy;
        if (upperLimit && lowerLimit) {
            testResult = FailureCodeUtil.OK;
            log = "Valor lido dentro da tolerancia: " +  (valueRef - tolerancy) + " < Valor medido: " + valueOnTest + " < " + (valueRef + tolerancy);
        } else {
            //testResult = FailureCodeUtil.OUT_OF_TOLERANCY;
            log = "Valor lido fora da tolerancia: " +  (valueRef - tolerancy) + " < Valor medido: " + valueOnTest + " < " + (valueRef + tolerancy);
            //TestMetaDataModel.isPositionEnabled[this.position-1] = false;
            //TestMetaDataModel.testStep[this.position-1] = this.id;
            //@Todo: takeOut
            setFailureCommandLog(FailureCodeUtil.OUT_OF_TOLERANCY, log);
        }
    }

    public boolean readRef() {
        try {
            BaseCommunication communicationRef = getCommunicationByName(communicationNameRef);

            delayMilliseconds(waitBefore);
            try {
                valueRef = communicationRef.readSingleRegister(registerRef);
                testResult = FailureCodeUtil.OK;
                log = "Valor de leitura igual a " + valueRef + " no registrador " + registerRef;
            } catch (CommunicationException e) {
                //testResult = FailureCodeUtil.FALHA_COMUNICACAO;
                log = "Falha na comunicação com " + communicationNameRef;
                //TestMetaDataModel.isPositionEnabled[this.position-1] = false;
                //TestMetaDataModel.testStep[this.position-1] = this.id;
                //@Todo: takeOut
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
                valueOnTest = communicationOnTest.readSingleRegister(registerOnTest);
                testResult = FailureCodeUtil.OK;
                log = "Valor de leitura igual a " + valueOnTest + " no registrador " + registerOnTest;
            } catch (CommunicationException e) {
                //testResult = FailureCodeUtil.FALHA_COMUNICACAO;
                log = "Falha na comunicação com " + communicationNameOnTest;
                //TestMetaDataModel.isPositionEnabled[this.position-1] = false;
                //TestMetaDataModel.testStep[this.position-1] = this.id;
                //@Todo: takeOut
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
        this.tagName = TagNameUtil.REGISTER_COMPARE;
    }
}
