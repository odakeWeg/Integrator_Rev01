package weg.net.tester.facade.datacenter;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;

import weg.net.tester.converter.JsonObjConverter;
import weg.net.tester.exception.DataBaseException;
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
    //@Todo: Make it an array (serial and result and description and step) 
    private String[] serial;
    private String[] result;
    private long duration;
    private String tagList;
    private String[] descricaoProduto;
    private int[] testStep;

    private long startTime;
    private long endingTime;

    public MongoConnector(String[] serial, String[] descricaoProduto) {
        this.serial = serial;
        this.descricaoProduto = descricaoProduto;
    }
    public MongoConnector() {
    }

    public void initialSetup() throws DataBaseException {
        try{
            this.startTime = System.currentTimeMillis() / 1000;

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            this.timestamp = String.valueOf(timestamp.getTime()); 

            this.cadastro = SessionUtil.sessionModel.getCadastro();
            this.sessionId = SessionUtil.sessionModel.getTimestamp();
        } catch (Exception e) {
            throw new DataBaseException("Falha no setup inicial da base de dados");
        }
    }

    public void endingSetup(String[] result, int[] testStep, TagList tagList) throws DataBaseException {
        try{
            //@Todo: need to make multi threaded, testStep is an array
            //duration will be divided by testStep length
            //maybe sum to the timestamp the duration

            //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            this. endingTime = System.currentTimeMillis() / 1000;   //timestamp.getTime(); 
            this.duration = endingTime - startTime;

            this.result = result;
            this.testStep = testStep;
            this.tagList = this.saveTest(tagList.getList());
            
            TestingResultModel testingResultModel = new TestingResultModel(this.descricaoProduto, this.sessionId, this.cadastro, this.serial, this.result, this.duration, this.tagList, this.timestamp, this.testStep);
            testingResultRepository.save(testingResultModel);
        } catch (Exception e) {
            throw new DataBaseException("Falha no setup final da base de dados");
        }
    }

    public String saveTest(List<BaseTag> tagList) throws DataBaseException {
        try{
            TagList tags = new TagList();
            JsonObjConverter jsonObjConverter = new JsonObjConverter();
            String stringfiedJson;
            
            tags.setList(tagList);
            stringfiedJson = jsonObjConverter.ObjToJsonStringConverter(tags);
            
            return stringfiedJson;     
        } catch (Exception e) {
            throw new DataBaseException("Falha ao salvar na base de dados");
        }
    }

    public String[] getSerial() {
        return this.serial;
    }

    public void setSerial(String[] serial) {
        this.serial = serial;
    }

    public String[] getDescricaoProduto() {
        return this.descricaoProduto;
    }

    public void setDescricaoProduto(String[] descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }
}
