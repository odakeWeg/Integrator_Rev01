package weg.net.tester.facade.datacenter;

import org.springframework.context.annotation.Configuration;

import net.weg.wdc.ens.DielectricTestCharacteristicList;
import net.weg.wdc.ens.FunctionalTestCharacteristicList;
import net.weg.wdc.ens.LoadTestCharacteristicList;
import net.weg.wdc.ens.ProductDataEns;
import net.weg.wdc.ens.ReneableEnergyCharacteristicList;

@Configuration
public class EnsConnector {
    private String serial;
    
    public void saveEns() {
        ProductDataEns productDataEns = new ProductDataEns();
        //@Todo: put ens data
        //1) How to check which ensaio to make
        //2) How to check which variable to fill
        //3) How to retrieve everydata -> from the tag list???
        //4) How to make the frontEnd aware of what is been created 
        productDataEns.setDielectricTestCharacteristicList(setDielectricTestFromObject());
        productDataEns.setFunctionalTestCharacteristicList(setFunctionalTestFromObject());
        productDataEns.setLoadTestCharacteristicList(setLoadTestFromObject());
        productDataEns.setReneableEnergyCharacteristicList(setRenewableEnergyFromObject());
    }

    //@Todo: Search method for each position and each serial

    //@Todo: Implementar adição de dados
    public DielectricTestCharacteristicList setDielectricTestFromObject() {
        DielectricTestCharacteristicList dielectricTestCharacteristicList = new DielectricTestCharacteristicList();
        return dielectricTestCharacteristicList;
    }

    public FunctionalTestCharacteristicList setFunctionalTestFromObject() {
        FunctionalTestCharacteristicList functionalTestCharacteristicList = new FunctionalTestCharacteristicList();
        return functionalTestCharacteristicList;
    }

    public LoadTestCharacteristicList setLoadTestFromObject() {
        LoadTestCharacteristicList loadTestCharacteristicList = new LoadTestCharacteristicList();
        return loadTestCharacteristicList;
    }

    public ReneableEnergyCharacteristicList setRenewableEnergyFromObject() {
        ReneableEnergyCharacteristicList reneableEnergyCharacteristicList = new ReneableEnergyCharacteristicList();
        return reneableEnergyCharacteristicList;
    }

    public String getSerial() {
        return this.serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }
}
