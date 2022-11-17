package weg.net.tester.tag;

import java.util.concurrent.TimeUnit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.utils.TagIdentifierUtil;

@XmlAccessorType (XmlAccessType.FIELD)
public abstract class NodeCompareTag extends ParentTag {
    protected String tagIdentifier = TagIdentifierUtil.COMPARE;

    protected void delayMilliseconds(int wait) {
        try {
            TimeUnit.MILLISECONDS.sleep(wait);
        } catch (InterruptedException e) {
            //@Todo
        }
    }

    protected BaseCommunication getCommunicationByName(String name) {
        NodeCommunicationTag communicationTag;
        for (int i = 0; i < testMetaData.getTagList().size(); i++) {
            if (testMetaData.getTagList().get(i).getTagIdentifier().equals(TagIdentifierUtil.COMMUNICATION)) {
                communicationTag = (NodeCommunicationTag) testMetaData.getTagList().get(i);
                if(communicationTag.communicationName.equals(name)) {
                    return communicationTag.connection;
                }
            }
        }
        return null;    //@Todo: null is not a good practice
    }
}
