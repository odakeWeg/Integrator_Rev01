package weg.net.tester.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.context.annotation.Configuration;

import net.weg.searchsap.Caract;
import net.weg.searchsap.ProdutoBrutoSAP;
import weg.net.tester.exception.SapException;
import weg.net.tester.utils.SapCaracUtil;

@Configuration
public class SapConnector {
    //@Todo: SAP Buffer so it doesn't take to long to process, 
    private String[] barCode;
    private List<HashMap<String, String>> sapDataMap = new ArrayList<HashMap<String, String>>();

    public void getDataBy2DBarcodeString() throws SapException {
        ProdutoBrutoSAP produtoBrutoSAP;
        try {
            for (int position = 0; position < barCode.length; position++) {
                produtoBrutoSAP = new ProdutoBrutoSAP(this.barCode[position]);
                produtoBrutoSAP.importarCaracteristicas();
                setDataMap(produtoBrutoSAP, position);
                sapDataMap.get(position).put(SapCaracUtil.SERIAL, Long.toString(produtoBrutoSAP.getSerial()));
                sapDataMap.get(position).put(SapCaracUtil.MATERIAL, Long.toString(produtoBrutoSAP.getMaterial()));
                sapDataMap.get(position).put(SapCaracUtil.ORDER, Long.toString(produtoBrutoSAP.getOrdemProducao()));
                putSerial(position);
                putMAC(produtoBrutoSAP, position);
            }
        } catch (Exception e) {
            throw new SapException("Falha na consulta do SAP");
        }
	}

    private void putMAC(ProdutoBrutoSAP produtoBrutoSAP, int position) {
        try {
            sapDataMap.get(position).put(SapCaracUtil.MAC, produtoBrutoSAP.getMacAddress());
            sapDataMap.get(position).put(SapCaracUtil.MAC_1, sapDataMap.get(position).get(SapCaracUtil.MAC).substring(0, 16));
            sapDataMap.get(position).put(SapCaracUtil.MAC_2, sapDataMap.get(position).get(SapCaracUtil.MAC).substring(16, 24));
            sapDataMap.get(position).put(SapCaracUtil.MAC_3, sapDataMap.get(position).get(SapCaracUtil.MAC).substring(24));
        } catch (NullPointerException e) {
            //@Todo: this is bad practice
        }
    }

    private void putSerial(int position) {
        sapDataMap.get(position).put(SapCaracUtil.SERIAL_1, sapDataMap.get(position).get(SapCaracUtil.SERIAL).substring(0, 4));
        sapDataMap.get(position).put(SapCaracUtil.SERIAL_2, sapDataMap.get(position).get(SapCaracUtil.SERIAL).substring(4, 8));
        sapDataMap.get(position).put(SapCaracUtil.SERIAL_3, sapDataMap.get(position).get(SapCaracUtil.SERIAL).substring(8));
    }

    private void setDataMap(ProdutoBrutoSAP produtoBrutoSAP, int position) {
        sapDataMap.add(new HashMap<>());
        for (Caract data: Caract.values()) {
            try {
                if(produtoBrutoSAP.getCaract(data)!=null) {
                    sapDataMap.get(position).put(data.name(), produtoBrutoSAP.getCaract(data));
                }
            } catch (NullPointerException e) {
            }
        }
    }

    public List<HashMap<String, String>> getSapDataMap() {
        return this.sapDataMap;
    }

    public HashMap<String, String> getSapDataMapByPosition(int position) {
        return this.sapDataMap.get(position);
    }

    public String[] getBarCode() {
        return this.barCode;
    }

    public void setBarCode(String[] barCode) {
        this.barCode = barCode;
    }
}
