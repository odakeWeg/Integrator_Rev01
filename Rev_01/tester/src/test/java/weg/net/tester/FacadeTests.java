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
import weg.net.tester.facade.datacenter.InlineConnector;
import weg.net.tester.facade.datacenter.MongoConnector;
import weg.net.tester.facade.datacenter.SapConnector;
import weg.net.tester.models.SessionModel;
import weg.net.tester.models.TestingResultModel;
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
	void sapConnection() {
        //Código de barras SSW900
        String cod = "017909492093169 211062114337 10 911121714557 24014419092";

        SapConnector sapInstance = new SapConnector();
        sapInstance.setBarCode(cod);
        Assert.assertEquals("SSW900", sapInstance.getSapDataMap().get(Caract.REF_PRODUTO_AUTOMACAO.name()));
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
    void sapGetingData() {
        //Código de barras SSW900
        String barCode = "017909492093169 211062114337 10 911121714557 24014419092";

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
	void mongoIndividualConnection() throws JsonProcessingException {
        //MongoConnector mongoConnector = new MongoConnector("1062114337", "SSW900");

        this.mongoConnector.setDescricaoProduto("SSW900");
        this.mongoConnector.setSerial("1062114337");

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
        mongoConnector.endingSetup("result", 1, tagList);

        List<TestingResultModel> testingResultModel = testingResultRepository.findBySerial("1062114337");

        Assert.assertEquals("SSW900", testingResultModel.get(0).getDescricaoProduto());
	}
}
