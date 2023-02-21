package net.weg.wdc.tag;

import net.weg.wdc.model.CommandLog;

public interface BaseTag {
    public void executeCommand();
    public CommandLog command();
    public int getId();
    public String getTagIdentifier();
}
