package weg.net.tester.tag;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.utils.TagIdentifierUtil;

@Getter
@Setter
public abstract class NodeMetaTestTag extends ParentTag {
    protected String tagIdentifier = TagIdentifierUtil.TEST;
}
