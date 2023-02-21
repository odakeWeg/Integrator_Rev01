package weg.net.tester.tag;

import weg.net.tester.utils.TagNameUtil;

public class LeafWaitTestTag extends NodeMetaTestTag{

    protected int testWait;

    @Override
    protected void executeCommand() {
        delayMilliseconds(testWait);
    }

    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.REGISTER_COMPARE;
    }
    
}
