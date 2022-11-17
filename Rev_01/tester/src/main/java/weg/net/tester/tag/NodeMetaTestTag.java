package weg.net.tester.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

import weg.net.tester.utils.TagIdentifierUtil;

@XmlAccessorType (XmlAccessType.FIELD)
public abstract class NodeMetaTestTag extends ParentTag {
    protected String tagIdentifier = TagIdentifierUtil.TEST;
}
