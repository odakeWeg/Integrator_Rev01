package weg.net.tester.models;

import java.util.List;

import weg.net.tester.tag.BaseTag;

public class TestMetaDataModel {
    public static List<BaseTag> tagList;
    public static String testName;
    public static boolean[] isPositionEnabled;
    //@Todo: must do it multi-threaded
    //private int[] testStep;

    public static int testStep;


    //private String dataStore;   //Hashmap? how to check values that wrote? manually || automatically
    //Tags wont be visible to each other

}
