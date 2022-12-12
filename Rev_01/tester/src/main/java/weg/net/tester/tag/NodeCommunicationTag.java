package weg.net.tester.tag;

import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.exception.CommunicationException;
import weg.net.tester.utils.TagIdentifierUtil;

@Getter
@Setter
public abstract class NodeCommunicationTag extends ParentTag {
    //@Todo: which position fail should this one be attributed to?
    //@Todo: Make the maybe the main communication instantiate a different kind of class
    protected String tagIdentifier = TagIdentifierUtil.COMMUNICATION;
    protected String communicationName;

    //protected boolean assurence;
    //protected int offTime;
    //protected int qntOfTrials;
    protected int registerToFlag;
    protected int registerToSetTimeout;
    protected boolean mainCommunication;
    protected boolean enableCommunication;
    protected int waitBetweenFeedback;

    @JsonIgnore
    protected BaseCommunication connection;

    //@Todo: Turn into a cascade call, Parent-Node-Leaf
    protected void systemInitialFeedback(int timeout) throws CommunicationException, InterruptedException {
        if(connection.readSingleRegister(registerToFlag)==1) {
            TimeUnit.MILLISECONDS.sleep(waitBetweenFeedback);
            connection.writeSingleRegister(registerToFlag, 0);
        } else {
            TimeUnit.MILLISECONDS.sleep(waitBetweenFeedback);
            connection.writeSingleRegister(registerToFlag, 1);
        }
        TimeUnit.MILLISECONDS.sleep(waitBetweenFeedback);
        connection.writeSingleRegister(registerToSetTimeout, timeout);
    }

    protected void enableMain() {
        if (mainCommunication) {
            this.enableCommunication = true;
        }
    }

    /* 
    protected void systemEndFeedback() throws CommunicationException, InterruptedException {
        if(connection.readSingleRegister(registerToFlag)==1) {
            TimeUnit.MILLISECONDS.sleep(waitBetweenFeedback);
            connection.writeSingleRegister(registerToFlag, 0);
        } else {
            TimeUnit.MILLISECONDS.sleep(waitBetweenFeedback);
            connection.writeSingleRegister(registerToFlag, 1);
        }
    }
    */



    /* 
    //@Todo: ---------------------------->>>>>>
    //2) To which position does the fault goes? -> Could be one if its a product / could be everyone if its the giga
    //3) If position != 0  disable the position, else disable everything
    //4) Who should this function warn? -> This function should only be called by the main 
    //5) A variable should be set in the form to show which would be the failing communication
    //6) PLC could be connected and the main program could be off

    protected void assureConnection() {
        if (assurence) {
            //@Todo: Open thread for this stuff
            try {
                this.connection.writeSingleRegister(registerToFlag, value);
                testResult = FailureCodeUtil.OK;
                log = "Escrita do valor " + value + " no registrador " + registerToFlag + " realizada com sucesso.";
            } catch (CommunicationException e) {
                log = "Falha na comunicação com " + communicationName;
                setFailureCommandLog(FailureCodeUtil.FALHA_COMUNICACAO, log);
                return;
            }
            delayMilliseconds(offTime); 
        }
    }

    protected void toggle() {
        if (this.value==1) {
            this.value = 0;
        } else {
            this.value = 1;
        }
    }

    protected void delayMilliseconds(int wait) {
        try {
            TimeUnit.MILLISECONDS.sleep(wait);
        } catch (InterruptedException e) {
        }
    }
    */
}
