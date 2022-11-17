package weg.net.tester.tag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import weg.net.tester.models.TestMetaDataModel;

@XmlRootElement(name = "userInput")
@XmlAccessorType (XmlAccessType.FIELD)
public class LeafUserInputTag extends NodeCompareTag {

    @Override
    public void executeCommand() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void setTagName() {
        this.tagName = "userInput";
    }

    @Override
    public TestMetaDataModel getTestMetaData() {
        return this.testMetaData;
    }

}
