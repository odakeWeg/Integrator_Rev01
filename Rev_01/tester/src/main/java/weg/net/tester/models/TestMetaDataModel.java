package weg.net.tester.models;

import java.util.HashMap;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import weg.net.tester.facade.datacenter.SapConnector;
import weg.net.tester.tag.TagList;

public class TestMetaDataModel {
    public static TagList tagList;
    public static String testName;
    public static boolean[] isPositionEnabled;
    public static boolean exitFlag;
    public static SimpMessagingTemplate template;
    public static HashMap<String, String> sapConnector; 
    //@Todo: must do it multi-threaded

    public static int[] testStep;


    //private String dataStore;   //Hashmap? how to check values that wrote? manually || automatically
    //Tags wont be visible to each other

    public TestMetaDataModel(int qnt) {
        this.exitFlag = false;
        this.isPositionEnabled = initiatePosition(qnt);
        this.testStep = new int[qnt];
    }

    public boolean[] initiatePosition(int qnt) {
        boolean[] array = new boolean[qnt];
        for (int i = 0; i < array.length; i++) {
            array[i] = true;
        }
        return array;
    }
}
