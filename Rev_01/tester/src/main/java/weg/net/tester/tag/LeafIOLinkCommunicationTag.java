package weg.net.tester.tag;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.communication.IOLinkCommunication;
import weg.net.tester.exception.CommunicationException;
import weg.net.tester.utils.FailureCodeUtil;
import weg.net.tester.utils.TagNameUtil;

@Getter
@Setter
public class LeafIOLinkCommunicationTag extends NodeCommunicationTag {
    protected String communicationName;
    protected String ip;
    protected int port;
    protected int address;
    protected int timeBetweenCommand;

    @Override
    public void executeCommand() {
        connection = (BaseCommunication) new IOLinkCommunication(ip, port, address, timeBetweenCommand);
        try {
            connection.startConnection();
            testResult = FailureCodeUtil.OK;
            log = "Setup de comunicação com " + communicationName + " realizado com sucesso.";
        } catch (CommunicationException e) {
            log = "Falha no setup de comunicação com " + communicationName;
            setFailureCommandLog(FailureCodeUtil.FALHA_SETUP_COMUNICACAO, log);
        }
        this.enableMain();
    }

    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.IOLINK_COMMUNICATION;
    }
}
