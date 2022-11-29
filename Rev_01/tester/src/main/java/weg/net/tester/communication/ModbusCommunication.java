package weg.net.tester.communication;

import java.io.IOException;

import weg.net.tester.exception.CommunicationException;
import weg.net.tester.utils.NetworkSettingsUtil;

import net.weg.wcomm.modbus.NegativeConfirmationException;
import net.weg.wcomm.modbus.Register;
import net.weg.wcomm.modbus.exception.ModbusExceptionResponseException;
import net.weg.wcomm.modbus.exception.ModbusUnexpectedResponseException;
import net.weg.wcomm.modbus.tcp.client.ModbusTCPHelper;
import net.weg.wcomm.modbus.tcp.client.ModbusTCPMaster;
import net.weg.wcomm.serial.SComm;

public class ModbusCommunication implements BaseCommunication{
    private SComm serialSettings;
	private ModbusTCPHelper serialModbusCommunication;

	private String portName;
    private int baudRate;
    private int dataBits;
    private int stopBits;
    private String parity;
    private int timeoutComm;
    private  int address;

	public ModbusCommunication(String portName, int baudRate, int dataBits, int stopBits, String parity, int timeoutComm, int address) {
		this.portName = portName;
        this.baudRate = baudRate;
        this.dataBits = dataBits;
        this.stopBits = stopBits;
        this.parity = parity;
        this.timeoutComm = timeoutComm;
        this.address = address;
	}

    @Override
    public void startConnection() throws CommunicationException {
        ModbusTCPMaster master = new ModbusTCPMaster.Builder().hostAddress(NetworkSettingsUtil.HOST_ADDRESS).port(NetworkSettingsUtil.PORT).build();
		if (this.serialSettings != null) {
			serialSettings.closePort();
		}
		try {
			serialModbusCommunication = new ModbusTCPHelper(master);
			serialModbusCommunication.connect();
			serialModbusCommunication.subscribeClient("Serial/"+ portName + "/Modbus-RTU/@" + address+ "#" + baudRate + "#" + dataBits + "#" + stopBits + "#" + parity + "#0#50#" + timeoutComm + "#40");
		} catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException | IOException e) {
			throw new CommunicationException("Falha na conexão serial!");
		}
    }

    @Override
	public int readSingleRegister(int register) throws CommunicationException {
		Register[] registers;
		int read;
		try {
			registers = serialModbusCommunication.readHoldingRegisters((short) register, (short) 1);
			read = registers[0].intValue();
			
		} catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException e) {
			throw new CommunicationException("Falha na leitura dos registradores");
		}

		return read;
	}

    @Override
	public int[] readMultipleRegisters(int startingAddress, int quantityOfRegisters) throws CommunicationException {
		Register[] registers;
		int[] reads = new int[quantityOfRegisters];
		try {
			registers = serialModbusCommunication.readHoldingRegisters((short) startingAddress, (short) quantityOfRegisters);
			for (int i = 0; i < registers.length; i++) {
				reads[i] = registers[i].intValue();
			}
		} catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException e) {
			throw new CommunicationException("Falha na leitura dos registradores");
		}

		return reads;
	}

	@Override
	public void writeSingleRegister(int registerAddress, int registerValue) throws CommunicationException {
		try {
			serialModbusCommunication.writeSingleRegister((short) registerAddress, (short) registerValue);
		} catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException e) {
			throw new CommunicationException("Falha na escrita dos registradores");
		}
	}

	@Override
	public void writeMultipleRegister(int initialRegister, int[] registersValue) throws CommunicationException {
		short[] shortRegisterValues = new short[registersValue.length];
		for (int i = 0; i < registersValue.length; i++) {
			shortRegisterValues[i] = (short) registersValue[i];
		}
		try {
			serialModbusCommunication.writeMultipleRegisters((short) initialRegister,  shortRegisterValues);
		} catch (NegativeConfirmationException | ModbusExceptionResponseException | ModbusUnexpectedResponseException e) {
			throw new CommunicationException("Falha na escrita dos registradores");
		}
	}

	@Override
	public void closeCommunication() {
		if (serialSettings != null) {
			serialSettings.closePort();
		}
	}

	@Override
	public void writeStringInRegister(int startingAddress, String stringToWrite) throws CommunicationException {
		// TODO Auto-generated method stub
		
	}

}
