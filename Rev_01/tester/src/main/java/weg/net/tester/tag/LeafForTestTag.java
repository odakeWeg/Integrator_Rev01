package weg.net.tester.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import weg.net.tester.models.TestMetaDataModel;

@XmlRootElement(name = "forTest")
@XmlAccessorType (XmlAccessType.FIELD)
public class LeafForTestTag extends NodeMetaTestTag {

    //@Todo: Somente utilizado para teste de carga (múltiplas leituras podem ser implementada nas próprias tags)
    @Override
    public void executeCommand() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setTagName() {
        this.tagName = "forTest";
    }

    @Override
    public TestMetaDataModel getTestMetaData() {
        return this.testMetaData;
    }
}
