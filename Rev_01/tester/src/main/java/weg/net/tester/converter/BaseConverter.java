package weg.net.tester.converter;

import java.io.File;

import com.fasterxml.jackson.core.JsonProcessingException;

import weg.net.tester.exception.TestUnmarshalingException;
import weg.net.tester.tag.TagList;

public interface BaseConverter {
    public TagList getRoutineFromFileName(String fileName) throws TestUnmarshalingException;
    //public TagList convertFromJsonToObj(File testFile) throws TestUnmarshalingException;
    public String ObjToJsonStringConverter(TagList tagList) throws JsonProcessingException;
}

