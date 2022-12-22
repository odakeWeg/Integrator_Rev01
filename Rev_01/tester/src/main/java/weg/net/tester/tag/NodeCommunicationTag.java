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

    @Override
    public boolean trivialTag() {
        return false;
    }
}
