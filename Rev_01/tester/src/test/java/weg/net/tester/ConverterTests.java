package weg.net.tester;

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
import weg.net.tester.tag.LeafTestTag;
import weg.net.tester.tag.TagList;

public class ConverterTests {

    @Test
    public void jsonToObjConverterTest() throws TestUnmarshalingException, StreamWriteException, DatabindException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, ParseException {
        TagList tagList = new TagList();
        tagList.setList(new ArrayList<>());
        tagList.getList().add(new LeafEthernetCommunicationTag());
        tagList.getList().add(new LeafTestTag());

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/test.txt"), tagList);

        JsonObjConverter converter = new JsonObjConverter();
        File file = new File("src/test/resources/test.txt");
        TagList tagList2 = converter.convertFromJsonToObj(file);
        Assert.assertEquals(tagList.getList().get(0).getId(), tagList2.getList().get(0).getId());
        Assert.assertEquals(tagList.getList().get(0).getTagName(), tagList2.getList().get(0).getTagName());
        Assert.assertEquals(tagList.getList().get(0).getTagIdentifier(), tagList2.getList().get(0).getTagIdentifier());
    }
}

