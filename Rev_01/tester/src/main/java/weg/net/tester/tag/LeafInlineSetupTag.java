package weg.net.tester.tag;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.FailureCodeUtil;
import weg.net.tester.utils.TagNameUtil;

@Getter
@Setter
public class LeafInlineSetupTag extends NodeMetaTestTag {
    private boolean enableInline;

    @Override
    protected void executeCommand() {
        testResult = FailureCodeUtil.OK;
        if(enableInline) {
            log = "Inline habilitado";
        } else {
            log = "Inline desabilitado";
        }
        action = ActionCommandUtil.EXIBIT_VALUES;
    }

    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.INLINE;
    }
    
}
