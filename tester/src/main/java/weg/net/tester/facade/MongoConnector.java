package weg.net.tester.facade;

import org.springframework.beans.factory.annotation.Autowired;

import weg.net.tester.models.TestingResultModel;
import weg.net.tester.repositories.TestingResultRepository;

public class MongoConnector {
    @Autowired
    private TestingResultRepository testingResultRepository;

    private String serial;
    private String descricaoProduto;

    public MongoConnector(String serial, String descricaoProduto) {
        this.serial = serial;
        this.descricaoProduto = descricaoProduto;
    }

    public void initialSetup() {
        //@Todo: get initial data
    }

    public void endingSetup() {
        //@Todo: get final data end save
    }
    
}
