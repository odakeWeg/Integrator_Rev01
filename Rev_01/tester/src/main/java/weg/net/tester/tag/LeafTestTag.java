package weg.net.tester.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.FailureCodeUtil;

@XmlRootElement(name = "test")
@XmlAccessorType (XmlAccessType.FIELD)
public class LeafTestTag extends NodeMetaTestTag {
    private String testName;

    @Override
    public void executeCommand() {
        testResult = FailureCodeUtil.OK;
        log = "Teste " + testName + "iniciado...";
        action = ActionCommandUtil.INIT;
    }

    @Override
    public void setTagName() {
        this.tagName = "test";
    }

    @Override
    public TestMetaDataModel getTestMetaData() {
        return this.testMetaData;
    }
}
