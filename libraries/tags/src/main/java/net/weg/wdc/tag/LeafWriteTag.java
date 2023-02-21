package net.weg.wdc.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import net.weg.wdc.communication.BaseCommunication;
import net.weg.wdc.exception.CommunicationException;
import net.weg.wdc.utils.FailureCodeUtil;

@XmlRootElement(name = "write")
@XmlAccessorType (XmlAccessType.FIELD)
public class LeafWriteTag extends NodeWriteTag {

    private String communicationName;
    private int register;
    private String registerName;
    private int value;
    private int waitBefore;
    private int waitAfter;
    
    @Override
    public void executeCommand() {
        BaseCommunication communication = getCommunicationByName(communicationName);
        delayMilliseconds(waitBefore);                                 
        if(communication == null) {
            testResult = FailureCodeUtil.objectNotFound;
            log = "Comunicação com nome " + communicationName + " não foi encontrado, verificar se a rotina de teste está correta";
        } else {
            try {
                communication.writeSingleRegister(register, value);
                testResult = FailureCodeUtil.ok;
                log = "Escrita do valor " + value + " no registrador " + register + " realizada com sucesso.";
            } catch (CommunicationException e) {
                testResult = FailureCodeUtil.falhaComunicacao;
                log = "Falha na comunicação com " + communicationName;
            }
        }
        delayMilliseconds(waitAfter); 
    }
    
}
