package weg.net.tester.tag;

import weg.net.tester.models.CommandLog;
import weg.net.tester.models.TestMetaDataModel;

public abstract class BaseTag {
    abstract protected void executeCommand();
    abstract public CommandLog command();
    abstract public int getId();
    abstract public String getTagIdentifier();
    abstract public String getTagName();
    abstract public void setTagName();
    abstract public TestMetaDataModel getTestMetaData();
}
