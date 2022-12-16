package weg.net.tester.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonSerializable.Base;

import net.weg.soa.serviceclient.JaxWsUsernameTokenClientConfigurator;
import net.weg.wdc.ens.DielectricTestCharacteristicList;
import net.weg.wdc.ens.FunctionalTestCharacteristicList;
import net.weg.wdc.ens.LoadTestCharacteristicList;
import net.weg.wdc.ens.ProductDataEns;
import net.weg.wdc.ens.ReneableEnergyCharacteristicList;
import net.weg.wdc.ens.entity.EnsServiceConfig;
import net.weg.wdc.ens.factory.EnsServiceEntitiesFactory;
import net.weg.wdc.ens.service.EnsServiceImpl;
import weg.net.tester.converter.BaseConverter;
import weg.net.tester.converter.JsonObjConverter;
import weg.net.tester.exception.EnsException;
import weg.net.tester.models.ens.EnsTagConfiguration;
import weg.net.tester.tag.TagList;
import weg.net.tester.utils.EnsParametersUtil;

@Configuration
public class EnsConnector {
    //@Todo: Refactor this class ASAP
    private static String BEAN_XML_FILEPATH = "conf/app-config.xml";

    private List<ProductDataEns> productDataEnsList = new ArrayList<>();
    private List<ReneableEnergyCharacteristicList> reneableList = new ArrayList<>();
    private List<DielectricTestCharacteristicList> dielectricList = new ArrayList<>();
    private List<FunctionalTestCharacteristicList> functionalList = new ArrayList<>();
    private List<LoadTestCharacteristicList> loadList = new ArrayList<>();

    private TagList tagList;
    private JSONArray json;

    public void saveEns(TagList tagList) throws EnsException, JsonProcessingException, ParseException {
        //ProductDataEns productDataEns = new ProductDataEns();
        BaseConverter base = new JsonObjConverter();
        this.json = base.objToJsonArrayConverter(tagList); //transform in a array obj
        this.tagList = tagList;

        //@Todo: put ens data
        //0) Configure tags that will use Ens -------> ENSTAGCONFIG ADD
        //1) Retrieve every ens pointed value from tagList and put in a list of object or so ------> use tagList with proper attributes
        //2) Create objs properly ----> Created 
        //3) Switch case with the name of the class -> CHECK
        //4) Get method using reflection
        //5) Get parameters using forName function
        /* 

        /*
        1) Adicionar valores de suplementares
        2) Adicionar os valores corretos de ensaio (medição) 
        */
        /*
        productDataEns.setDielectricTestCharacteristicList(setDielectricTestFromObject());
        productDataEns.setFunctionalTestCharacteristicList(setFunctionalTestFromObject());
        productDataEns.setLoadTestCharacteristicList(setLoadTestFromObject());
        productDataEns.setReneableEnergyCharacteristicList(setRenewableEnergyFromObject());
        */
        instantiatePositionObj(tagList.qntOfProductInTest());
        setEnsObj();
        saveEveryEnabledPosition(); //@Todo: create enabler value on ens
    }

    private void setEnsObj() {
        for (int id = 0; id < tagList.getList().size(); id++) {
            if (tagList.getList().get(id).getEnsTagConfiguration().isEnabled()) {
                for (int position = 0; position < tagList.getList().get(id).getEnsTagConfiguration().getEnsType().size(); position++) {
                    switch(tagList.getList().get(id).getEnsTagConfiguration().getEnsType().get(position)) {
                        //case EnsParametersUtil.PRODUCT_DATA_ENS_TYPE:
                        //    setProductVariable(tagList.getList().get(id).getEnsTagConfiguration(), position, id);
                        //break;
                        case EnsParametersUtil.DIELECTRIC_TEST_CHARACTERISTIC_LIST_TYPE:
                            setDielectricVariable(tagList.getList().get(id).getEnsTagConfiguration(), position, id);
                        break;
                        case EnsParametersUtil.FUNCTIONAL_TEST_CHARACTERISTIC_LIST_TYPE:
                            setFunctionalVariable(tagList.getList().get(id).getEnsTagConfiguration(), position, id);
                        break;
                        case EnsParametersUtil.LOAD_TEST_CHARACTERISTIC_LIST_TYPE:
                            setLoadVariable(tagList.getList().get(id).getEnsTagConfiguration(), position, id);
                        break;
                        case EnsParametersUtil.RENEABLE_TEST_CHARACTERISTIC_LIST_TYPE:
                            setReneableVariable(tagList.getList().get(id).getEnsTagConfiguration(), position, id);
                        break;
                        default:
                            //Trow error
                    }
                }
            }
        }
    }

    private void instantiatePositionObj(int qnt) {
        for (int i = 0; i < qnt; i++) {
            productDataEnsList.add(new ProductDataEns());
            reneableList.add(new ReneableEnergyCharacteristicList());
            dielectricList.add(new DielectricTestCharacteristicList());
            functionalList.add(new FunctionalTestCharacteristicList());
            loadList.add(new LoadTestCharacteristicList());
        }
    }

    private void saveEveryEnabledPosition() throws EnsException {
        EnsServiceImpl imp = createEnsServiceImpl();
        for (int i = 0; i < productDataEnsList.size(); i++) {
            if (productDataEnsList.size() > 0) {
                try {
                    imp.sendEvent(productDataEnsList.get(i));
                } catch (Exception e) {
                    throw new EnsException("Falha ao salvar no Ens");
                }
                
            }
        }
    }

    private static EnsServiceImpl createEnsServiceImpl() {
		FileSystemXmlApplicationContext applicationContext = new FileSystemXmlApplicationContext(BEAN_XML_FILEPATH);
		JaxWsUsernameTokenClientConfigurator token = applicationContext.getBean(JaxWsUsernameTokenClientConfigurator.class);
        EnsServiceConfig config = applicationContext.getBean(EnsServiceConfig.class);
        EnsServiceEntitiesFactory factory = new EnsServiceEntitiesFactory();
        factory.setEnsConfig(config);
        EnsServiceImpl service = new EnsServiceImpl();
        service.setTokenConfigurator(token);
        service.setFactory(factory);
        applicationContext.close();
		return service;
	}

    private void setDielectricVariable(EnsTagConfiguration config, int position, int id) {
        String variable = config.getVariableToReadFrom().get(position);
        JSONObject jsonObj = (JSONObject) this.json.get(id);

        //@Todo: --------------------------> This switch should be made only in the end of it all
        switch(config.getEnsVariableName().get(position)) {
            case EnsParametersUtil.:
                this.productDataEnsList.get(position).setSerial((long) jsonObj.get(variable));
            break;
            default:
                //Trow error
        }
    }

    private void setFunctionalVariable(EnsTagConfiguration config, int position, int id) {

    }

    private void setLoadVariable(EnsTagConfiguration config, int position, int id) {

    }

    private void setReneableVariable(EnsTagConfiguration config, int position, int id) {

    }






















    /* 
    //@Todo: probably transform whole obj in json and then only pass one part of the list
    private void setProductVariable(EnsTagConfiguration config, int position, int id) {
        String variable = config.getVariableToReadFrom().get(position);
        JSONObject jsonObj = (JSONObject) this.json.get(id);

        //@Todo: --------------------------> This switch should be made only in the end of it all
        switch(config.getEnsVariableName().get(position)) {
            case EnsParametersUtil.SERIAL_PRODUCT:
                this.productDataEnsList.get(position).setSerial((long) jsonObj.get(variable));
            break;
            case EnsParametersUtil.START_DATE_TIME_PRODUCT:
                this.productDataEnsList.get(position).setStartDatetime((Date) jsonObj.get(variable));
            break;
            case EnsParametersUtil.END_DATE_TIME_PRODUCT:
                this.productDataEnsList.get(position).setEndDatetime((Date) jsonObj.get(variable));
            break;
            case EnsParametersUtil.RELEASED_PRODUCT:
                this.productDataEnsList.get(position).setReleased(jsonObj.get(variable));
            break;
            case EnsParametersUtil.TEST_STATUS_PRODUCT:
                this.productDataEnsList.get(position).setTestStatus(jsonObj.get(variable));
            break;
            case EnsParametersUtil.FAILURE_CODE_PRODUCT:
                this.productDataEnsList.get(position).setFailureCode(jsonObj.get(variable));
            break;
            case EnsParametersUtil.FAILURE_CODE_SAP_PRODUCT:
                this.productDataEnsList.get(position).setFailureCodeSap(jsonObj.get(variable));
            break;
            case EnsParametersUtil.FAILURE_DESCRIPTION_PRODUCT:
                this.productDataEnsList.get(position).setFailureDescription(jsonObj.get(variable));
            break;
            default:
                //Trow error
        }
    }

    private void setDielectricVariable(EnsTagConfiguration config, int position, int id) {
        String variable = config.getVariableToReadFrom().get(position);
        JSONObject jsonObj = (JSONObject) this.json.get(id);

        switch() {
            case EnsParametersUtil.TEST_RESULT_DIELECTRIC:
                
            break;
            case EnsParametersUtil.START_DATE_TIME_DIELECTRIC:
                
            break;
            case EnsParametersUtil.END_DATE_TIME_DIELECTRIC:
                
            break;
            case EnsParametersUtil.VOLTAGE_SETPOINT_IN_MA_DIELECTRIC:
                
            break;
            case EnsParametersUtil.CURRENT_SETPOINT_IN_MA_DIELECTRIC:
                
            break;
            case EnsParametersUtil.TEST_DURATION_DIELECTRIC:
                
            break;
            case EnsParametersUtil.DURATION_TIME_UNIT_DIELECTRIC:
                
            break;
            case EnsParametersUtil.EMPLOYEE_CODE_DIELECTRIC:
                
            break;
            default:
                //Trow error
        }
    }

    private void setFunctionalVariable(EnsTagConfiguration config, int position, int id) {
        String variable = config.getVariableToReadFrom().get(position);
        JSONObject jsonObj = (JSONObject) this.json.get(id);

        switch() {
            case EnsParametersUtil.TEST_RESULT_FUNCTIONAL:
                
            break;
            case EnsParametersUtil.START_DATE_TIME_FUNCTIONAL:
                
            break;
            case EnsParametersUtil.END_DATE_TIME_FUNCTIONAL:
                
            break;
            case EnsParametersUtil.TEST_DURATION_FUNCTIONAL:
                
            break;
            case EnsParametersUtil.TIME_UNIT_FUNCTIONAL:
                
            break;
            case EnsParametersUtil.EMPLOYEE_CODE_FUNCTIONAL:
                
            break;
            case EnsParametersUtil.TEST_POSITION_FUNCTIONAL:
                
            break;
            case EnsParametersUtil.FAULT_DESCRIPTION_FUNCTIONAL:
                
            break;
            default:
                //Trow error
        }
    }

    private void setLoadVariable(EnsTagConfiguration config, int position, int id) {
        String variable = config.getVariableToReadFrom().get(position);
        JSONObject jsonObj = (JSONObject) this.json.get(id);

        switch() {
            case EnsParametersUtil.TEST_RESULT_LOAD:
                
            break;
            case EnsParametersUtil.START_DATE_TIME_LOAD:
                
            break;
            case EnsParametersUtil.END_DATE_TIME_LOAD:
                
            break;
            case EnsParametersUtil.TEST_DURATION_LOAD:
                
            break;
            case EnsParametersUtil.TIME_UNIT_LOAD:
                
            break;
            case EnsParametersUtil.EMPLOYEE_CODE_LOAD:
                
            break;
            case EnsParametersUtil.TEST_POSITION_LOAD:
                
            break;
            case EnsParametersUtil.VOLTAGE_SETPOINT_LOAD:
                
            break;
            case EnsParametersUtil.DERATING_PERCENTAGE_LOAD:
                
            break;
            case EnsParametersUtil.SPEED_SETPOINT_IN_HZ_LOAD:
                
            break;
            case EnsParametersUtil.SPEED_SETPOINT_IN_RPM_LOAD:
                
            break;
            case EnsParametersUtil.CURRENT_SETPOINT_LOAD:
                
            break;
            case EnsParametersUtil.TIME_UNTIL_FAILURE_LOAD:
                
            break;
            case EnsParametersUtil.START_DERATING_TEMPERATURE_SETPOINT_LOAD:
                
            break;
            case EnsParametersUtil.CSV_BIN_FILE_LOAD:
                
            break;
            case EnsParametersUtil.FAULT_DESCRIPTION_LOAD:
                
            break;
            default:
                //Trow error
        }
    }

    private void setReneableVariable(EnsTagConfiguration config, int position, int id) {
        String variable = config.getVariableToReadFrom().get(position);
        JSONObject jsonObj = (JSONObject) this.json.get(id);

        switch() {
            case EnsParametersUtil.TEST_RESULT_RENEABLE:
                
            break;
            case EnsParametersUtil.TEST_DURATION_RENEABLE:
                
            break;
            case EnsParametersUtil.EMPLOYEE_CODE_RENEABLE:
                
            break;
            case EnsParametersUtil.POSITION_RENEABLE:
                
            break;
            case EnsParametersUtil.UNIT_RENEABLE:
                
            break;
            case EnsParametersUtil.START_DATE_TIME_RENEABLE:
                
            break;
            case EnsParametersUtil.END_DATE_TIME_RENEABLE:
                
            break;
            case EnsParametersUtil.VOLTAGE_MAIN_RENEABLE:
                
            break;
            case EnsParametersUtil.NETWORK_FREQUENCY_RENEABLE:
                
            break;
            case EnsParametersUtil.RECTIFIER_VOLTAGE_RENEABLE:
                
            break;
            case EnsParametersUtil.RECTIFIER_FREQUENCY_RENEABLE:
                
            break;
            case EnsParametersUtil.COOLANT_TEMPERATURE_RENEABLE:
                
            break;
            case EnsParametersUtil.COOLANT_SYSTEM_PRESSURE_RENEABLE:
                
            break;
            case EnsParametersUtil.WDC_TIME_UNTIL_FAILURE_RENEABLE:
                
            break;
            case EnsParametersUtil.DURATION_TIME_UNIT_RENEABLE:
                
            break;
            case EnsParametersUtil.DEFECT_RENEABLE:
            
            break;
            default:
                //Trow error
        }
        */
    }















    //@Todo: Search method for each position and each serial

    //@Todo: Implementar adição de dados
    /* 
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

    public String[] getSerial() {
        return this.serial;
    }

    public void setSerial(String[] serial) {
        this.serial = serial;
    }


    private void mockClass(int someArraySize) {
        for (int i = 0; i < someArraySize; i++) {
            
        }
    }
    

    ////@Todo: (Mock Functions) implement it on Ens's library (one declarable function in each class and one that integrate in a separate class) and take it out a here
    private void listEveryEnsVariable() {
        //1) JsonObject or HashMap to get names and types (maybe type is not needed)
        //2) 
        //3) 
    }
    */
}
