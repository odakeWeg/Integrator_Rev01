package weg.net.tester.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.tag.BaseTag;

@Getter
@Setter
public class TestMetaDataModel {
    private List<BaseTag> tagList;
    private String testName;
    private boolean[] isPositionEnabled;
    //@Todo: must do it multi-threaded
    //private int[] testStep;

    private int testStep;


    //private String dataStore;   //Hashmap? how to check values that wrote? manually || automatically
    //Tags wont be visible to each other

}
