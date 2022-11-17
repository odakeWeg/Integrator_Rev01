package weg.net.tester.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.communication.IOLinkCommunication;
import weg.net.tester.exception.CommunicationException;
import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.utils.FailureCodeUtil;

@XmlRootElement(name = "IOLinkCommunication")
@XmlAccessorType (XmlAccessType.FIELD)
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
            log = "Setup de comunicação com " + communicationName + "realizado com sucesso.";
        } catch (CommunicationException e) {
            testResult = FailureCodeUtil.FALHA_SETUP_COMUNICACAO;
            log = "Falha no setup de comunicação com " + communicationName;
            testMetaData.getIsPositionEnabled()[this.position-1] = false;
        }
        //commandLog = new CommandLog(testResult, errorMessage, descricao, log, action);
    }

    @Override
    public void setTagName() {
        this.tagName = "IOLinkCommunication";
    }

    @Override
    public TestMetaDataModel getTestMetaData() {
        return this.testMetaData;
    }
}
