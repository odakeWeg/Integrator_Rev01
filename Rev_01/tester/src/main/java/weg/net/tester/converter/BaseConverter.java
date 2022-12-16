package weg.net.tester.converter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.core.JsonProcessingException;

import weg.net.tester.exception.TestUnmarshalingException;
import weg.net.tester.tag.TagList;

public interface BaseConverter {
    public TagList getRoutineFromFileName(String fileName) throws TestUnmarshalingException;
    public String objToJsonStringConverter(TagList tagList) throws JsonProcessingException;
    public JSONObject objToJsonObjectConverter(TagList tagList) throws JsonProcessingException, ParseException; 
    public JSONArray objToJsonArrayConverter(TagList tagList) throws JsonProcessingException, ParseException; 
}

