package net.weg.wdc.communication;

import net.weg.wdc.exception.CommunicationException;

public interface BaseCommunication {
    public void startConnection() throws CommunicationException;
    public int readSingleRegister(int register) throws CommunicationException;
    public int[] readMultipleRegisters(int startingAddress, int quantityOfRegisters) throws CommunicationException;
    public void writeSingleRegister(int registerAddress, int registerValue) throws CommunicationException;
    public void writeMultipleRegister(int initialRegister, int[] registersValue) throws CommunicationException;
    public void writeStringInRegister(int startingAddress, String stringToWrite) throws CommunicationException;
    public void closeCommunication();
}
