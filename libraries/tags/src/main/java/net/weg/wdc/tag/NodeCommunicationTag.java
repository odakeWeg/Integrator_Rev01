package net.weg.wdc.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import net.weg.wdc.communication.BaseCommunication;
import net.weg.wdc.utils.TagIdentifierUtil;

@XmlAccessorType (XmlAccessType.FIELD)
public abstract class NodeCommunicationTag extends ParentTag {
    protected String tagIdentifier = TagIdentifierUtil.communication;
    protected String communicationName;

    @XmlTransient
    protected BaseCommunication connection;
}
