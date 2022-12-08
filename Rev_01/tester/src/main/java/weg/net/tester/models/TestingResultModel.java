package weg.net.tester.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

@Document
@Getter
public class TestingResultModel {
    @Id
    private String timestamp;
    private String sessionId;
    private String cadastro;
    private String[] serial;
    private String[] result;
    private long duration;
    private String tagList;
    private String[] descricaoProduto;
    private int[] testStep;

    public TestingResultModel(String[] descricaoProduto, String sessionId, String cadastro, String[] serial, String[] result, long duration, String tagList, String timestamp, int[] testStep) {
        this.testStep = testStep;
        this.descricaoProduto = descricaoProduto;
        this.sessionId = sessionId;
        this.cadastro = cadastro;
        this.serial = serial;
        this.result = result;
        this.duration = duration;
        this.tagList = tagList;
        this.timestamp = timestamp;
    }

}