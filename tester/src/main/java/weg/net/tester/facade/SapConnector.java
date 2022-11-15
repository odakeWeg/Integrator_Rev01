package weg.net.tester.facade;

import java.util.HashMap;

import net.weg.searchsap.Caract;
import net.weg.searchsap.ProdutoBrutoSAP;

//@Todo remove or refactor unnecessary fields
public class SapConnector {
    private String barCode;
    private HashMap<String, String> sapDataMap = new HashMap<>();

    public SapConnector(String barCode) {
        getDataBy2DBarcodeString(barCode);
    }

    private void getDataBy2DBarcodeString(String barCode) {
        ProdutoBrutoSAP produtoBrutoSAP = new ProdutoBrutoSAP(barCode);
        produtoBrutoSAP.importarCaracteristicas();
        setDataMap(produtoBrutoSAP);
        sapDataMap.put("serial", Long.toString(produtoBrutoSAP.getSerial()));
        sapDataMap.put("material", Long.toString(produtoBrutoSAP.getMaterial()));
        sapDataMap.put("ordem", Long.toString(produtoBrutoSAP.getOrdemProducao()));
        putSerial();
        putMAC(produtoBrutoSAP);
	}

    private void putMAC(ProdutoBrutoSAP produtoBrutoSAP) {
        try {
            sapDataMap.put("MAC", produtoBrutoSAP.getMacAddress());
            sapDataMap.put("MAC1", sapDataMap.get("MAC").substring(0, 16));
            sapDataMap.put("MAC2", sapDataMap.get("MAC").substring(16, 24));
            sapDataMap.put("MAC3", sapDataMap.get("MAC").substring(24));
        } catch (NullPointerException e) {
            //@Todo: Throw some error
        }
    }

    private void putSerial() {
        sapDataMap.put("serial1", sapDataMap.get("serial").substring(0, 4));
        sapDataMap.put("serial2", sapDataMap.get("serial").substring(4, 8));
        sapDataMap.put("serial3", sapDataMap.get("serial").substring(8));
    }

    private void setDataMap(ProdutoBrutoSAP produtoBrutoSAP) {
        for (Caract data: Caract.values()) {
            try {
                if(produtoBrutoSAP.getCaract(data)==null) {
                    //sapDataMap.put(data.name(), "N/A");
                } else {
                    sapDataMap.put(data.name(), produtoBrutoSAP.getCaract(data));
                }
            } catch (NullPointerException e) {
                //@Todo: Trow some error -> not necessarily
            }
        }
    }

    public HashMap<String,String> getSapDataMap() {
        return this.sapDataMap;
    }
}
