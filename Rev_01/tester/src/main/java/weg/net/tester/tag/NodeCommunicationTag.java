package weg.net.tester.tag;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.utils.TagIdentifierUtil;

@Getter
@Setter
public abstract class NodeCommunicationTag extends ParentTag {
    protected String tagIdentifier = TagIdentifierUtil.COMMUNICATION;
    protected String communicationName;

    @JsonIgnore
    protected BaseCommunication connection;
}
