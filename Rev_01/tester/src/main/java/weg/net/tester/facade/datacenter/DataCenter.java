package weg.net.tester.facade.datacenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import weg.net.tester.exception.DataBaseException;
import weg.net.tester.exception.InlineException;
import weg.net.tester.exception.SapException;
import weg.net.tester.exception.TestSetupException;
import weg.net.tester.exception.TestUnmarshalingException;
import weg.net.tester.models.TestMetaDataModel;
import weg.net.tester.models.TestingRoutine;
import weg.net.tester.tag.TagList;
import weg.net.tester.utils.FilePathUtil;
import weg.net.tester.utils.FrontEndFeedbackUtil;
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
    public TagList initiate(String[] barCode) throws SapException, InlineException, DataBaseException, TestUnmarshalingException, TestSetupException {
        this.sapConnector.setBarCode(barCode);
        this.sapConnector.getDataBy2DBarcodeString();

        String[] serial = new String[barCode.length];
        String[] descricaoProduto = new String[barCode.length];
        for (int position = 0; position < barCode.length; position++) {
            serial[position] = this.sapConnector.getSapDataMap().get(position).get(SapCaracUtil.SERIAL);
            descricaoProduto[position] = this.sapConnector.getSapDataMap().get(position).get(SapCaracUtil.SHORT_TEXT);
        }

        TestingRoutine testingRoutine = getList(barCode.length);
        TagList baseTagList = testingRoutine.getRoutine();
        mongoConnector.initialSetup();
        this.inlineConnector.saveInitialEvent();
        //Make it a loop or something

        //@Todo: take the inline initialization that is in the Test Executor and bring here
        //this.inlineConnector.setSerial(serial);
        //this.ensConnector.setSerial(serial);
        
        //this.mongoConnector.setDescricaoProduto(descricaoProduto);
        //this.mongoConnector.setSerial(serial);
        return baseTagList;
    }

    public void end(String[] result) throws InlineException, DataBaseException {
        saveInlineEnd(result);
        mongoConnector.endingSetup(result, TestMetaDataModel.testStep, TestMetaDataModel.tagList);
    }

    private void saveInlineEnd(String[] result) throws InlineException {
        for (int position = 0; position < result.length; position++) {
            if (result[0].equals(FrontEndFeedbackUtil.OK)) {
                this.inlineConnector.saveApprovalEvent();
            } else {
                if (result[0].equals(FrontEndFeedbackUtil.CANCELADO)) {
                    this.inlineConnector.saveCancelEvent(result[0]);
                } else {
                    this.inlineConnector.saveRepprovalEvent(result[0]);
                }
            }
        }
    }

    private TestingRoutine getList(int qnt) throws TestSetupException {
        String fileReferenceName = sapConnector.getSapDataMap().get(0).get(SapCaracUtil.SHORT_TEXT);
        for (int position = 0; position < qnt; position++) {
            if (!fileReferenceName.equals(sapConnector.getSapDataMap().get(position).get(SapCaracUtil.SHORT_TEXT))) {
                throw new TestSetupException("Falha no setup do produto na posição " + (position+1));
            }
        }
        return new TestingRoutine(FilePathUtil.TEST_ROUTINE_PATH + fileReferenceName + ".json");
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
