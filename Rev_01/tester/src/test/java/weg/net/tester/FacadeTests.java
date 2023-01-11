package weg.net.tester;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;

import net.weg.searchsap.Caract;
import net.weg.searchsap.ProdutoBrutoSAP;
import weg.net.tester.exception.DataBaseException;
import weg.net.tester.exception.InlineException;
import weg.net.tester.exception.SapException;
import weg.net.tester.exception.SessionException;
import weg.net.tester.facade.InlineConnector;
import weg.net.tester.facade.MongoConnector;
import weg.net.tester.facade.SapConnector;
import weg.net.tester.models.database.SessionModel;
import weg.net.tester.models.database.TestingResultModel;
import weg.net.tester.repositories.SessionRepository;
import weg.net.tester.repositories.TestingResultRepository;
import weg.net.tester.tag.BaseTag;
import weg.net.tester.tag.LeafForTestTag;
import weg.net.tester.tag.LeafTestTag;
import weg.net.tester.tag.TagList;
import weg.net.tester.utils.SessionUtil;

@SpringBootTest
public class FacadeTests {

    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private TestingResultRepository testingResultRepository;
    @Autowired
    private MongoConnector mongoConnector;
    @Autowired
    private InlineConnector inlineConnector;
    @Autowired
    private SapConnector sapConnector;
    
    @Test
	void sapConnection() throws SapException {
        //Código de barras SSW900
        String[] cod = {"017909492093169 211062114337 10 911121714557 24014419092"};

        SapConnector sapInstance = new SapConnector();
        sapInstance.setBarCode(cod);
        sapInstance.getDataBy2DBarcodeString();
        Assert.assertEquals("SSW900", sapInstance.getSapDataMap().get(0).get(Caract.REF_PRODUTO_AUTOMACAO.name()));
	}

    @Test
    void sapCaract() {
        //Código de barras SSW900
        String cod = "017909492093169 211062114337 10 911121714557 24014419092";

        ProdutoBrutoSAP produtoBrutoSAP = new ProdutoBrutoSAP(cod);
        produtoBrutoSAP.importarCaracteristicas();

        for (Caract data: Caract.values()) {
            try {
                if(produtoBrutoSAP.getCaract(data)==null) {
                    //sapDataMap.put(data.name(), "N/A");
                } else {
                    System.out.println(data.name());
                    System.out.println(produtoBrutoSAP.getCaract(data));
                }
            } catch (NullPointerException e) {
                
            }
        }
    }

    @Test
    void sapGetingData() throws SapException {
        //Código de barras SSW900
        String[] barCode = {"017909492093169 211062114337 10 911121714557 24014419092"};

        this.sapConnector.setBarCode(barCode);
        this.sapConnector.getDataBy2DBarcodeString();
    }

    @Test
	void ensConnection() {
        //Código de barras SSW900
	}

    @Test
	void inlineConnection() throws Exception {
        inlineConnector.setSerial("1062114337");
        inlineConnector.isTestAllowed();
	}

    @Test
	void mongoSessionConnection() {
        SessionUtil.initiateSession("7881");
        SessionUtil.endSession();
        sessionRepository.save(SessionUtil.sessionModel);

        List<SessionModel> sessionModel = sessionRepository.findByCadastro("7881");
        
        Assert.assertEquals("7881", sessionModel.get(0).getCadastro());

        sessionRepository.delete(SessionUtil.sessionModel);

        sessionModel= sessionRepository.findByCadastro("7881");

        Assert.assertEquals(0, sessionModel.size());
	}

    @Test
	void mongoIndividualConnection() throws JsonProcessingException, InlineException, DataBaseException, SessionException {
        //MongoConnector mongoConnector = new MongoConnector("1062114337", "SSW900");

        String[] desc = {"SSW900"};
        String[] serial = {"1062114337"};
        this.mongoConnector.setDescricaoProduto(desc);
        this.mongoConnector.setSerial(serial);

        List<BaseTag> list = new ArrayList();
        list.add((BaseTag) new LeafTestTag());
        list.add((BaseTag) new LeafForTestTag());
        list.add((BaseTag) new LeafTestTag());

        list.get(0).setTagName();
        list.get(1).setTagName();
        list.get(2).setTagName();

        TagList tagList = new TagList();
        tagList.setList(list);

        SessionUtil.sessionModel = new SessionModel();
        SessionUtil.sessionModel.setCadastro("7881");
        SessionUtil.sessionModel.setTimestamp("1234567890");
        mongoConnector.initialSetup();

        int[] step = {1};
        String[] result = {"result"};
        mongoConnector.endingSetup(result, step, tagList);

        List<TestingResultModel> testingResultModel = testingResultRepository.findBySerial("1062114337");

        Assert.assertEquals("SSW900", testingResultModel.get(0).getDescricaoProduto());
	}
}
