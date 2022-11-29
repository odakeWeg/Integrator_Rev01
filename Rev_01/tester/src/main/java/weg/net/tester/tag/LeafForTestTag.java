package weg.net.tester.tag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeafForTestTag extends NodeMetaTestTag {

    //@Todo: Somente utilizado para teste de carga (múltiplas leituras podem ser implementada nas próprias tags)
    @Override
    public void executeCommand() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setTagName() {
        this.tagName = "forTest";   //if this tag is really gonna be used, update this field
    }
}
