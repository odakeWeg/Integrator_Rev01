package weg.net.tester.communication;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import weg.net.tester.exception.CommunicationException;
import weg.net.tester.utils.NetworkSettingsUtil;

import net.weg.wcomm.modbus.NegativeConfirmationException;
import net.weg.wcomm.modbus.Register;
import net.weg.wcomm.modbus.exception.ModbusExceptionResponseException;
import net.weg.wcomm.modbus.exception.ModbusUnexpectedResponseException;
import net.weg.wcomm.modbus.tcp.client.ModbusTCPHelper;
import net.weg.wcomm.modbus.tcp.client.ModbusTCPMaster;

//TODO: Desacoplar função criar função nova ao invés de chamar outra
//Refatorar tudo e botar para ele tentar comunicação n vezes


public class IOLinkCommunication implements BaseCommunication {

    private ModbusTCPHelper ethernetIOLinkCommunication;

    private String ip;
    private int address;
    private int port;
    private int timeBetweenCommand;
    private int toggleReading = 260;
    private int toggleWriting = 530;
    private boolean toggle = false;

    public IOLinkCommunication(String ip, int port, int address, int timeBetweenCommand) {
        this.ip = ip;
        this.address = address;
        this.port = port;
        this.timeBetweenCommand = timeBetweenCommand;
    }
    
    @Override
    public void startConnection() throws CommunicationException {
        ModbusTCPMaster master = new ModbusTCPMaster.Builder().hostAddress(NetworkSettingsUtil.HOST_ADDRESS).port(NetworkSettingsUtil.PORT).build();
        try {
			ethernetIOLinkCommunication = new ModbusTCPHelper(master);
			ethernetIOLinkCommunication.connect();
			ethernetIOLinkCommunication.subscribeClient("Ethernet/Modbus-TCP/" + ip + ":" + port + "/@" + address);
		} catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException | IOException ex) {
			throw new CommunicationException("Falha na conexão IOLink!");
		}
    }

    @Override
    public int readSingleRegister(int register) throws CommunicationException {
        Register[] registers;
		int read;
		try {
			registers = ethernetIOLinkCommunication.readHoldingRegisters((short) register, (short) 1);
			read = registers[0].intValue();
		} catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException e) {
			throw new CommunicationException("Falha na leitura dos registradores");
		}

		return read;
    }

    @Override
    public int[] readMultipleRegisters(int startingAddress, int quantityOfRegisters) throws CommunicationException {
        return readTargetRegister(startingAddress, quantityOfRegisters);
    }

    private int[] readTargetRegister(int address, int quantityOfRegisters) throws CommunicationException {
        short[] readingStructureRequest = { 1, (short) address, 0, (short) toggleReading, 0 };
        Register[] leituraRegister;
        int[] leitura;

        try {
            toggleCommandIdentifier();
            ethernetIOLinkCommunication.writeMultipleRegisters((short) 500, readingStructureRequest);
            TimeUnit.MILLISECONDS.sleep(timeBetweenCommand);
            leituraRegister = ethernetIOLinkCommunication.readHoldingRegisters((short) 6, (short) quantityOfRegisters);
            leitura = new int[leituraRegister.length];
            for (int i = 0; i < leituraRegister.length; i++) {
                leitura[i] = invertByte(String.valueOf(leituraRegister[i].intValue()));
                System.out.println("----" + leituraRegister[i].intValue());
            }
        } catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException | InterruptedException e) {
            throw new CommunicationException("Falha na leitura dos registradores");
        }
        int[] data = {concatenateIntInBinary(leitura)};
        return data;
    }

    public int concatenateIntInBinary(int[] serial) {
        String dataToSendBuffer = "";
        for (int i = 0; i < serial.length; i++) {
            dataToSendBuffer += fillLeftZeros(Integer.toBinaryString(serial[i]), 16);
        }
        return Integer.parseInt(dataToSendBuffer, 2);
    }

    @Override
    public void writeSingleRegister(int registerAddress, int registerValue) throws CommunicationException {
        short[] writingStructure = {(short) address, (short) registerAddress, 0, (short) toggleWriting, 16, (short) registerValue};
        try {
            toggleCommandIdentifier();
            ethernetIOLinkCommunication.writeMultipleRegisters((short) 500, writingStructure);
            TimeUnit.MILLISECONDS.sleep(timeBetweenCommand);
        } catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException | InterruptedException e) {
            throw new CommunicationException("Falha na leitura dos registradores");
        }
    }

    @Override
    public void writeMultipleRegister(int initialRegister, int[] registersValue) throws CommunicationException {
        short[] writingStructure = new short[5+registersValue.length];
        writingStructure[0] = (short) address;
        writingStructure[1] = (short) initialRegister;
        writingStructure[2] = 0;
        writingStructure[3] = (short) toggleWriting;
        writingStructure[4] = (short) 16;

        for (int i = 0; i < registersValue.length;i++) {
            writingStructure[5+i] = (short) invertByte(String.valueOf(registersValue[i]));
        }
        try {
            toggleCommandIdentifier();
            ethernetIOLinkCommunication.writeMultipleRegisters((short) 500, writingStructure);
            TimeUnit.MILLISECONDS.sleep(timeBetweenCommand);
        } catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException | InterruptedException e) {
            throw new CommunicationException("Falha na leitura dos registradores");
        }
    }

    @Override
    public void writeStringInRegister(int startingAddress, String stringToWrite) throws CommunicationException {
        int[] data = invertBytes(stringToWrite);
        //writeMultipleRegister(startingAddress, data);

        short[] writingStructure = {(short) address, (short) startingAddress, 0, (short) toggleWriting, 16, (short) data[0], (short) data[1]};
        try {
            toggleCommandIdentifier();
            ethernetIOLinkCommunication.writeMultipleRegisters((short) 500, writingStructure);
        } catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException e) {
            throw new CommunicationException("Falha na leitura dos registradores");
        }
    }

    @Override
    public void closeCommunication() {
        // TODO Auto-generated method stub
        
    }

    public int invertByte(String serial) {
        long serialNumber = Long.parseLong(serial);
        String binarySerialNumber = Long.toBinaryString(serialNumber);
        String dataToSendBuffer;
        int size = 16;

        binarySerialNumber = fillLeftZeros(binarySerialNumber, size);
        dataToSendBuffer = binarySerialNumber.substring(8) + binarySerialNumber.substring(0, 8);
        int dataToSend = Integer.parseInt(dataToSendBuffer, 2);

        return dataToSend;
    }

    public int[] invertBytes(String serial) {
        long serialNumber = Long.parseLong(serial);
        String binarySerialNumber = Long.toBinaryString(serialNumber);
        String[] dataToSendBuffer = new String[2];
        int size = 32;

        binarySerialNumber = fillLeftZeros(binarySerialNumber, size);
        dataToSendBuffer[0] = binarySerialNumber.substring(0, 16).substring(8) + binarySerialNumber.substring(0, 16).substring(0, 8);
        dataToSendBuffer[1] = binarySerialNumber.substring(16).substring(8) + binarySerialNumber.substring(16).substring(0, 8);
        int[] dataToSend = {Integer.parseInt(dataToSendBuffer[0], 2), Integer.parseInt(dataToSendBuffer[1], 2)};

        return dataToSend;
    }

    private String fillLeftZeros(String number, int size) {
        while(number.length() < size) {
            number = "0" + number;
        }
        return number;
    }

    private void toggleCommandIdentifier() {
        if(toggle) {
            toggleReading = 258;
            toggleWriting = 525;
            toggle = false;
        } else {
            toggleReading = 257;
            toggleWriting = 524;
            toggle = true;
        }
    }

}

/* 
public class IOLinkCommunication implements BaseCommunication {

    private ModbusTCPHelper ethernetIOLinkCommunication;

    private String ip;
    private int address;
    private int port;
    private int timeBetweenCommand;
    private int toggleReading = 260;
    private int toggleWriting = 530;
    private boolean toggle = false;

    public IOLinkCommunication(String ip, int port, int address, int timeBetweenCommand) {
        this.ip = ip;
        this.address = address;
        this.port = port;
        this.timeBetweenCommand = timeBetweenCommand;
    }
    
    @Override
    public void startConnection() throws CommunicationException {
        ModbusTCPMaster master = new ModbusTCPMaster.Builder().hostAddress(NetworkSettingsUtil.HOST_ADDRESS).port(NetworkSettingsUtil.PORT).build();
        try {
			ethernetIOLinkCommunication = new ModbusTCPHelper(master);
			ethernetIOLinkCommunication.connect();
			ethernetIOLinkCommunication.subscribeClient("Ethernet/Modbus-TCP/" + ip + ":" + port + "/@" + address);
		} catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException | IOException ex) {
			throw new CommunicationException("Falha na conexão IOLink!");
		}
    }

    @Override
    public int readSingleRegister(int register) throws CommunicationException {
        Register[] registers;
		int read;
		try {
			registers = ethernetIOLinkCommunication.readHoldingRegisters((short) register, (short) 1);
			read = registers[0].intValue();
		} catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException e) {
			throw new CommunicationException("Falha na leitura dos registradores");
		}

		return read;
    }

    @Override
    public int[] readMultipleRegisters(int startingAddress, int quantityOfRegisters) throws CommunicationException {
        return readTargetRegister(startingAddress, quantityOfRegisters);
    }

    private int[] readTargetRegister(int address, int quantityOfRegisters) throws CommunicationException {
        short[] readingStructureRequest = { 1, (short) address, 0, (short) toggleReading, 0 };
        Register[] leituraRegister;
        int[] leitura;

        try {
            toggleCommandIdentifier();
            ethernetIOLinkCommunication.writeMultipleRegisters((short) 500, readingStructureRequest);
            TimeUnit.MILLISECONDS.sleep(timeBetweenCommand);
            leituraRegister = ethernetIOLinkCommunication.readHoldingRegisters((short) 6, (short) quantityOfRegisters);
            leitura = new int[leituraRegister.length];
            for (int i = 0; i < leituraRegister.length; i++) {
                leitura[i] = invertByte(String.valueOf(leituraRegister[i].intValue()));
                System.out.println("----" + leituraRegister[i].intValue());
            }
        } catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException | InterruptedException e) {
            throw new CommunicationException("Falha na leitura dos registradores");
        }
        int[] data = {concatenateIntInBinary(leitura)};
        return data;
    }

    public int concatenateIntInBinary(int[] serial) {
        String dataToSendBuffer = "";
        for (int i = 0; i < serial.length; i++) {
            dataToSendBuffer += fillLeftZeros(Integer.toBinaryString(serial[i]), 16);
        }
        return Integer.parseInt(dataToSendBuffer, 2);
    }

    @Override
    public void writeSingleRegister(int registerAddress, int registerValue) throws CommunicationException {
        short[] writingStructure = {(short) address, (short) registerAddress, 0, (short) toggleWriting, 16, (short) registerValue};
        try {
            toggleCommandIdentifier();
            ethernetIOLinkCommunication.writeMultipleRegisters((short) 500, writingStructure);
            TimeUnit.MILLISECONDS.sleep(timeBetweenCommand);
        } catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException | InterruptedException e) {
            throw new CommunicationException("Falha na leitura dos registradores");
        }
    }

    @Override
    public void writeMultipleRegister(int initialRegister, int[] registersValue) throws CommunicationException {
        short[] writingStructure = new short[5+registersValue.length];
        writingStructure[0] = (short) address;
        writingStructure[1] = (short) initialRegister;
        writingStructure[2] = 0;
        writingStructure[3] = (short) toggleWriting;
        writingStructure[4] = (short) 16;

        for (int i = 0; i < registersValue.length;i++) {
            writingStructure[5+i] = (short) invertByte(String.valueOf(registersValue[i]));
        }
        try {
            toggleCommandIdentifier();
            ethernetIOLinkCommunication.writeMultipleRegisters((short) 500, writingStructure);
            TimeUnit.MILLISECONDS.sleep(timeBetweenCommand);
        } catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException | InterruptedException e) {
            throw new CommunicationException("Falha na leitura dos registradores");
        }
    }

    @Override
    public void writeStringInRegister(int startingAddress, String stringToWrite) throws CommunicationException {
        int[] data = invertBytes(stringToWrite);
        //writeMultipleRegister(startingAddress, data);

        short[] writingStructure = {(short) address, (short) startingAddress, 0, (short) toggleWriting, 16, (short) data[0], (short) data[1]};
        try {
            toggleCommandIdentifier();
            ethernetIOLinkCommunication.writeMultipleRegisters((short) 500, writingStructure);
        } catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException e) {
            throw new CommunicationException("Falha na leitura dos registradores");
        }
    }

    @Override
    public void closeCommunication() {
        // TODO Auto-generated method stub
        
    }

    public int invertByte(String serial) {
        long serialNumber = Long.parseLong(serial);
        String binarySerialNumber = Long.toBinaryString(serialNumber);
        String dataToSendBuffer;
        int size = 16;

        binarySerialNumber = fillLeftZeros(binarySerialNumber, size);
        dataToSendBuffer = binarySerialNumber.substring(8) + binarySerialNumber.substring(0, 8);
        int dataToSend = Integer.parseInt(dataToSendBuffer, 2);

        return dataToSend;
    }

    public int[] invertBytes(String serial) {
        long serialNumber = Long.parseLong(serial);
        String binarySerialNumber = Long.toBinaryString(serialNumber);
        String[] dataToSendBuffer = new String[2];
        int size = 32;

        binarySerialNumber = fillLeftZeros(binarySerialNumber, size);
        dataToSendBuffer[0] = binarySerialNumber.substring(0, 16).substring(8) + binarySerialNumber.substring(0, 16).substring(0, 8);
        dataToSendBuffer[1] = binarySerialNumber.substring(16).substring(8) + binarySerialNumber.substring(16).substring(0, 8);
        int[] dataToSend = {Integer.parseInt(dataToSendBuffer[0], 2), Integer.parseInt(dataToSendBuffer[1], 2)};

        return dataToSend;
    }

    private String fillLeftZeros(String number, int size) {
        while(number.length() < size) {
            number = "0" + number;
        }
        return number;
    }

    private void toggleCommandIdentifier() {
        if(toggle) {
            toggleReading = 258;
            toggleWriting = 525;
            toggle = false;
        } else {
            toggleReading = 257;
            toggleWriting = 524;
            toggle = true;
        }
    }

}
*/
