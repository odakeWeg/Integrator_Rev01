package weg.net.tester.tag;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.FailureCodeUtil;
import weg.net.tester.utils.TagNameUtil;

@Getter
@Setter
public class LeafTestTag extends NodeMetaTestTag {
    private String testName;

    @Override
    public void executeCommand() {
        testResult = FailureCodeUtil.OK;
        log = "Teste " + testName + "iniciado...";
        action = ActionCommandUtil.INIT;

        TestMetaDataModel.testName = testName;
    }

    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.TEST;
    }
}
