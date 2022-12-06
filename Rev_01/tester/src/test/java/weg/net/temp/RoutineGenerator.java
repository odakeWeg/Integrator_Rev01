package weg.net.temp;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import weg.net.tester.converter.JsonObjConverter;
import weg.net.tester.exception.TestUnmarshalingException;
import weg.net.tester.tag.LeafEthernetCommunicationTag;
import weg.net.tester.tag.LeafRegisterCompareTag;
import weg.net.tester.tag.LeafTestTag;
import weg.net.tester.tag.LeafUserConfirmationTag;
import weg.net.tester.tag.LeafUserInputTag;
import weg.net.tester.tag.LeafVariableCompareTag;
import weg.net.tester.tag.LeafVariableWriteTag;
import weg.net.tester.tag.LeafWriteTag;
import weg.net.tester.tag.TagList;

public class RoutineGenerator {
    @Test
    public void jsonGenerator() throws TestUnmarshalingException, StreamWriteException, DatabindException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, ParseException {
        TagList tagList = new TagList();
        tagList.setList(new ArrayList<>());
        LeafTestTag leafTestTag = new LeafTestTag();
        leafTestTag.setId(0);
        leafTestTag.setTestName("teste 1");;
        leafTestTag.setTimeout(10);
        leafTestTag.setPosition(1);
        tagList.getList().add(leafTestTag);
        LeafEthernetCommunicationTag leafEthernetCommunicationTag = new LeafEthernetCommunicationTag();
        leafEthernetCommunicationTag.setId(1);
        leafEthernetCommunicationTag.setCommunicationName("PLC");
        leafEthernetCommunicationTag.setIp("192.168.0.10");
        leafEthernetCommunicationTag.setPort(502);
        leafEthernetCommunicationTag.setAddress(255);
        leafEthernetCommunicationTag.setTimeout(10);
        leafEthernetCommunicationTag.setPosition(1);
        tagList.getList().add(leafEthernetCommunicationTag);
        LeafTestTag leafTestTag2 = new LeafTestTag();
        leafTestTag2.setId(2);
        leafTestTag2.setTestName("teste 2");;
        leafTestTag2.setTimeout(10);
        leafTestTag2.setPosition(1);
        tagList.getList().add(leafTestTag2);
        LeafRegisterCompareTag leafRegisterCompareTag = new LeafRegisterCompareTag();
        leafRegisterCompareTag.setCommunicationNameOnTest("PLC");
        leafRegisterCompareTag.setCommunicationNameRef("PLC");
        leafRegisterCompareTag.setRegisterOnTest(8000);
        leafRegisterCompareTag.setRegisterRef(8000);
        leafRegisterCompareTag.setCalculateBy("Absolute");
        leafRegisterCompareTag.setTolerancy(10);
        leafRegisterCompareTag.setWaitBefore(10);
        leafRegisterCompareTag.setWaitAfter(10);
        leafRegisterCompareTag.setId(3);
        leafRegisterCompareTag.setTimeout(10);
        leafRegisterCompareTag.setPosition(1);
        tagList.getList().add(leafRegisterCompareTag);
        LeafWriteTag leafWriteTag = new LeafWriteTag();
        leafWriteTag.setId(4);
        leafWriteTag.setCommunicationName(null);
        leafWriteTag.setCommunicationName("PLC");
        leafWriteTag.setRegister(8004);
        leafWriteTag.setValue(1);
        leafWriteTag.setWaitBefore(10);
        leafWriteTag.setWaitAfter(10);
        leafWriteTag.setTimeout(10);
        leafWriteTag.setPosition(1);
        tagList.getList().add(leafWriteTag);
        LeafRegisterCompareTag leafRegisterCompareTag2 = new LeafRegisterCompareTag();
        leafRegisterCompareTag2.setCommunicationNameOnTest("PLC");
        leafRegisterCompareTag2.setCommunicationNameRef("PLC");
        leafRegisterCompareTag2.setRegisterOnTest(8000);
        leafRegisterCompareTag2.setRegisterRef(8000);
        leafRegisterCompareTag2.setCalculateBy("Absolute");
        leafRegisterCompareTag2.setTolerancy(100);
        leafRegisterCompareTag2.setWaitBefore(10);
        leafRegisterCompareTag2.setWaitAfter(10);
        leafRegisterCompareTag2.setId(5);
        leafRegisterCompareTag2.setTimeout(10);
        leafRegisterCompareTag2.setPosition(1);
        tagList.getList().add(leafRegisterCompareTag2);
        

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/test4.json"), tagList);

        JsonObjConverter converter = new JsonObjConverter();
        File file = new File("src/test/resources/test4.json");
        TagList tagList2 = converter.convertFromJsonToObj(file);
        Assert.assertEquals(tagList.getList().get(0).getId(), tagList2.getList().get(0).getId());
        Assert.assertEquals(tagList.getList().get(0).getTagName(), tagList2.getList().get(0).getTagName());
        Assert.assertEquals(tagList.getList().get(0).getTagIdentifier(), tagList2.getList().get(0).getTagIdentifier());
    }

    @Test
    public void userConfirmationTagTest() throws StreamWriteException, DatabindException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, ParseException {
        TagList tagList = new TagList();
        tagList.setList(new ArrayList<>());
        LeafTestTag leafTestTag = new LeafTestTag();
        leafTestTag.setId(0);
        leafTestTag.setTestName("teste 1");;
        leafTestTag.setTimeout(10);
        leafTestTag.setPosition(1);
        tagList.getList().add(leafTestTag);
        LeafUserConfirmationTag leafUserConfirmationTag = new LeafUserConfirmationTag();
        leafUserConfirmationTag.setId(1);
        leafUserConfirmationTag.setMessageToDisplay("My confirmation message");
        leafUserConfirmationTag.setTimeout(10);
        leafUserConfirmationTag.setPosition(1);
        tagList.getList().add(leafUserConfirmationTag);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/test5.json"), tagList);

        JsonObjConverter converter = new JsonObjConverter();
        File file = new File("src/test/resources/test5.json");
        TagList tagList2 = converter.convertFromJsonToObj(file);
        Assert.assertEquals(tagList.getList().get(0).getId(), tagList2.getList().get(0).getId());
        Assert.assertEquals(tagList.getList().get(0).getTagName(), tagList2.getList().get(0).getTagName());
        Assert.assertEquals(tagList.getList().get(0).getTagIdentifier(), tagList2.getList().get(0).getTagIdentifier());
    }

    @Test
    public void userInputTagTest() throws StreamWriteException, DatabindException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, ParseException {
        TagList tagList = new TagList();
        tagList.setList(new ArrayList<>());
        LeafTestTag leafTestTag = new LeafTestTag();
        leafTestTag.setId(0);
        leafTestTag.setTestName("teste 1");;
        leafTestTag.setTimeout(10);
        leafTestTag.setPosition(1);
        tagList.getList().add(leafTestTag);
        LeafEthernetCommunicationTag leafEthernetCommunicationTag = new LeafEthernetCommunicationTag();
        leafEthernetCommunicationTag.setId(1);
        leafEthernetCommunicationTag.setCommunicationName("PLC");
        leafEthernetCommunicationTag.setIp("192.168.0.10");
        leafEthernetCommunicationTag.setPort(502);
        leafEthernetCommunicationTag.setAddress(255);
        leafEthernetCommunicationTag.setTimeout(10);
        leafEthernetCommunicationTag.setPosition(1);
        tagList.getList().add(leafEthernetCommunicationTag);
        LeafUserInputTag leafUserInputTag = new LeafUserInputTag();
        leafUserInputTag.setId(2);
        leafUserInputTag.setMessageToDisplay("My Input message");
        leafUserInputTag.setTimeout(10);
        leafUserInputTag.setPosition(1);
        leafUserInputTag.setCommunicationNameRef("PLC");
        leafUserInputTag.setRegisterRef(8000);
        leafUserInputTag.setCalculateBy("Absolute");
        leafUserInputTag.setTolerancy(10);
        leafUserInputTag.setWaitAfter(1);
        leafUserInputTag.setWaitBefore(1);
        tagList.getList().add(leafUserInputTag);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/test6.json"), tagList);

        JsonObjConverter converter = new JsonObjConverter();
        File file = new File("src/test/resources/test6.json");
        TagList tagList2 = converter.convertFromJsonToObj(file);
        Assert.assertEquals(tagList.getList().get(0).getId(), tagList2.getList().get(0).getId());
        Assert.assertEquals(tagList.getList().get(0).getTagName(), tagList2.getList().get(0).getTagName());
        Assert.assertEquals(tagList.getList().get(0).getTagIdentifier(), tagList2.getList().get(0).getTagIdentifier());
    }

    @Test
    public void variableReadTest() throws StreamWriteException, DatabindException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, ParseException {
        TagList tagList = new TagList();
        tagList.setList(new ArrayList<>());
        LeafTestTag leafTestTag = new LeafTestTag();
        leafTestTag.setId(0);
        leafTestTag.setTestName("teste 1");;
        leafTestTag.setTimeout(10);
        leafTestTag.setPosition(1);
        tagList.getList().add(leafTestTag);
        LeafEthernetCommunicationTag leafEthernetCommunicationTag = new LeafEthernetCommunicationTag();
        leafEthernetCommunicationTag.setId(1);
        leafEthernetCommunicationTag.setCommunicationName("PLC");
        leafEthernetCommunicationTag.setIp("192.168.0.10");
        leafEthernetCommunicationTag.setPort(502);
        leafEthernetCommunicationTag.setAddress(255);
        leafEthernetCommunicationTag.setTimeout(10);
        leafEthernetCommunicationTag.setPosition(1);
        tagList.getList().add(leafEthernetCommunicationTag);
        LeafVariableCompareTag leafVariableCompareTag = new LeafVariableCompareTag();
        leafVariableCompareTag.setId(2);
        leafVariableCompareTag.setTimeout(10);
        leafVariableCompareTag.setPosition(1);
        leafVariableCompareTag.setCommunicationNameRef("PLC");
        leafVariableCompareTag.setRegisterRef(8000);
        leafVariableCompareTag.setCalculateBy("Absolute");
        leafVariableCompareTag.setTolerancy(10);
        leafVariableCompareTag.setVariableName("serial1");
        leafVariableCompareTag.setWaitAfter(1);
        leafVariableCompareTag.setWaitBefore(1);
        tagList.getList().add(leafVariableCompareTag);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/test7.json"), tagList);

        JsonObjConverter converter = new JsonObjConverter();
        File file = new File("src/test/resources/test7.json");
        TagList tagList2 = converter.convertFromJsonToObj(file);
        Assert.assertEquals(tagList.getList().get(0).getId(), tagList2.getList().get(0).getId());
        Assert.assertEquals(tagList.getList().get(0).getTagName(), tagList2.getList().get(0).getTagName());
        Assert.assertEquals(tagList.getList().get(0).getTagIdentifier(), tagList2.getList().get(0).getTagIdentifier());
    }

    @Test
    public void variableWriteTest() throws StreamWriteException, DatabindException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, ParseException {
        TagList tagList = new TagList();
        tagList.setList(new ArrayList<>());
        LeafTestTag leafTestTag = new LeafTestTag();
        leafTestTag.setId(0);
        leafTestTag.setTestName("teste 1");;
        leafTestTag.setTimeout(10);
        leafTestTag.setPosition(1);
        tagList.getList().add(leafTestTag);
        LeafEthernetCommunicationTag leafEthernetCommunicationTag = new LeafEthernetCommunicationTag();
        leafEthernetCommunicationTag.setId(1);
        leafEthernetCommunicationTag.setCommunicationName("PLC");
        leafEthernetCommunicationTag.setIp("192.168.0.10");
        leafEthernetCommunicationTag.setPort(502);
        leafEthernetCommunicationTag.setAddress(255);
        leafEthernetCommunicationTag.setTimeout(10);
        leafEthernetCommunicationTag.setPosition(1);
        tagList.getList().add(leafEthernetCommunicationTag);
        LeafVariableWriteTag leafVariableWriteTag = new LeafVariableWriteTag();
        leafVariableWriteTag.setId(2);
        leafVariableWriteTag.setTimeout(10);
        leafVariableWriteTag.setPosition(1);
        leafVariableWriteTag.setCommunicationNameRef("PLC");
        leafVariableWriteTag.setRegisterRef(8000);
        leafVariableWriteTag.setVariableName("serial1");
        leafVariableWriteTag.setWaitAfter(1);
        leafVariableWriteTag.setWaitBefore(1);
        tagList.getList().add(leafVariableWriteTag);
        LeafVariableCompareTag leafVariableCompareTag = new LeafVariableCompareTag();
        leafVariableCompareTag.setId(2);
        leafVariableCompareTag.setTimeout(10);
        leafVariableCompareTag.setPosition(1);
        leafVariableCompareTag.setCommunicationNameRef("PLC");
        leafVariableCompareTag.setRegisterRef(8000);
        leafVariableCompareTag.setCalculateBy("Absolute");
        leafVariableCompareTag.setTolerancy(10);
        leafVariableCompareTag.setVariableName("serial1");
        leafVariableCompareTag.setWaitAfter(1);
        leafVariableCompareTag.setWaitBefore(1);
        tagList.getList().add(leafVariableCompareTag);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/test7.json"), tagList);

        JsonObjConverter converter = new JsonObjConverter();
        File file = new File("src/test/resources/test7.json");
        TagList tagList2 = converter.convertFromJsonToObj(file);
        Assert.assertEquals(tagList.getList().get(0).getId(), tagList2.getList().get(0).getId());
        Assert.assertEquals(tagList.getList().get(0).getTagName(), tagList2.getList().get(0).getTagName());
        Assert.assertEquals(tagList.getList().get(0).getTagIdentifier(), tagList2.getList().get(0).getTagIdentifier());
    }
}
