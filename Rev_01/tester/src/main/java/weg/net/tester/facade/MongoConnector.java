package weg.net.tester.facade;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;

import weg.net.tester.converter.JsonObjConverter;
import weg.net.tester.exception.DataBaseException;
import weg.net.tester.exception.SessionException;
import weg.net.tester.helper.SessionHelper;
import weg.net.tester.models.database.TestingResultModel;
import weg.net.tester.repositories.TestingResultRepository;
import weg.net.tester.tag.BaseTag;
import weg.net.tester.tag.TagList;

@Configuration 
public class MongoConnector {
    @Autowired
    private TestingResultRepository testingResultRepository;

    private String timestamp;
    private String sessionId;
    private String cadastro;
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

    public void initialSetup() throws SessionException {
        try{
            this.startTime = System.currentTimeMillis() / 1000;

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            this.timestamp = String.valueOf(timestamp.getTime()); 

            this.cadastro = SessionHelper.sessionModel.getCadastro();
            this.sessionId = SessionHelper.sessionModel.getTimestamp();
        } catch (Exception e) {
            throw new SessionException("Falha no setup inicial da base de dados");
        }
    }

    public void endingSetup(String[] result, int[] testStep, TagList tagList) throws DataBaseException {
        try{
            this. endingTime = System.currentTimeMillis() / 1000;
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
            stringfiedJson = jsonObjConverter.objToJsonStringConverter(tags);
            
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
