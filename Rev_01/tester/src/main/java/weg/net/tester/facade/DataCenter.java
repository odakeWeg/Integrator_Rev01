package weg.net.tester.facade;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;

import weg.net.tester.converter.BaseConverter;
import weg.net.tester.converter.JsonObjConverter;
import weg.net.tester.exception.DataBaseException;
import weg.net.tester.exception.EnsException;
import weg.net.tester.exception.InlineException;
import weg.net.tester.exception.SapException;
import weg.net.tester.exception.SessionException;
import weg.net.tester.exception.TestSetupException;
import weg.net.tester.exception.TestUnmarshalingException;
import weg.net.tester.tag.TagList;
import weg.net.tester.tag.TestMetaDataModel;
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
    //@Todo: Save test end HTML file (easy way for the people who fix the product)
    public TagList initiate(String[] barCode) throws SapException, InlineException, SessionException, TestUnmarshalingException, TestSetupException {
        this.sapConnector.setBarCode(barCode);
        this.sapConnector.getDataBy2DBarcodeString();

        String[] serial = new String[barCode.length];
        String[] descricaoProduto = new String[barCode.length];
        for (int position = 0; position < barCode.length; position++) {
            serial[position] = this.sapConnector.getSapDataMap().get(position).get(SapCaracUtil.SERIAL);
            descricaoProduto[position] = this.sapConnector.getSapDataMap().get(position).get(SapCaracUtil.SHORT_TEXT);
        }

        TagList baseTagList = getList(barCode.length);
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

    public void end(String[] result) throws InlineException, DataBaseException, EnsException, JsonProcessingException, ParseException {
        saveInlineEnd(result);
        mongoConnector.endingSetup(result, TestMetaDataModel.testStep, TestMetaDataModel.tagList);
        ensConnector.saveEns(TestMetaDataModel.tagList);
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

    private TagList getList(int qnt) throws TestSetupException, TestUnmarshalingException {
        String fileReferenceName = sapConnector.getSapDataMap().get(0).get(SapCaracUtil.SHORT_TEXT);
        for (int position = 0; position < qnt; position++) {
            if (!fileReferenceName.equals(sapConnector.getSapDataMap().get(position).get(SapCaracUtil.SHORT_TEXT))) {
                throw new TestSetupException("Falha no setup do produto na posição " + (position+1));
            }
        }
        BaseConverter converter = new JsonObjConverter();
        TagList list = converter.getRoutineFromFileName(FilePathUtil.TEST_ROUTINE_PATH + fileReferenceName + ".json");
        if(list.qntOfProductInTest()!=qnt) {
            throw new TestSetupException("Falha no setup do produto: Quantidade de produtos lidos diferente da quantidade de posições de teste, quantidade lida = "+ qnt + ", quantidade esperada: " + list.qntOfProductInTest());
        }
        return list;
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
