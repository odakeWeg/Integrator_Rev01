package weg.net.tester.tag;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.communication.EthernetCommunication;
import weg.net.tester.exception.CommunicationException;
import weg.net.tester.utils.FailureCodeUtil;
import weg.net.tester.utils.TagNameUtil;

@Getter
@Setter
public class LeafEthernetCommunicationTag extends NodeCommunicationTag {
    protected String ip;
    protected int port;
    protected int address;
    protected int timeBetweenCommand;
    protected int trials;

    @Override
    public void executeCommand() {
        connection = (BaseCommunication) new EthernetCommunication(ip, port, address, timeBetweenCommand, trials);
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
        this.tagName = TagNameUtil.ETHERNET_COMMUNICATION;
    }
}
