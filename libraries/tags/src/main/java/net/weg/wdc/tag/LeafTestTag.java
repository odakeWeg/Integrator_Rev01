package net.weg.wdc.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import net.weg.wdc.utils.FailureCodeUtil;

@XmlRootElement(name = "test")
@XmlAccessorType (XmlAccessType.FIELD)
public class LeafTestTag extends NodeMetaTestTag {

    private String testName;

    @Override
    public void executeCommand() {
        testResult = FailureCodeUtil.ok;
        log = "Teste " + testName + "iniciado...";
        action = "Iniciar";
    }
    
}
