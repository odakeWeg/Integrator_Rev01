package weg.net.tester.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.communication.ModbusCommunication;
import weg.net.tester.exception.CommunicationException;
import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.utils.FailureCodeUtil;

@XmlRootElement(name = "modbusCommunication")
@XmlAccessorType (XmlAccessType.FIELD)
public class LeafModbusCommunicationTag extends NodeCommunicationTag {
    protected String portName;
    protected int baudRate;
    protected int dataBits;
    protected int stopBits;
    protected String parity;
    protected int timeout;
    protected int address;

    @XmlTransient
    BaseCommunication connection;
    
    @Override
    public void executeCommand() {
        connection = (BaseCommunication) new ModbusCommunication(portName, baudRate, dataBits, stopBits, parity, timeout, address);
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
        this.tagName = "modbusCommunication";
    }

    @Override
    public TestMetaDataModel getTestMetaData() {
        return this.testMetaData;
    }
}
