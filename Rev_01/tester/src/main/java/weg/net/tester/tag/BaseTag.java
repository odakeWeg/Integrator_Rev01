package weg.net.tester.tag;

import weg.net.tester.models.CommandLog;
import weg.net.tester.models.TestMetaDataModel;

public abstract class BaseTag {
    abstract protected void executeCommand();
    abstract public CommandLog command();
    abstract public int getId();    //Maybe not needed anymore -> @Getter
    abstract public String getTagIdentifier();  //Maybe not needed anymore -> @Getter
    abstract public String getTagName();  //Maybe not needed anymore -> @Getter
    abstract public void setTagName();  //Maybe not needed anymore -> @Setter
    abstract public int getPosition();  //Maybe not needed anymore -> @Getter
    //Maybe not needed anymore -> @Getter -> maybe needed to use it outside the class
}
