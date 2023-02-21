package net.weg.wdc.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import net.weg.wdc.communication.BaseCommunication;
import net.weg.wdc.communication.IOLinkCommunication;
import net.weg.wdc.exception.CommunicationException;
import net.weg.wdc.model.CommandLog;
import net.weg.wdc.utils.FailureCodeUtil;

@XmlRootElement(name = "communicationIOLink")
@XmlAccessorType (XmlAccessType.FIELD)
public class LeafIOLinkCommunicationTag extends NodeCommunicationTag {
    private String communicationName;
    private String ip;
    private int port;
    private int address;
    private int timeBetweenCommand;

    @Override
    public void executeCommand() {
        connection = (BaseCommunication) new IOLinkCommunication(ip, port, address, timeBetweenCommand);
        try {
            connection.startConnection();
            testResult = FailureCodeUtil.ok;
            log = "Setup de comunicação com " + communicationName + "realizado com sucesso.";
        } catch (CommunicationException e) {
            testResult = FailureCodeUtil.falhaSetupComunicacao;
            log = "Falha no setup de comunicação com " + communicationName;
        }
        commandLog = new CommandLog(testResult, errorMessage, descricao, log);
    }

}
