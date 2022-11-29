package weg.net.tester.tag;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.utils.TagNameUtil;

@Getter
@Setter
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
