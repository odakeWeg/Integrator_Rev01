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
        leafEthernetCommunicationTag.setAddress(1);
        leafEthernetCommunicationTag.setTimeout(10);
        leafTestTag.setPosition(1);
        tagList.getList().add(leafEthernetCommunicationTag);
        LeafRegisterCompareTag leafRegisterCompareTag = new LeafRegisterCompareTag();
        leafRegisterCompareTag.setCommunicationNameOnTest("PLC");
        leafRegisterCompareTag.setCommunicationNameRef("PLC");
        leafRegisterCompareTag.setRegisterOnTest(8000);
        leafRegisterCompareTag.setRegisterRef(8000);
        leafRegisterCompareTag.setCalculateBy("Absolute");
        leafRegisterCompareTag.setTolerancy(10);
        leafRegisterCompareTag.setWaitBefore(10);
        leafRegisterCompareTag.setWaitAfter(10);
        leafRegisterCompareTag.setId(2);
        leafRegisterCompareTag.setTimeout(10);
        leafTestTag.setPosition(1);
        tagList.getList().add(leafRegisterCompareTag);
        LeafWriteTag leafWriteTag = new LeafWriteTag();
        leafWriteTag.setId(3);
        leafWriteTag.setCommunicationName(null);
        leafWriteTag.setCommunicationName("PLC");
        leafWriteTag.setRegister(8004);
        leafWriteTag.setValue(1);
        leafWriteTag.setWaitBefore(10);
        leafWriteTag.setWaitAfter(10);
        leafWriteTag.setTimeout(10);
        leafTestTag.setPosition(1);
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
        leafRegisterCompareTag2.setId(4);
        leafRegisterCompareTag2.setTimeout(10);
        leafTestTag.setPosition(1);
        tagList.getList().add(leafRegisterCompareTag2);
        

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/test3.json"), tagList);

        JsonObjConverter converter = new JsonObjConverter();
        File file = new File("src/test/resources/test3.json");
        TagList tagList2 = converter.convertFromJsonToObj(file);
        Assert.assertEquals(tagList.getList().get(0).getId(), tagList2.getList().get(0).getId());
        Assert.assertEquals(tagList.getList().get(0).getTagName(), tagList2.getList().get(0).getTagName());
        Assert.assertEquals(tagList.getList().get(0).getTagIdentifier(), tagList2.getList().get(0).getTagIdentifier());
    }
}
