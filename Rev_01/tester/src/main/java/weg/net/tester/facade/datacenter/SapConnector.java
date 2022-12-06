package weg.net.tester.facade.datacenter;

import java.util.HashMap;

import org.springframework.context.annotation.Configuration;

import net.weg.searchsap.Caract;
import net.weg.searchsap.ProdutoBrutoSAP;
import weg.net.tester.exception.SapException;
import weg.net.tester.utils.SapCaracUtil;

@Configuration
public class SapConnector {
    private String barCode;
    private HashMap<String, String> sapDataMap = new HashMap<>();

    public void getDataBy2DBarcodeString() throws SapException {
        try {
            ProdutoBrutoSAP produtoBrutoSAP = new ProdutoBrutoSAP(this.barCode);
            produtoBrutoSAP.importarCaracteristicas();
            setDataMap(produtoBrutoSAP);
            sapDataMap.put(SapCaracUtil.SERIAL, Long.toString(produtoBrutoSAP.getSerial()));
            sapDataMap.put(SapCaracUtil.MATERIAL, Long.toString(produtoBrutoSAP.getMaterial()));
            sapDataMap.put(SapCaracUtil.ORDER, Long.toString(produtoBrutoSAP.getOrdemProducao()));
            putSerial();
            putMAC(produtoBrutoSAP);
        } catch (Exception e) {
            throw new SapException("Falha na consulta do SAP");
        }
	}

    private void putMAC(ProdutoBrutoSAP produtoBrutoSAP) {
        try {
            sapDataMap.put(SapCaracUtil.MAC, produtoBrutoSAP.getMacAddress());
            sapDataMap.put(SapCaracUtil.MAC_1, sapDataMap.get(SapCaracUtil.MAC).substring(0, 16));
            sapDataMap.put(SapCaracUtil.MAC_2, sapDataMap.get(SapCaracUtil.MAC).substring(16, 24));
            sapDataMap.put(SapCaracUtil.MAC_3, sapDataMap.get(SapCaracUtil.MAC).substring(24));
        } catch (NullPointerException e) {
        }
    }

    private void putSerial() {
        sapDataMap.put(SapCaracUtil.SERIAL_1, sapDataMap.get(SapCaracUtil.SERIAL).substring(0, 4));
        sapDataMap.put(SapCaracUtil.SERIAL_2, sapDataMap.get(SapCaracUtil.SERIAL).substring(4, 8));
        sapDataMap.put(SapCaracUtil.SERIAL_3, sapDataMap.get(SapCaracUtil.SERIAL).substring(8));
    }

    private void setDataMap(ProdutoBrutoSAP produtoBrutoSAP) {
        for (Caract data: Caract.values()) {
            try {
                if(produtoBrutoSAP.getCaract(data)!=null) {
                    sapDataMap.put(data.name(), produtoBrutoSAP.getCaract(data));
                }
            } catch (NullPointerException e) {
            }
        }
    }

    public HashMap<String,String> getSapDataMap() {
        return this.sapDataMap;
    }

    public String getBarCode() {
        return this.barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
}
