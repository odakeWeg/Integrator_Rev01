package weg.net.tester;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import weg.net.tester.communication.EthernetCommunication;
import weg.net.tester.exception.CommunicationException;

public class CommunicationStressTest {
    //@Todo: Doesnt have gateway open
    /* 
    @Test
    public void ethernetCommunicationStress() throws CommunicationException, InterruptedException {
        EthernetCommunication ethernet = new EthernetCommunication("192.168.0.10", 502, 255, 1, 1);
        ethernet.startConnection();
        while (true) {
            try {
                System.out.println("Registrador valor: " + ethernet.readSingleRegister(8000));
                TimeUnit.MILLISECONDS.sleep(3000);
            } catch (CommunicationException e) {
                System.out.println("Comm erro");
                TimeUnit.MILLISECONDS.sleep(3000);
            }
        }
    }
    */
}
