package weg.net.tester.tag;

import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.utils.TagNameUtil;

public class LeafUserInputTag extends NodeCompareTag {

    public LeafUserInputTag() {
        this.setTagName();
    }

    @Override
    public void executeCommand() {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.USER_INPUT;
    }

}
