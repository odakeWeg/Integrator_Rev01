package net.weg.wdc.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import net.weg.wdc.communication.BaseCommunication;
import net.weg.wdc.communication.ModbusCommunication;
import net.weg.wdc.exception.CommunicationException;
import net.weg.wdc.model.CommandLog;
import net.weg.wdc.utils.FailureCodeUtil;

@XmlRootElement(name = "communicationModbus")
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
            testResult = FailureCodeUtil.ok;
            log = "Setup de comunicação com " + communicationName + "realizado com sucesso.";
        } catch (CommunicationException e) {
            testResult = FailureCodeUtil.falhaSetupComunicacao;
            log = "Falha no setup de comunicação com " + communicationName;
        }
        commandLog = new CommandLog(testResult, errorMessage, descricao, log);
        //wait response here with a timeouted thread
    }

    //Create controller here
}
