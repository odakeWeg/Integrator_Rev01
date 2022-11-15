package weg.net.tester.tag;

import java.util.concurrent.TimeUnit;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.utils.TagIdentifierUtil;

@XmlAccessorType (XmlAccessType.FIELD)
public abstract class NodeWriteTag extends ParentTag {
    protected String tagIdentifier = TagIdentifierUtil.WRITE;

    protected void delayMilliseconds(int wait) {
        try {
            TimeUnit.MILLISECONDS.sleep(wait);
        } catch (InterruptedException e) {
            //@Todo
        }
    }

    protected BaseCommunication getCommunicationByName(String name) {
        NodeCommunicationTag communicationTag;
        for (int i = 0; i < tagList.size(); i++) {
            if (tagList.get(i).getTagIdentifier().equals(TagIdentifierUtil.COMMUNICATION)) {
                communicationTag = (NodeCommunicationTag) tagList.get(i);
                if(communicationTag.communicationName.equals(name)) {
                    return communicationTag.connection;
                }
            }
        }
        return null;    //@Todo: null is not a good practice
    }
}
