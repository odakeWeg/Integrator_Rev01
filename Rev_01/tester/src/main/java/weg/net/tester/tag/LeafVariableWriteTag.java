package weg.net.tester.tag;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.utils.TagNameUtil;

@Getter
@Setter
public class LeafVariableWriteTag extends NodeWriteTag {

    @Override
    protected void executeCommand() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.WRITE;
    }
    
}
