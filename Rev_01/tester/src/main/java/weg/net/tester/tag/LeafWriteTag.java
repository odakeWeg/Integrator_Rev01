package weg.net.tester.tag;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.exception.CommunicationException;
import weg.net.tester.exception.ObjectNotFoundException;
import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.utils.FailureCodeUtil;
import weg.net.tester.utils.TagNameUtil;

@Getter
@Setter
public class LeafWriteTag extends NodeWriteTag {

    private String communicationName;
    private int register;
    private String registerName;
    private int value;
    private int waitBefore;
    private int waitAfter;
    
    @Override
    public void executeCommand() {
        try {
            BaseCommunication communication = getCommunicationByName(communicationName);

            delayMilliseconds(waitBefore);   
            try {
                communication.writeSingleRegister(register, value);
                testResult = FailureCodeUtil.OK;
                log = "Escrita do valor " + value + " no registrador " + register + " realizada com sucesso.";
            } catch (CommunicationException e) {
                log = "Falha na comunicação com " + communicationName;
                setFailureCommandLog(FailureCodeUtil.FALHA_COMUNICACAO, log);
                return;
            }
            delayMilliseconds(waitAfter); 
        } catch (ObjectNotFoundException e) {
            log = "Comunicação com nome " + communicationName + " não foi encontrado, verificar se a rotina de teste está correta";
            setFailureCommandLog(FailureCodeUtil.OBJECT_NOT_FOUND, log);
        }
    }
    
    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.WRITE;
    }

}
