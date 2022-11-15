package weg.net.tester.facade;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import weg.net.tester.models.TestingResultModel;
import weg.net.tester.repositories.TestingResultRepository;

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

    public MongoConnector(String serial, String descricaoProduto) {
        this.serial = serial;
        this.descricaoProduto = descricaoProduto;
    }

    public void initialSetup() {
        //@Todo: get initial data
        long startTime;
        startTime = System.currentTimeMillis() / 1000;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        this.timestamp = String.valueOf(timestamp.getTime()); 

        this.cadastro = ApplicationSetup.getSessionDTO().getCadastro();
        this.sessionId = ApplicationSetup.getSessionDTO().getTimestamp();
    }

    public void endingSetup() {
        //@Todo: get final data
        TestingResultModel testingResultModel = new TestingResultModel(descricaoProduto, sessionId, cadastro, serial, result, duration, tagList, timestamp, testStep);
        testingResultRepository.save(testingResultModel);
    }
}
