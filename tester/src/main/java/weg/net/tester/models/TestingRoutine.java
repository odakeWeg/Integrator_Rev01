package weg.net.tester.models;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import weg.net.tester.exception.TestUnmarshalingException;
import weg.net.tester.tag.BaseTag;
import weg.net.tester.tag.TagList;
import weg.net.tester.utils.FilePathUtil;

public class TestingRoutine {
    private List<BaseTag> baseTagList;
    private String fileName;

    public TestingRoutine(String fileName) {
        this.fileName = fileName;
    }

    public List<BaseTag> getRoutine() {
        try {
            File testFile = new File(FilePathUtil.TEST_ROUTINE_PATH + fileName + ".xml");
            baseTagList = unmarshalingFromXML(testFile);
        } catch (Exception e) {
            downloadRoutineFromDataBase();
        }
        return baseTagList;
    }

    private void downloadRoutineFromDataBase() {
        try {
            //get from database, marshal it, save in folder and then read it
            //marshalFromJsonToXml(getRoutineJson());
        } catch (Exception e) {
            //Throw some
        }
    }

    private void getRoutineJson() {

    }

    private void marshalFromJsonToXml() {

    }

    public List<BaseTag> unmarshalingFromXML(File testFile) throws TestUnmarshalingException {
        try {
            JAXBContext jaxbContext;
            jaxbContext = JAXBContext.newInstance(TagList.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            TagList tagList = (TagList) jaxbUnmarshaller.unmarshal(testFile);
            return tagList.getList();
        } catch (JAXBException e) {
            throw new TestUnmarshalingException("Falha na aquisição da rotina de teste!");
        }
    }

}
