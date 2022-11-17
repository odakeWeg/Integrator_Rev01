package weg.net.tester.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import weg.net.tester.communication.BaseCommunication;
import weg.net.tester.utils.TagIdentifierUtil;

@XmlAccessorType (XmlAccessType.FIELD)
public abstract class NodeCommunicationTag extends ParentTag {
    protected String tagIdentifier = TagIdentifierUtil.COMMUNICATION;
    protected String communicationName;

    @XmlTransient
    protected BaseCommunication connection;
}
