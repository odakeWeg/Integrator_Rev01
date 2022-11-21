package weg.net.tester.tag;

import lombok.Getter;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.FailureCodeUtil;
import weg.net.tester.utils.TagNameUtil;

@Getter
public class LeafTestTag extends NodeMetaTestTag {
    private String testName;

    public LeafTestTag() {
        this.setTagName();
    }

    @Override
    public void executeCommand() {
        testResult = FailureCodeUtil.OK;
        log = "Teste " + testName + "iniciado...";
        action = ActionCommandUtil.INIT;
    }

    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.TEST;
    }
}
