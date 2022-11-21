package weg.net.tester.tag;

import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.exception.CommunicationException;
import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.utils.FailureCodeUtil;
import weg.net.tester.utils.TagNameUtil;

public class LeafWriteTag extends NodeWriteTag {

    private String communicationName;
    private int register;
    private String registerName;
    private int value;
    private int waitBefore;
    private int waitAfter;

    public LeafWriteTag() {
        this.setTagName();
    }
    
    @Override
    public void executeCommand() {
        BaseCommunication communication = getCommunicationByName(communicationName);
        delayMilliseconds(waitBefore);                                 
        if(communication == null) {
            testResult = FailureCodeUtil.OBJECT_NOT_FOUND;
            log = "Comunicação com nome " + communicationName + " não foi encontrado, verificar se a rotina de teste está correta";
        } else {
            try {
                communication.writeSingleRegister(register, value);
                testResult = FailureCodeUtil.OK;
                log = "Escrita do valor " + value + " no registrador " + register + " realizada com sucesso.";
            } catch (CommunicationException e) {
                testResult = FailureCodeUtil.FALHA_COMUNICACAO;
                log = "Falha na comunicação com " + communicationName;
                TestMetaDataModel.isPositionEnabled[this.position-1] = false;
            }
        }
        delayMilliseconds(waitAfter); 
    }
    
    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.WRITE;
    }

}
