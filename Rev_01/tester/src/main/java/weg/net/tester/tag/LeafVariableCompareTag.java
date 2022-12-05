package weg.net.tester.tag;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.utils.TagNameUtil;

@Getter
@Setter
public class LeafVariableCompareTag extends NodeCompareTag {

    @Override
    protected void executeCommand() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.VARIABLE_COMPARE;
    }
    
}
