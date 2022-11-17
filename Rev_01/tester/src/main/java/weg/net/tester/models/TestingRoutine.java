package weg.net.tester.models;

import java.util.List;

import weg.net.tester.converter.TagListUnmarshal;
import weg.net.tester.tag.BaseTag;

public class TestingRoutine {
    //private List<BaseTag> baseTagList;    //Not needed by now
    private String fileName;

    public TestingRoutine(String fileName) {
        this.fileName = fileName;
    }

    public List<BaseTag> getRoutine() {
        TagListUnmarshal tagListUnmarshal = new TagListUnmarshal();
        return tagListUnmarshal.getRoutineFromFileName(fileName).getList();
    }
}
