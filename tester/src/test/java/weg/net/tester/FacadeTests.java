package weg.net.tester;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import weg.net.tester.facade.MongoConnector;
import weg.net.tester.facade.SapConnector;

@SpringBootTest
public class FacadeTests {
    @Test
	void sapConnection() {
        //Código de barras SSW900
        String cod = "017909492093169 211062114337 10 911121714557 24014419092";

        SapConnector sapInstance = new SapConnector(cod);
        Assert.assertEquals("1062114337", sapInstance.getSapDataMap().get("serial"));
	}

    @Test
	void ensConnection() {
        //Código de barras SSW900
	}

    @Test
	void inlineConnection() {
        //Código de barras SSW900
	}

    @Test
	void mongoConnection() {
        MongoConnector mongoConnector = new MongoConnector("1062114337", "SSW900");

        mongoConnector.initialSetup();
        mongoConnector.endingSetup();
        
        Assert.assertEquals("1062114337", );
	}
}
