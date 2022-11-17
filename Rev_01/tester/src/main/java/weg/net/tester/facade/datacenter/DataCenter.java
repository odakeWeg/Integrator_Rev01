package weg.net.tester.facade.datacenter;

import org.springframework.beans.factory.annotation.Autowired;

public class DataCenter {
    private InlineConnector inlineConnector;
    private EnsConnector ensConnector;
    private SapConnector sapConnector;

    @Autowired
    private MongoConnector mongoConnector;

    //@Todo: implement ens
    //@Todo: implement mongo
    public DataCenter(String barCode) {
        this.sapConnector = new SapConnector(barCode);
        String serial = "serial"; //@Todo: get from sap;
        String descricaoProduto = "produto"; //@Todo: get from sap

        this.inlineConnector = new InlineConnector(serial);
        this.ensConnector = new EnsConnector(serial);
        //this.mongoConnector = new MongoConnector(serial, descricaoProduto);
        this.mongoConnector.setDescricaoProduto(descricaoProduto);
        this.mongoConnector.setSerial(serial);
    }

    public InlineConnector getInlineConnector() {
        return this.inlineConnector;
    }

    public EnsConnector getEnsConnector() {
        return this.ensConnector;
    }

    public SapConnector getSapConnector() {
        return this.sapConnector;
    }

    public MongoConnector getMongoConnector() {
        return this.mongoConnector;
    }
}
