package weg.net.tester.facade;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;

import net.weg.soa.serviceclient.JaxWsUsernameTokenClientConfigurator;
import net.weg.wdc.ens.DielectricTestCharacteristicList;
import net.weg.wdc.ens.FunctionalTestCharacteristicList;
import net.weg.wdc.ens.LoadTestCharacteristicList;
import net.weg.wdc.ens.ProductDataEns;
import net.weg.wdc.ens.ReneableEnergyCharacteristicList;
import net.weg.wdc.ens.entity.EnsServiceConfig;
import net.weg.wdc.ens.entity.WdcFunctionalTestMeasurePointType;
import net.weg.wdc.ens.entity.WdcReneableEnergyTestMeasurePointType;
import net.weg.wdc.ens.entity.WdcTestResultType;
import net.weg.wdc.ens.factory.EnsServiceEntitiesFactory;
import net.weg.wdc.ens.factory.InputCharacteristicLineFactory;
import net.weg.wdc.ens.service.EnsServiceImpl;
import weg.net.tester.converter.BaseConverter;
import weg.net.tester.converter.JsonObjConverter;
import weg.net.tester.exception.EnsException;
import weg.net.tester.models.ens.EnsTagConfiguration;
import weg.net.tester.tag.TagList;
import weg.net.tester.tag.TestMetaDataModel;
import weg.net.tester.utils.EnsParametersUtil;

@Configuration
public class EnsConnector {
    //@Todo: Refactor this class ASAP
    //@Todo: maybe dont save it when the test is over, use a parallel thread to do so and make a check in the mongo repository
    private static String BEAN_XML_FILEPATH = "conf/app-config.xml";

    private List<ProductDataEns> productDataEnsList = new ArrayList<>();
    private List<ReneableEnergyCharacteristicList> reneableList = new ArrayList<>();
    //private List<DielectricTestCharacteristicList> dielectricList = new ArrayList<>();
    //private List<FunctionalTestCharacteristicList> functionalList = new ArrayList<>();
    //private List<LoadTestCharacteristicList> loadList = new ArrayList<>();

    private TagList tagList;
    private JSONArray json;

    public void saveEns(TagList tagList) throws EnsException, JsonProcessingException, ParseException {
        BaseConverter base = new JsonObjConverter();
        this.json = base.objToJsonArrayConverter(tagList); //transform in a array obj
        this.tagList = tagList;

        //@Todo: put ens data
        instantiatePositionObj(tagList.qntOfProductInTest());
        setEnsObjMesures();
        //setEnsObj();
        saveEveryEnabledPosition(); //@Todo: create enabler value on ens
    }

    //private void setEnsObj() {
        /* 
        //1) Should ens be in metaTest?
        for (int id = 0; id < tagList.getList().size(); id++) {
            //1) encontra tag init e final
            //2) executa uma funcao the começo ou fim
            if(tagList.getList().get(id).getTagIdentifier().equals(TagNameUtil.ENS_SETUP)) {
                tagList.getList().get(id)
            }
        }
        */
        /* 
        this.productDataEnsList = TestMetaDataModel.productDataEnsList;
        for (int position = 0; position < tagList.qntOfProductInTest(); position++) {
            productDataEnsList.get(position).getReneableEnergyCharacteristicList().setTestInputCharacteristics(reneableList.get(position).getTestInputCharacteristics());
            
            //Ens não tem um set dessa variavel
            //productDataEnsList.get(position).getFunctionalTestCharacteristicList().setTestInputCharacteristics(functionalList.get(position).getTestInputCharacteristics());
        }
        */
    //}

    private void setEnsObjMesures() {
        for (int id = 0; id < tagList.getList().size(); id++) {
            if (tagList.getList().get(id).getEnsTagConfiguration().isEnabled()) {
                for (int position = 0; position < tagList.getList().get(id).getEnsTagConfiguration().getEnsType().size(); position++) {
                    switch(tagList.getList().get(id).getEnsTagConfiguration().getEnsType().get(position)) {
                        //case EnsParametersUtil.PRODUCT_DATA_ENS_TYPE:
                        //    setProductVariable(tagList.getList().get(id).getEnsTagConfiguration(), position, id);
                        //break;
                        //case EnsParametersUtil.DIELECTRIC_TEST_CHARACTERISTIC_LIST_TYPE:
                        //    setDielectricVariable(tagList.getList().get(id).getEnsTagConfiguration(), position, id);
                        //break;
                        case EnsParametersUtil.FUNCTIONAL_TEST_CHARACTERISTIC_LIST_TYPE:
                            setFunctionalVariable(tagList.getList().get(id).getEnsTagConfiguration(), position, id);
                        break;
                        //case EnsParametersUtil.LOAD_TEST_CHARACTERISTIC_LIST_TYPE:
                        //    setLoadVariable(tagList.getList().get(id).getEnsTagConfiguration(), position, id);
                        //break;
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
        /* 
        for (int i = 0; i < qnt; i++) {
            productDataEnsList.add(new ProductDataEns());
            reneableList.add(new ReneableEnergyCharacteristicList());
            dielectricList.add(new DielectricTestCharacteristicList());
            functionalList.add(new FunctionalTestCharacteristicList());
            loadList.add(new LoadTestCharacteristicList());
        }
        */
        productDataEnsList = TestMetaDataModel.productDataEnsList;
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

    private void setFunctionalVariable(EnsTagConfiguration config, int position, int id) {
        String variable = config.getVariableToReadFrom().get(position);
        JSONObject jsonObj = (JSONObject) this.json.get(id);

        //@Todo: --------------------------> This switch should be made only in the end of it all
        switch(config.getEnsVariableName().get(position)) {
            case EnsParametersUtil.ANALOG_INPUTS_FUNCTIONAL:
                //this.productDataEnsList.get(position).setSerial((long) jsonObj.get(variable));
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.AnalogInputs, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.ANALOG_OUTPUTS_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.AnalogOutputs, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.MOTOR_SPEED_50_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.MotorSpeed50, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.MOTOR_SPEED_100_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.MotorSpeed100, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.POWER_SUPPLY_24_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.PowerSupply24, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.POWER_SUPPLY_REF_P_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.PowerSupplyRefP, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.POWER_SUPPLY_REF_N_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.PowerSupplyRefN, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.POWER_SUPPLY_5_INTERNAL_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.PowerSupply5Internal, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.POWER_SUPPLY_15_ENCODER_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.PowerSupply15Encoder, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.POWER_SUPPLY_10_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.PowerSupply10, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.CURRENT_INDICATION_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.CurrentIndication, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.LINK_VOLTAGE_INDICATION_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.LinkVoltageIndication, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.DIGITAL_INPUTS_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.DigitalInputs, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.DIGITAL_OUTPUTS_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.DigitalOutputs, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.BRAKING_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.Braking, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.OUTPUT_SHORT_CIRCUIT_DETECTION_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.OutputShortCircuitDetection, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.EARTH_FAULT_DETECTION_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.EarthFaultDetection, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.HARDWARE_IDENTIFICATION_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.HardwareIdentification, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.USB_AND_SERIAL_COMMUNICATION_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.USBandSerialCommunication, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.MEMORY_CARD_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.MemoryCard, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.HEAT_SINK_FAN_FUNCTIONAL:
                productDataEnsList.get(position).getFunctionalTestCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcFunctionalTestMeasurePointType.HeatSinkFan, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            default:
                //Trow error
        }
    }

    private void setReneableVariable(EnsTagConfiguration config, int position, int id) {
        String variable = config.getVariableToReadFrom().get(position);
        JSONObject jsonObj = (JSONObject) this.json.get(id);

        //@Todo: --------------------------> This switch should be made only in the end of it all
        switch(config.getEnsVariableName().get(position)) {
            case EnsParametersUtil.CURRENT_INV_U_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentInvU, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.CURRENT_INV_V_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentInvV, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.CURRENT_INV_W_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentInvW, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.TEMP_INV_U_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempInvU, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.TEMP_INV_V_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempInvV, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.TEMP_INV_W_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempInvW, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.TEMP_INDUCTOR_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempInductor, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.CURRENT_RET_U_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentRetU, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.CURRENT_RET_V_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentRetV, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.CURRENT_RET_W_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentRetW, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.TEMP_RET_U1_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempRetU1, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.TEMP_RET_V1_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempRetV1, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.TEMP_RET_W1_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempRetW1, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.TEMP_RET_U2_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempRetU2, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.TEMP_RET_V2_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempRetV2, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.TEMP_RET_W2_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempRetW2, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.CURRENT_PARALLEL_U_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentParallelU, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.CURRENT_PARALLEL_V_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentParallelV, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.CURRENT_PARALLEL_W_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentParallelW, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.TEMP_LIQ_ARREF_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempLiqArref, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.VAZAO_ARREF_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.VazaoArref, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.PRESS_ARREF_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.PressArref, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.TEMPO_TEST_SETPOINT_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempoTestSetPoint, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.TENSAO_ALIM_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TensaoAlim, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.FREQ_ALIM_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.FreqAlim, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.TENSAO_RET_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TensaoRet, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.FREQ_RET_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.FreqRet, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.TEMP_LIQ_ARREF_SETPOINT_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempLiqArrefSetPoint, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            case EnsParametersUtil.PRESS_ARREF_SETPOINT_RENEABLE:
                productDataEnsList.get(position).getReneableEnergyCharacteristicList().getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.PressArrefSetPoint, getTolerancyResult(config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)), config.getMinValue().get(id), config.getMaxValue().get(id), (String) jsonObj.get(variable)));
            break;
            default:
                //Trow error
        }
    }

    private WdcTestResultType getTolerancyResult(String minString, String maxString, String resultString) {
        float min = Float.parseFloat(minString);
        float max = Float.parseFloat(maxString);
        float result = Float.parseFloat(resultString);
        if(result <= max && result >= min) {
            return WdcTestResultType.Approved;
        } else {
            return WdcTestResultType.Failed;
        }
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
