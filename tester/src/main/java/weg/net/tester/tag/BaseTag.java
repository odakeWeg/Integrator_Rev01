package weg.net.tester.tag;

import weg.net.tester.models.CommandLog;

public interface BaseTag {
    public void executeCommand();
    public CommandLog command();
    public int getId();
    public String getTagIdentifier();
}
