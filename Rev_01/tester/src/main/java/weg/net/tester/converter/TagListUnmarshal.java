package weg.net.tester.converter;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import weg.net.tester.exception.TestUnmarshalingException;
import weg.net.tester.tag.TagList;
import weg.net.tester.utils.FilePathUtil;

public class TagListUnmarshal {
    //From file to object

    public TagList getRoutineFromFileName(String fileName) {
        TagList TagList;
        try {
            File testFile = new File(FilePathUtil.TEST_ROUTINE_PATH + fileName + ".xml");
            TagList = unmarshalingFromXML(testFile);
            return TagList;
        } catch (Exception e) {
            //@Todo: treat error or something
            TagListMarshal tagListMarshal = new TagListMarshal();
            tagListMarshal.downloadRoutineFromDataBase(fileName);
            return getRoutineFromFileName(fileName);
        }
    }

    //From xml to object
    public TagList unmarshalingFromXML(File testFile) throws TestUnmarshalingException {
        try {
            JAXBContext jaxbContext;
            jaxbContext = JAXBContext.newInstance(TagList.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            TagList tagList = (TagList) jaxbUnmarshaller.unmarshal(testFile);
            return tagList;
        } catch (JAXBException e) {
            throw new TestUnmarshalingException("Falha na aquisição da rotina de teste!");
        }
    }

    //From json to object
    public TagList unmarshalingFromJsonString(String json) throws TestUnmarshalingException {
        try {
            //Reflection or something
            return null;
        } catch (Exception e) {
            throw new TestUnmarshalingException("Falha na aquisição da rotina de teste!");
        }
    }

    /* 
    //From json to xml to object
    public static List<Tag> fromJsonToXmlToObj() {
        return null;
    }
    */
}
