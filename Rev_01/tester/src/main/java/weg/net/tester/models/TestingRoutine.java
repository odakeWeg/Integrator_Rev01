package weg.net.tester.models;

import java.util.List;

import weg.net.tester.converter.JsonObjConverter;
import weg.net.tester.exception.TestUnmarshalingException;
import weg.net.tester.tag.BaseTag;
import weg.net.tester.tag.TagList;

public class TestingRoutine {
    //private List<BaseTag> baseTagList;    //Not needed by now
    private String fileName;

    public TestingRoutine(String fileName) {
        this.fileName = fileName;
    }

    public TagList getRoutine() throws TestUnmarshalingException {
        JsonObjConverter jsonObjConverter = new JsonObjConverter();
        return jsonObjConverter.getRoutineFromFileName(this.fileName);
    }
}
