package weg.net.tester.facade;

import net.weg.wdc.ens.DielectricTestCharacteristicList;
import net.weg.wdc.ens.FunctionalTestCharacteristicList;
import net.weg.wdc.ens.LoadTestCharacteristicList;
import net.weg.wdc.ens.ProductDataEns;
import net.weg.wdc.ens.ReneableEnergyCharacteristicList;

public class EnsConnector {
    private String serial;

    public EnsConnector(String serial) {
        this.serial = serial;
    }
    
    public void saveEns() {
        ProductDataEns productDataEns = new ProductDataEns();
        //@Todo: put ens data
        productDataEns.setDielectricTestCharacteristicList(setDielectricTestFromObject());
        productDataEns.setFunctionalTestCharacteristicList(setFunctionalTestFromObject());
        productDataEns.setLoadTestCharacteristicList(setLoadTestFromObject());
        productDataEns.setReneableEnergyCharacteristicList(setRenewableEnergyFromObject());
    }

    //@Todo: Implementar adição de dados
    public DielectricTestCharacteristicList setDielectricTestFromObject() {
        return null;
    }

    public FunctionalTestCharacteristicList setFunctionalTestFromObject() {
        return null;
    }

    public LoadTestCharacteristicList setLoadTestFromObject() {
        return null;
    }

    public ReneableEnergyCharacteristicList setRenewableEnergyFromObject() {
        return null;
    }
}
