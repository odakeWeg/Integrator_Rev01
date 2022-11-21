package weg.net.tester.converter;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;

import weg.net.tester.exception.TestUnmarshalingException;
import weg.net.tester.tag.TagList;
import weg.net.tester.utils.FilePathUtil;

public class TagListUnmarshal {
    //From file to object

    public TagList getRoutineFromFileName(String fileName) {
        TagList TagList;
        try {
            File testFile = new File(FilePathUtil.TEST_ROUTINE_PATH + fileName + ".xml");
            TagList = unmarshalingFromJson(testFile);
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
    public TagList unmarshalingFromJson(File testFile) throws TestUnmarshalingException {
        try {
            //Reflection or something
            String routineString = FileUtils.readFileToString(testFile, StandardCharsets.UTF_8);
            
            return null;
        } catch (Exception e) {
            throw new TestUnmarshalingException("Falha na aquisição da rotina de teste!");
        }
    }

    public String[] commaSeparatedJson() {
        
    }

    /* 
    //From json to xml to object
    public static List<Tag> fromJsonToXmlToObj() {
        return null;
    }
    */
}
