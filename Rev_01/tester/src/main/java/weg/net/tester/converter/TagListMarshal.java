package weg.net.tester.converter;

import java.io.File;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.springframework.beans.factory.annotation.Autowired;

import weg.net.tester.repositories.TestingRoutineRepository;
import weg.net.tester.tag.TagList;
import weg.net.tester.utils.FilePathUtil;

public class TagListMarshal {
    //From object to file

    @Autowired
    private TestingRoutineRepository testingRoutineRepository;

    public void downloadRoutineFromDataBase(String fileName) {
        try {
            TagListUnmarshal tagListUnmarshal = new TagListUnmarshal();
            TagList tagList = tagListUnmarshal.unmarshalingFromJsonString(getRoutineJson(fileName));
            marshalToXml(tagList);
        } catch (Exception e) {
            //Throw some
            //@Todo
        }
    }

    public String getRoutineJson(String fileName) {
        return testingRoutineRepository.findByFileName(fileName).getRoutineJson().toString();
    }

    //From obj to xml
    public void marshalToXml(TagList listToMarshall) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(TagList.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(listToMarshall, new File(FilePathUtil.TEST_ROUTINE_PATH));
    }

    public String marshalToXmlString(TagList listToMarshall) throws JAXBException {
        java.io.StringWriter sw = new StringWriter();
        JAXBContext jaxbContext = JAXBContext.newInstance(TagList.class);

        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.marshal(listToMarshall, sw);
    
        return sw.toString();
    }

    //From obj to json
    public String marshalToJsonString(TagList listToMarshall) throws JAXBException {
        JsonXmlConverter jsonXmlConverter = new JsonXmlConverter();
        return jsonXmlConverter.fromXmlToJson(marshalToXmlString(listToMarshall));
    }
}
