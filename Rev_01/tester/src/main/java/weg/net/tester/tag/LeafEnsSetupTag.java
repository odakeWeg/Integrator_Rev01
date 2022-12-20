package weg.net.tester.tag;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import net.weg.wdc.ens.DielectricTestCharacteristicList;
import net.weg.wdc.ens.FunctionalTestCharacteristicList;
import net.weg.wdc.ens.LoadTestCharacteristicList;
import net.weg.wdc.ens.ProductDataEns;
import net.weg.wdc.ens.ReneableEnergyCharacteristicList;
import net.weg.wdc.ens.entity.WdcTestResultType;
import net.weg.wdc.ens.entity.WdcTimeUnitType;
import weg.net.tester.utils.ActionCommandUtil;
import weg.net.tester.utils.EnsParametersUtil;
import weg.net.tester.utils.FailureCodeUtil;
import weg.net.tester.utils.TagNameUtil;

@Getter
@Setter
public class LeafEnsSetupTag extends NodeEnsTag {
    //@Todo: refactor
    //@Todo: This tag can only be used once for positions

    //@Todo: make this class take from material after?

    protected boolean start;
    protected String selectedItem;

    //Product
    //protected boolean enableProduct; //Form
    protected long serialProduct;   //runtime sap
	//protected Date startDatetimeProduct;    //runtime somewhere
	//protected Date endDatetimeProduct;  //runtime somewhere
	//protected boolean releasedProduct;  //runtime end
	//protected byte testStatusProduct;   //runtime end
	//protected int failureCodeProduct;   //runtime end
	//protected int failureCodeSapProduct;    //runtime end
	//protected String failureDescriptionProduct; //runtime end

    //Dielectric
    //protected boolean enableDielectric; //Form
    //protected WdcTestResultType testResultDielectric;   //runtime end
	//protected Date startDateTimeDielectric; //runtime somewhere
	//protected Date endDateTimeDielectric;   //runtime somewhere
	protected int voltageSetpointDielectric;    //Form? material?
	protected float currentSetpointIn_mADielectric; //Form? material?
	protected float currentAcceptanceIn_mADielectric;   //Form? material?
	protected int testDurationDielectric;   //Form? material? runtime end?
	protected WdcTimeUnitType durationTimeUnitDielectric;   //Form: treat in command
	protected int employeeCodeDielectric;   //runtime session or so

    //Functional
    //protected boolean enableFunctional; //Form
    //protected WdcTestResultType testResultFunctional;   //runtime end
	//protected Date startDateTimeFunctional; //runtime somewhere
	//protected Date endDateTimeFunctional;   //runtime somewhere
	//protected int testDurationFunctional;   //Form? material? runtime end?
	protected WdcTimeUnitType timeUnitFunctional;   //Form: treat in command
	protected int employeeCodeFunctional;   //runtime session or so
	//protected int testPositionFunctional;   //Form
	//protected String faultDescriptionFunctional;    //runtime end
    
    //Load
    //protected boolean enableLoad;    //Form
    //protected WdcTestResultType testResultLoad; //runtime end
	//protected Date startDateTimeLoad;   //runtime somewhere
	//protected Date endDateTimeLoad; //runtime somewhere
	//protected int testDurationLoad; //Form? material? runtime end?
	protected WdcTimeUnitType timeUnitLoad; //Form: treat in command
	protected int employeeCodeLoad; //runtime session or so
	//protected String testPositionLoad;  //Form
	protected int voltageSetpointLoad;  //Form? material?
	protected int temperatureSetpointLoad;  //Form? material?
	protected int deratingPercentageLoad;   //Form? material?
	protected int speedSetpointInHzLoad;    //Form? material?
	protected int speedSetpointInRpmLoad;   //Form? material?
	protected float currentSetpointLoad;    //Form? material?
	//protected int timeUntilFailureLoad; //runtime somewhere
	protected int startDeratingTemperatureSetpointLoad; //Form? material?
	//protected String csvBinFileLoad;    //runtime end
	//protected String faultDescriptionLoad;  //runtime end

    //Renewable
    //protected boolean enableReneable;   //Form
    //protected WdcTestResultType testResultReneable; //runtime end
    //protected int testDurationReneable; //Form? material? runtime end?
    protected int employeeCodeReneable; //runtime session or so
    //protected int positionReneable; //Form
    protected int unitReneable; //Form? material?
    //protected Date startDateTimeReneable;   //runtime somewhere
	//protected Date endDateTimeReneable; //runtime somewhere
    protected int voltageMainReneable;  //Form? material?
    protected float networkFrequencyReneable;   //Form? material?
    protected int rectifierVoltageReneable; //Form? material?
    protected int rectifierFrequencyReneable;   //Form? material?
    protected float coolantTemperatureReneable; //Form? material?
    protected float coolantSystemPressureReneable;  //Form? material?
    //protected int WdcTimeUntilFailureReneable;  //runtime end
    protected WdcTimeUnitType durationTimeUnitReneable; //Treat in command
    //protected String defectReneable;    //runtime end

    @Override
    public void executeCommand() {
        //1) Unificação de dados?
        //2) Somente os necessários e o resto o Ens connector faz? -> Parece mais correto
        //3) Retirar atributos desnecessários 
        //4) Uma Tag para cada?

        testResult = FailureCodeUtil.OK;
        log = "Setup inicial do ENS realizado com sucesso.";
        action = ActionCommandUtil.EXIBIT_VALUES;

        //1) There is an instance of the product in the TestMetaDataModel? -> then this class would not be protected
        //2) 

        mapValues();
    }

    private void mapValues() {
        int lastId;
        for(int position = 0; position < TestMetaDataModel.productDataEnsList.size(); position++) {
            ProductDataEns product = TestMetaDataModel.productDataEnsList.get(this.position);
            if(start) {
                TestMetaDataModel.initialEnsId = this.id; 
                switch(selectedItem) {
                    case EnsParametersUtil.PRODUCT_DATA_ENS_TYPE:
                        productInitialSetup(product);
                    break;
                    case EnsParametersUtil.DIELECTRIC_TEST_CHARACTERISTIC_LIST_TYPE:
                        dielectricInitialSetup(product);
                    break;
                    case EnsParametersUtil.FUNCTIONAL_TEST_CHARACTERISTIC_LIST_TYPE:
                        functionalInitialSetup(product);
                    break;
                    case EnsParametersUtil.LOAD_TEST_CHARACTERISTIC_LIST_TYPE:
                        loadInitialSetup(product);
                    break;
                    case EnsParametersUtil.RENEABLE_TEST_CHARACTERISTIC_LIST_TYPE:
                        reneableInitialSetup(product);
                    break;
                }
            } else {
                lastId = TestMetaDataModel.initialEnsId;
                switch(selectedItem) {
                    case EnsParametersUtil.PRODUCT_DATA_ENS_TYPE:
                        productFinalSetup(product, lastId, position);
                    break;
                    case EnsParametersUtil.DIELECTRIC_TEST_CHARACTERISTIC_LIST_TYPE:
                        dielectricFinalSetup(product, lastId, position);
                    break;
                    case EnsParametersUtil.FUNCTIONAL_TEST_CHARACTERISTIC_LIST_TYPE:
                        functionalFinalSetup(product, lastId, position);
                    break;
                    case EnsParametersUtil.LOAD_TEST_CHARACTERISTIC_LIST_TYPE:
                        loadFinalSetup(product, lastId, position);
                    break;
                    case EnsParametersUtil.RENEABLE_TEST_CHARACTERISTIC_LIST_TYPE:
                        reneableFinalSetup(product, lastId, position);
                    break;
                }
            }
        }
    }

    private void productInitialSetup(ProductDataEns product) {
        product.setSerial(serialProduct);
        product.setStartDatetime(new Date());
    }

    private void dielectricInitialSetup(ProductDataEns product) {
        DielectricTestCharacteristicList dielectric = new DielectricTestCharacteristicList();
        dielectric.setStartDateTime(new Date());
        dielectric.setVoltageSetpoint(voltageSetpointDielectric);
        dielectric.setCurrentSetpointIn_mA(currentSetpointIn_mADielectric);
        dielectric.setCurrentAcceptanceIn_mA(currentAcceptanceIn_mADielectric);
        dielectric.setDurationTimeUnit(durationTimeUnitDielectric);
        product.setDielectricTestCharacteristicList(dielectric);
    }

    private void functionalInitialSetup(ProductDataEns product) {
        FunctionalTestCharacteristicList functional = new FunctionalTestCharacteristicList();
        functional.setStartDateTime(new Date());
        functional.setTimeUnit(timeUnitFunctional);
        functional.setEmployeeCode(employeeCodeFunctional);
        functional.setTestPosition(this.position);
        product.setFunctionalTestCharacteristicList(functional);
    }

    private void loadInitialSetup(ProductDataEns product) {
        LoadTestCharacteristicList load = new LoadTestCharacteristicList();
        load.setStartDateTime(new Date());
        load.setTimeUnit(durationTimeUnitDielectric);
        load.setEmployeeCode(employeeCodeDielectric);
        load.setTestPosition(String.valueOf(this.position));
        load.setVoltageSetpoint(voltageSetpointDielectric);
        load.setTemperatureSetpoint(startDeratingTemperatureSetpointLoad);
        load.setDeratingPercentage(deratingPercentageLoad);
        load.setSpeedSetpointInHz(speedSetpointInHzLoad);
        load.setSpeedSetpointInRpm(speedSetpointInRpmLoad);
        load.setCurrentSetpoint(currentSetpointIn_mADielectric);
        load.setStartDeratingTemperatureSetpoint(startDeratingTemperatureSetpointLoad);
        product.setLoadTestCharacteristicList(load);
    }

    private void reneableInitialSetup(ProductDataEns product) {
        ReneableEnergyCharacteristicList reneable = new ReneableEnergyCharacteristicList();
        reneable.setEmployeeCode(employeeCodeDielectric);
        reneable.setPosition(this.position);
        reneable.setUnit(unitReneable);
        reneable.setStartDateTime(new Date());
        reneable.setVoltageMain(voltageMainReneable);
        reneable.setNetworkFrequency(networkFrequencyReneable);
        reneable.setRectifierVoltage(rectifierVoltageReneable);
        reneable.setRectifierFrequency(rectifierFrequencyReneable);
        reneable.setCoolantTemperature(coolantTemperatureReneable);
        reneable.setCoolantSystemPressure(coolantSystemPressureReneable);
        reneable.setDurationTimeUnit(durationTimeUnitDielectric);
        product.setReneableEnergyCharacteristicList(reneable);
    }

    private void productFinalSetup(ProductDataEns product, int lastId, int position) {
        product.setEndDatetime(new Date());
        if(TestMetaDataModel.isPositionEnabled[position].get()) {
            product.setReleased(true);
            product.setTestStatus((byte) 1);
            //protected String failureDescriptionProduct; //@Todo: maybe later
        } else {
            product.setReleased(false);
            product.setTestStatus((byte) 2);
            //protected String failureDescriptionProduct; //@Todo: maybe later
        }
    }

    private void dielectricFinalSetup(ProductDataEns product, int lastId, int position) {
        product.getDielectricTestCharacteristicList().setEndDateTime(new Date());
        product.getDielectricTestCharacteristicList().setTestDuration(testDurationDielectric);
        product.getDielectricTestCharacteristicList().setTestResult(WdcTestResultType.Approved);

        /* 
        //protected int testDurationDielectric;   //Form? material? runtime end?
        if(TestMetaDataModel.isPositionEnabled[position].get()) {
            //protected WdcTestResultType testResultDielectric;   //runtime end
        } else {
            //protected WdcTestResultType testResultDielectric;   //runtime end
        }
        */
    }

    private void functionalFinalSetup(ProductDataEns product, int lastId, int position) {
        product.getFunctionalTestCharacteristicList().setEndDateTime(new Date());
        long testDuration = product.getFunctionalTestCharacteristicList().getEndDateTime().getTime() - product.getFunctionalTestCharacteristicList().getStartDateTime().getTime();
        product.getFunctionalTestCharacteristicList().setTestDuration((int) testDuration);
        if(TestMetaDataModel.isPositionEnabled[position].get()) {
            product.getFunctionalTestCharacteristicList().setTestResult(WdcTestResultType.Approved);
            //protected String faultDescriptionFunctional;    //@Todo: maybe later
        } else {
            product.getFunctionalTestCharacteristicList().setTestResult(WdcTestResultType.Failed);
            //protected String faultDescriptionFunctional;    //@Todo: maybe later
        }
    }

    private void loadFinalSetup(ProductDataEns product, int lastId, int position) {
        product.getLoadTestCharacteristicList().setEndDateTime(new Date());
        long testDuration = product.getLoadTestCharacteristicList().getEndDateTime().getTime() - product.getLoadTestCharacteristicList().getStartDateTime().getTime();
        product.getLoadTestCharacteristicList().setTestDuration((int) testDuration);
        if(TestMetaDataModel.isPositionEnabled[position].get()) {
            product.getLoadTestCharacteristicList().setTestResult(WdcTestResultType.Approved);
            //protected int timeUntilFailureLoad;  //@Todo: maybe later
            //protected String csvBinFileLoad;     //@Todo: maybe later
            //protected String faultDescriptionLoad;   //@Todo: maybe later
        } else {
            product.getLoadTestCharacteristicList().setTestResult(WdcTestResultType.Failed);
            //protected int timeUntilFailureLoad;  //@Todo: maybe later
            //protected String csvBinFileLoad;     //@Todo: maybe later
            //protected String faultDescriptionLoad;   //@Todo: maybe later
        }
    }

    private void reneableFinalSetup(ProductDataEns product, int lastId, int position) {
        product.getReneableEnergyCharacteristicList().setEndDateTime(new Date());
        long testDuration = product.getReneableEnergyCharacteristicList().getEndDateTime().getTime() - product.getReneableEnergyCharacteristicList().getStartDateTime().getTime();
        product.getReneableEnergyCharacteristicList().setTestDuration((int) testDuration);
        if(TestMetaDataModel.isPositionEnabled[position].get()) {
            product.getReneableEnergyCharacteristicList().setTestResult(WdcTestResultType.Approved);
            //protected int WdcTimeUntilFailureReneable;   //@Todo: maybe later
            //protected String defectReneable;     //@Todo: maybe later
        } else {
            product.getReneableEnergyCharacteristicList().setTestResult(WdcTestResultType.Failed);
            //protected int WdcTimeUntilFailureReneable;   //@Todo: maybe later
            //protected String defectReneable;     //@Todo: maybe later
        }
    }

    @Override
    public void setTagName() {
        this.tagName = TagNameUtil.ENS_SETUP;
    }
}
