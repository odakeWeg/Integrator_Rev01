package weg.net.tester.tag;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.communication.ModbusCommunication;
import weg.net.tester.exception.CommunicationException;
import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.utils.FailureCodeUtil;
import weg.net.tester.utils.TagNameUtil;

@Getter
@Setter
public class LeafModbusCommunicationTag extends NodeCommunicationTag {
    protected String portName;
    protected int baudRate;
    protected int dataBits;
    protected int stopBits;
    protected String parity;
    protected int timeoutComm;
    protected int address;
    
    @Override
    public void executeCommand() {
        connection = (BaseCommunication) new ModbusCommunication(portName, baudRate, dataBits, stopBits, parity, timeoutComm, address);
        try {
            connection.startConnection();
            testResult = FailureCodeUtil.OK;
            log = "Setup de comunicação com " + communicationName + " realizado com sucesso.";
        } catch (CommunicationException e) {
            //testResult = FailureCodeUtil.FALHA_SETUP_COMUNICACAO;
            log = "Falha no setup de comunicação com " + communicationName;
            //TestMetaDataModel.isPositionEnabled[this.position-1] = false;
            //TestMetaDataModel.testStep[this.position-1] = this.id;
            //@Todo: takeOut
            setFailureCommandLog(FailureCodeUtil.FALHA_SETUP_COMUNICACAO, log);
        }
        this.enableMain();
    }

    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.MODBUS_COMMUNICATION;
    }
}
