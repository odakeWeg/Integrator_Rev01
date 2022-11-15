package weg.net.tester.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.communication.EthernetCommunication;
import weg.net.tester.exception.CommunicationException;
import weg.net.tester.models.CommandLog;
import weg.net.tester.utils.FailureCodeUtil;

@XmlRootElement(name = "communicationEthernet")
@XmlAccessorType (XmlAccessType.FIELD)
public class LeafEthernetCommunicationTag extends NodeCommunicationTag {
    protected String ip;
    protected int port;
    protected int address;
    protected int timeBetweenCommand;

    @Override
    public void executeCommand() {
        connection = (BaseCommunication) new EthernetCommunication(ip, port, address, timeBetweenCommand);
        try {
            connection.startConnection();
            testResult = FailureCodeUtil.OK;
            log = "Setup de comunicação com " + communicationName + "realizado com sucesso.";
        } catch (CommunicationException e) {
            testResult = FailureCodeUtil.FALHA_SETUP_COMUNICACAO;
            log = "Falha no setup de comunicação com " + communicationName;
            isPositionEnable[this.position-1] = false;
        }
        commandLog = new CommandLog(testResult, errorMessage, descricao, log, action);
    }
}
