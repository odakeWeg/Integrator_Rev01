package weg.net.tester.tag;

import weg.net.tester.models.ens.EnsTagConfiguration;
import weg.net.tester.models.web.CommandLog;

public abstract class BaseTag {
    abstract protected void executeCommand();
    abstract public CommandLog command();
    abstract public int getId();
    abstract public String getTagIdentifier();
    abstract public String getTagName();
    abstract public void setTagName();
    abstract public int getPosition();
    abstract public EnsTagConfiguration getEnsTagConfiguration();
    abstract public boolean trivialTag();
    abstract public String getTestResult();
}
