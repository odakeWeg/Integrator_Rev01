package net.weg.wdc.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import net.weg.wdc.utils.TagIdentifierUtil;

@XmlAccessorType (XmlAccessType.FIELD)
public abstract class NodeMetaTestTag extends ParentTag {
    protected String tagIdentifier = TagIdentifierUtil.test;
}
