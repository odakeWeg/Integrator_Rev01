package weg.net.tester.tag;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.exception.CommunicationException;
import weg.net.tester.exception.ObjectNotFoundException;
import weg.net.tester.utils.FailureCodeUtil;
import weg.net.tester.utils.TagNameUtil;

@Getter
@Setter
public class LeafVariableWriteTag extends NodeWriteTag {

    protected String variableName;
    protected int variableValue;
    protected String communicationNameRef;
    protected String registerName;
    protected int registerRef;
    protected int waitBefore;
    protected int waitAfter;

    @Override
    protected void executeCommand() {
        if (getVariableValue()) {
            writeVariableValue();
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

    private void writeVariableValue() {
        try {
            BaseCommunication communication = getCommunicationByName(communicationNameRef);

            delayMilliseconds(waitBefore);   
            try {
                communication.writeSingleRegister(registerRef, variableValue);
                testResult = FailureCodeUtil.OK;
                log = "Escrita do valor " + variableValue + " no registrador " + registerRef + " realizada com sucesso.";
            } catch (CommunicationException e) {
                log = "Falha na comunicação com " + communicationNameRef;
                setFailureCommandLog(FailureCodeUtil.FALHA_COMUNICACAO, log);
                return;
            }
            delayMilliseconds(waitAfter); 
        } catch (ObjectNotFoundException e) {
            log = "Comunicação com nome " + communicationNameRef + " não foi encontrado, verificar se a rotina de teste está correta";
            setFailureCommandLog(FailureCodeUtil.OBJECT_NOT_FOUND, log);
        }
    }

    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.VARIABLE_WRITE;
    }
}
