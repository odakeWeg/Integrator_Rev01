package weg.net.tester.tag;

import java.util.concurrent.TimeUnit;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.utils.TagIdentifierUtil;

@Getter
@Setter
public abstract class NodeMetaTestTag extends ParentTag {
    protected String tagIdentifier = TagIdentifierUtil.TEST;

    @Override
    public boolean trivialTag() {
        return true;
    }

    protected void delayMilliseconds(int wait) {
        try {
            TimeUnit.MILLISECONDS.sleep(wait);
        } catch (InterruptedException e) {
        }
    }
}
