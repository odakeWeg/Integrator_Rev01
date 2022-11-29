package weg.net.tester.facade.datacenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataCenter {
    @Autowired
    private InlineConnector inlineConnector;
    @Autowired
    private EnsConnector ensConnector;
    @Autowired
    private SapConnector sapConnector;
    @Autowired
    private MongoConnector mongoConnector;

    //@Todo: implement ens
    //@Todo: implement mongo

    public void initiate(String barCode) {
        this.sapConnector.setBarCode(barCode);
        this.sapConnector.getDataBy2DBarcodeString();
        String serial = "serial"; //@Todo: get from sap;
        String descricaoProduto = "produto"; //@Todo: get from sap

        this.inlineConnector.setSerial(serial);
        //@Todo initial inline stuff
        this.ensConnector.setSerial(serial);
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
