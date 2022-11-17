package weg.net.tester.facade.datacenter;

import java.io.File;
import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;

import weg.net.tester.models.TestingResultModel;
import weg.net.tester.repositories.TestingResultRepository;
import weg.net.tester.tag.BaseTag;
import weg.net.tester.tag.TagList;
import weg.net.tester.utils.SessionUtil;

@Configuration 
public class MongoConnector {
    @Autowired
    private TestingResultRepository testingResultRepository;

    private String timestamp;
    private String sessionId;
    private String cadastro;
    private String serial;
    private String result;
    private long duration;
    private String tagList;
    private String descricaoProduto;
    private int testStep;

    private long startTime;
    private long endingTime;

    public MongoConnector(String serial, String descricaoProduto) {
        this.serial = serial;
        this.descricaoProduto = descricaoProduto;
    }
    public MongoConnector() {
    }

    public void initialSetup() {
        this.startTime = System.currentTimeMillis() / 1000;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.timestamp = String.valueOf(timestamp.getTime()); 

        this.cadastro = SessionUtil.sessionModel.getCadastro();
        this.sessionId = SessionUtil.sessionModel.getTimestamp();
    }

    public void endingSetup(String result, int testStep, List<BaseTag> tagList) {
        //@Todo: need to make multi threaded, testStep is an array
        //duration will be divided by testStep length
        //maybe sum to the timestamp the duration

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this. endingTime = timestamp.getTime(); 
        this.duration = endingTime - startTime;

        this.result = result;
        this.testStep = testStep;
        this.tagList = this.saveTest(tagList);
        
        TestingResultModel testingResultModel = new TestingResultModel(this.descricaoProduto, this.sessionId, this.cadastro, this.serial, this.result, this.duration, this.tagList, this.timestamp, this.testStep);
        testingResultRepository.save(testingResultModel);
    }

    public String saveTest(List<BaseTag> tagList) {
        TagList tags;
        String stringfiedXML;
        try {
            tags = new TagList(tagList);
            stringfiedXML = readXML(marshalingToXML(tags));
        } catch (ClassNotFoundException | JAXBException e) {
            stringfiedXML = "Falha ao converter documento para String";
        } 
        return stringfiedXML;     
    }

    private File marshalingToXML(TagList listToMarshall) throws JAXBException {
        File file = new File("listMarshalFile.xml");

        JAXBContext jaxbContext = JAXBContext.newInstance(TagList.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        jaxbMarshaller.marshal(listToMarshall, file);

        return file;
    }

    private String readXML(File file) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            Document xmlDocument = builder.parse(file);
            return writeXmlDocumentToXmlFile(xmlDocument);
        }  catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String writeXmlDocumentToXmlFile(Document xmlDocument)
    {
        String xmlString;
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(xmlDocument), new StreamResult(writer));
    
            xmlString = writer.getBuffer().toString(); 
        } 
        catch (Exception e) {
            xmlString = "Falha ao converter documento para String";
        }
        return xmlString;
    }

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getDescricaoProduto() {
        return this.descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }
}
