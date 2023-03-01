package weg.net.tester.converter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import weg.net.tester.exception.TestUnmarshalingException;
import weg.net.tester.repositories.TestingRoutineRepository;
import weg.net.tester.tag.BaseTag;
import weg.net.tester.tag.TagList;
import weg.net.tester.utils.FilePathUtil;
import weg.net.tester.utils.JsonTagAttributesUtil;

public class JsonObjConverter implements BaseConverter {
    @Autowired
    private TestingRoutineRepository testingRoutineRepository;
    
    @Override
    public TagList getRoutineFromFileName(String fileName) throws TestUnmarshalingException {
        TagList TagList;
        try {
            File testFile = new File(fileName);
            TagList = convertFromJsonToObj(testFile);
            return TagList;
        } catch (Exception e) {
            throw new TestUnmarshalingException("Falha na aquisição da rotina de teste!");
        }
    }

    public TagList convertFromJsonToObj(File testFile) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, FileNotFoundException, IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(testFile));
        JSONObject jsonObject = (JSONObject) obj;
        JSONArray routines = (JSONArray) jsonObject.get(JsonTagAttributesUtil.LIST);

        TagList tagList = new TagList();
        ObjectMapper mapper = new ObjectMapper();
        JSONObject json;
        String className;
        Iterator iterator = routines.iterator();
        while (iterator.hasNext()) {
            json = (JSONObject) iterator.next();
            className = (String) json.get(JsonTagAttributesUtil.TAG_NAME);
            BaseTag baseTag = (BaseTag)  Class.forName(FilePathUtil.TAG_FILES_PATH + className).getDeclaredConstructor().newInstance();
            BaseTag dtoObject = mapper.readValue(json.toString(), baseTag.getClass());
            tagList.getList().add(dtoObject);
        }

        return tagList;
    }

    @Override
    public String objToJsonStringConverter(TagList tagList) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(tagList);
    }

    @Override
    public JSONObject objToJsonObjectConverter(TagList tagList) throws JsonProcessingException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        JSONParser parser = new JSONParser();  
        return (JSONObject) parser.parse(mapper.writeValueAsString(tagList));
    }

    @Override
    public JSONArray objToJsonArrayConverter(TagList tagList) throws JsonProcessingException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        JSONObject jsonObject = objToJsonObjectConverter(tagList);

        return (JSONArray) jsonObject.get(JsonTagAttributesUtil.LIST);
    }

    private String getRoutineJson(String fileName) {
        return testingRoutineRepository.findByFileName(fileName).getRoutineJson().toString();
    }
}


/*
@Override
public TagList getRoutineFromFileName(String fileName) throws TestUnmarshalingException {
    TagList TagList;
    try {
        File testFile = new File(fileName);
        TagList = convertFromJsonToObj(testFile);
        return TagList;
    } catch (Exception e) {
        downloadRoutineFromDataBase(fileName);
        return getRoutineFromFileName(fileName);
    }
} 

private void downloadRoutineFromDataBase(String fileName) throws TestUnmarshalingException {
    try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(FilePathUtil.TEST_ROUTINE_PATH + fileName + ".json"));
        writer.write(getRoutineJson(fileName));
        writer.close();
    } catch (Exception e) {
        throw new TestUnmarshalingException("Falha na aquisição da rotina de teste!");
    }
}
*/