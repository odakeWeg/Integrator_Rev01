package weg.net.tester.tag;

import java.util.concurrent.TimeUnit;

import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.utils.TagIdentifierUtil;

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
        for (int i = 0; i < TestMetaDataModel.tagList.size(); i++) {
            if (TestMetaDataModel.tagList.get(i).getTagIdentifier().equals(TagIdentifierUtil.COMMUNICATION)) {
                communicationTag = (NodeCommunicationTag) TestMetaDataModel.tagList.get(i);
                if(communicationTag.communicationName.equals(name)) {
                    return communicationTag.connection;
                }
            }
        }
        return null;    //@Todo: null is not a good practice
    }
}
