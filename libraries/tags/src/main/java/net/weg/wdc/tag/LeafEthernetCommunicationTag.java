package net.weg.wdc.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import net.weg.wdc.communication.BaseCommunication;
import net.weg.wdc.communication.EthernetCommunication;
import net.weg.wdc.exception.CommunicationException;
import net.weg.wdc.model.CommandLog;
import net.weg.wdc.utils.FailureCodeUtil;

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
            testResult = FailureCodeUtil.ok;
            log = "Setup de comunicação com " + communicationName + "realizado com sucesso.";
        } catch (CommunicationException e) {
            testResult = FailureCodeUtil.falhaComunicacao;
            log = "Falha no setup de comunicação com " + communicationName;
        }
        commandLog = new CommandLog(testResult, errorMessage, descricao, log);
    }
}
