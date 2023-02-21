package weg.net.tester.tag;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeafForTestTag extends NodeMetaTestTag {

    //@Todo: Somente utilizado para teste de carga (múltiplas leituras podem ser implementada nas próprias tags)
    @Override
    public void executeCommand() {
     //Ideia 1) One loop that reads every variable in a cyclic way
     //Ideia 2) Multiple threads that read the data syncronously but syncronise in the end and shares the same variables
     //Ideia 3) Two loops in one thread, to capture wave forms of different variable, maybe better only one variable and use more tags in the routine   

     startMonitoringVariables();
    }

    private void startMonitoringVariables() {
        //1) 
    }

    @Override
    public void setTagName() {
        this.tagName = "forTest";   //if this tag is really gonna be used, update this field
    }
}
