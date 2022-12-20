package weg.net.tester.tag;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.utils.TagIdentifierUtil;

@Getter
@Setter
public abstract class NodeEnsTag extends ParentTag {
    //@Todo: maybe one node for every integration system (unify Ens and Inline)
    protected String tagIdentifier = TagIdentifierUtil.ENS;
}
