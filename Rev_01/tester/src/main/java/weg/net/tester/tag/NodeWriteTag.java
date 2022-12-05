package weg.net.tester.tag;

import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.exception.ObjectNotFoundException;
import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.utils.TagIdentifierUtil;

@Getter
@Setter
public abstract class NodeWriteTag extends ParentTag {
    protected String tagIdentifier = TagIdentifierUtil.WRITE;

    protected void delayMilliseconds(int wait) {
        try {
            TimeUnit.MILLISECONDS.sleep(wait);
        } catch (InterruptedException e) {
        }
    }

    protected BaseCommunication getCommunicationByName(String name) throws ObjectNotFoundException {
        NodeCommunicationTag communicationTag;
        for (int i = 0; i < TestMetaDataModel.tagList.getList().size(); i++) {
            if (TestMetaDataModel.tagList.getList().get(i).getTagIdentifier().equals(TagIdentifierUtil.COMMUNICATION)) {
                communicationTag = (NodeCommunicationTag) TestMetaDataModel.tagList.getList().get(i);
                if(communicationTag.communicationName.equals(name)) {
                    return communicationTag.connection;
                }
            }
        }
        throw new ObjectNotFoundException("Objeto de comunicação não encontrado!");
    }
}
