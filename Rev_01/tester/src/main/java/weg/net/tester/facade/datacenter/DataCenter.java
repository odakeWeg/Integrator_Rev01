package weg.net.tester.facade.datacenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import weg.net.tester.exception.SapException;
import weg.net.tester.utils.SapCaracUtil;

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
    //@Todo: or make it multi-threaded or make an array
    public void initiate(String barCode) throws SapException {
        this.sapConnector.setBarCode(barCode);
        this.sapConnector.getDataBy2DBarcodeString();
        String serial = this.sapConnector.getSapDataMap().get(SapCaracUtil.SERIAL); //@Todo: get from sap;
        String descricaoProduto = this.sapConnector.getSapDataMap().get(SapCaracUtil.SHORT_TEXT); //@Todo: get from sap

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
