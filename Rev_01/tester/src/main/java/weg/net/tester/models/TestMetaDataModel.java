package weg.net.tester.models;

import weg.net.tester.tag.TagList;

public class TestMetaDataModel {
    public static TagList tagList;
    public static String testName;
    public static boolean[] isPositionEnabled;
    public static boolean exitFlag;
    //@Todo: must do it multi-threaded

    public static int[] testStep;


    //private String dataStore;   //Hashmap? how to check values that wrote? manually || automatically
    //Tags wont be visible to each other

    public TestMetaDataModel(int qnt) {
        this.exitFlag = false;
        this.isPositionEnabled = new boolean[qnt];
        this.testStep = new int[qnt];

        //@Todo: Make it automatic
        for (int i = 0; i < this.isPositionEnabled.length; i++) {
            this.isPositionEnabled[i] = true;
        }   
    }
}
