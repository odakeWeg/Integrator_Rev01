package weg.net.tester.utils;


public enum EnsParametersUtil {
    PRODUCT_DATA_ENS_TYPE,
    DIELECTRIC_TEST_CHARACTERISTIC_LIST_TYPE,
    FUNCTIONAL_TEST_CHARACTERISTIC_LIST_TYPE,
    LOAD_TEST_CHARACTERISTIC_LIST_TYPE,
    RENEABLE_TEST_CHARACTERISTIC_LIST_TYPE,

    ANALOG_INPUTS_FUNCTIONAL,
    ANALOG_OUTPUTS_FUNCTIONAL,
    MOTOR_SPEED_50_FUNCTIONAL,
    MOTOR_SPEED_100_FUNCTIONAL,
    POWER_SUPPLY_24_FUNCTIONAL,
    POWER_SUPPLY_REF_P_FUNCTIONAL,
    POWER_SUPPLY_REF_N_FUNCTIONAL,
    POWER_SUPPLY_5_INTERNAL_FUNCTIONAL,
    POWER_SUPPLY_15_ENCODER_FUNCTIONAL,
    POWER_SUPPLY_10_FUNCTIONAL,
    CURRENT_INDICATION_FUNCTIONAL,
    LINK_VOLTAGE_INDICATION_FUNCTIONAL,
    DIGITAL_INPUTS_FUNCTIONAL,
    DIGITAL_OUTPUTS_FUNCTIONAL,
    BRAKING_FUNCTIONAL,
    OUTPUT_SHORT_CIRCUIT_DETECTION_FUNCTIONAL,
    EARTH_FAULT_DETECTION_FUNCTIONAL,
    HARDWARE_IDENTIFICATION_FUNCTIONAL,
    USB_AND_SERIAL_COMMUNICATION_FUNCTIONAL,
    MEMORY_CARD_FUNCTIONAL,
    HEAT_SINK_FAN_FUNCTIONAL,

    CURRENT_INV_U_RENEABLE,
    CURRENT_INV_V_RENEABLE,
    CURRENT_INV_W_RENEABLE,
    TEMP_INV_U_RENEABLE,
    TEMP_INV_V_RENEABLE,
    TEMP_INV_W_RENEABLE,
    TEMP_INDUCTOR_RENEABLE,
    CURRENT_RET_U_RENEABLE,
    CURRENT_RET_V_RENEABLE,
    CURRENT_RET_W_RENEABLE,
    TEMP_RET_U1_RENEABLE,
    TEMP_RET_V1_RENEABLE,
    TEMP_RET_W1_RENEABLE,
    TEMP_RET_U2_RENEABLE,
    TEMP_RET_V2_RENEABLE,
    TEMP_RET_W2_RENEABLE,
    CURRENT_PARALLEL_U_RENEABLE,
    CURRENT_PARALLEL_V_RENEABLE,
    CURRENT_PARALLEL_W_RENEABLE,
    TEMP_LIQ_ARREF_RENEABLE,
    VAZAO_ARREF_RENEABLE,
    PRESS_ARREF_RENEABLE,
    TEMPO_TEST_SETPOINT_RENEABLE,
    TENSAO_ALIM_RENEABLE,
    FREQ_ALIM_RENEABLE,
    TENSAO_RET_RENEABLE,
    FREQ_RET_RENEABLE,
    TEMP_LIQ_ARREF_SETPOINT_RENEABLE,
    PRESS_ARREF_SETPOINT_RENEABLE
}



/* 
public class EnsParametersUtil {
    //@Todo: Change this stuff for an Enum, easier to iterate Throw names
    //Maybe iteration could be made directly from the library class
    
    public static final String PRODUCT_DATA_ENS_TYPE = "PRODUCT_DATA_ENS_TYPE";
    public static final String DIELECTRIC_TEST_CHARACTERISTIC_LIST_TYPE = "DIELECTRIC_TEST_CHARACTERISTIC_LIST_TYPE";
    public static final String FUNCTIONAL_TEST_CHARACTERISTIC_LIST_TYPE = "FUNCTIONAL_TEST_CHARACTERISTIC_LIST_TYPE";
    public static final String LOAD_TEST_CHARACTERISTIC_LIST_TYPE = "LOAD_TEST_CHARACTERISTIC_LIST_TYPE";
    public static final String RENEABLE_TEST_CHARACTERISTIC_LIST_TYPE = "RENEABLE_TEST_CHARACTERISTIC_LIST_TYPE";

    public static final String ANALOG_INPUTS_FUNCTIONAL = "ANALOG_INPUTS_FUNCTIONAL"; 
    public static final String ANALOG_OUTPUTS_FUNCTIONAL = "ANALOG_OUTPUTS_FUNCTIONAL";
    public static final String MOTOR_SPEED_50_FUNCTIONAL = "MOTOR_SPEED_50_FUNCTIONAL";
    public static final String MOTOR_SPEED_100_FUNCTIONAL = "MOTOR_SPEED_100_FUNCTIONAL";
    public static final String POWER_SUPPLY_24_FUNCTIONAL = "POWER_SUPPLY_24_FUNCTIONAL";
    public static final String POWER_SUPPLY_REF_P_FUNCTIONAL = "POWER_SUPPLY_REF_P_FUNCTIONAL";
    public static final String POWER_SUPPLY_REF_N_FUNCTIONAL = "POWER_SUPPLY_REF_N_FUNCTIONAL";
    public static final String POWER_SUPPLY_5_INTERNAL_FUNCTIONAL = "POWER_SUPPLY_5_INTERNAL_FUNCTIONAL";
    public static final String POWER_SUPPLY_15_ENCODER_FUNCTIONAL = "POWER_SUPPLY_15_ENCODER_FUNCTIONAL";
    public static final String POWER_SUPPLY_10_FUNCTIONAL = "POWER_SUPPLY_10_FUNCTIONAL";
    public static final String CURRENT_INDICATION_FUNCTIONAL = "CURRENT_INDICATION_FUNCTIONAL";
    public static final String LINK_VOLTAGE_INDICATION_FUNCTIONAL = "LINK_VOLTAGE_INDICATION_FUNCTIONAL";
    public static final String DIGITAL_INPUTS_FUNCTIONAL = "DIGITAL_INPUTS_FUNCTIONAL";
    public static final String DIGITAL_OUTPUTS_FUNCTIONAL = "DIGITAL_OUTPUTS_FUNCTIONAL";
    public static final String BRAKING_FUNCTIONAL = "BRAKING_FUNCTIONAL";
    public static final String OUTPUT_SHORT_CIRCUIT_DETECTION_FUNCTIONAL = "OUTPUT_SHORT_CIRCUIT_DETECTION_FUNCTIONAL";
    public static final String EARTH_FAULT_DETECTION_FUNCTIONAL = "EARTH_FAULT_DETECTION_FUNCTIONAL";
    public static final String HARDWARE_IDENTIFICATION_FUNCTIONAL = "HARDWARE_IDENTIFICATION_FUNCTIONAL";
    public static final String USB_AND_SERIAL_COMMUNICATION_FUNCTIONAL = "USB_AND_SERIAL_COMMUNICATION_FUNCTIONAL";
    public static final String MEMORY_CARD_FUNCTIONAL = "MEMORY_CARD";
    public static final String HEAT_SINK_FAN_FUNCTIONAL = "HEAT_SINK_FAN";

    public static final String CURRENT_INV_U_RENEABLE = "CURRENT_INV_U_RENEABLE";
    public static final String CURRENT_INV_V_RENEABLE = "CURRENT_INV_V_RENEABLE";
    public static final String CURRENT_INV_W_RENEABLE = "CURRENT_INV_W_RENEABLE";
    public static final String TEMP_INV_U_RENEABLE = "TEMP_INV_U_RENEABLE";
    public static final String TEMP_INV_V_RENEABLE = "TEMP_INV_V_RENEABLE";
    public static final String TEMP_INV_W_RENEABLE = "TEMP_INV_W_RENEABLE";
    public static final String TEMP_INDUCTOR_RENEABLE = "TEMP_INDUCTOR_RENEABLE";
    public static final String CURRENT_RET_U_RENEABLE = "CURRENT_RET_U_RENEABLE";
    public static final String CURRENT_RET_V_RENEABLE = "CURRENT_RET_V_RENEABLE";
    public static final String CURRENT_RET_W_RENEABLE = "CURRENT_RET_W_RENEABLE";
    public static final String TEMP_RET_U1_RENEABLE = "TEMP_RET_U1_RENEABLE";
    public static final String TEMP_RET_V1_RENEABLE = "TEMP_RET_V1_RENEABLE";
    public static final String TEMP_RET_W1_RENEABLE = "TEMP_RET_W1_RENEABLE";
    public static final String TEMP_RET_U2_RENEABLE = "TEMP_RET_U2_RENEABLE";
    public static final String TEMP_RET_V2_RENEABLE = "TEMP_RET_V2_RENEABLE";
    public static final String TEMP_RET_W2_RENEABLE = "TEMP_RET_W2_RENEABLE";
    public static final String CURRENT_PARALLEL_U_RENEABLE = "CURRENT_PARALLEL_U_RENEABLE";
    public static final String CURRENT_PARALLEL_V_RENEABLE = "CURRENT_PARALLEL_V_RENEABLE";
    public static final String CURRENT_PARALLEL_W_RENEABLE = "CURRENT_PARALLEL_W_RENEABLE";
    public static final String TEMP_LIQ_ARREF_RENEABLE = "TEMP_LIQ_ARREF_RENEABLE";
    public static final String VAZAO_ARREF_RENEABLE = "VAZAO_ARREF_RENEABLE";
    public static final String PRESS_ARREF_RENEABLE = "PRESS_ARREF_RENEABLE";
    public static final String TEMPO_TEST_SETPOINT_RENEABLE = "TEMPO_TEST_SETPOINT_RENEABLE";
    public static final String TENSAO_ALIM_RENEABLE = "TENSAO_ALIM_RENEABLE";
    public static final String FREQ_ALIM_RENEABLE = "FREQ_ALIM_RENEABLE";
    public static final String TENSAO_RET_RENEABLE = "TENSAO_RET_RENEABLE";
    public static final String FREQ_RET_RENEABLE = "FREQ_RET_RENEABLE";
    public static final String TEMP_LIQ_ARREF_SETPOINT_RENEABLE = "TEMP_LIQ_ARREF_SETPOINT_RENEABLE";
    public static final String PRESS_ARREF_SETPOINT_RENEABLE = "PRESS_ARREF_SETPOINT_RENEABLE";
*/











    /* 
    public static final String SERIAL_PRODUCT = "serial produto";
    public static final String START_DATE_TIME_PRODUCT = "startDateTime produto";
    public static final String END_DATE_TIME_PRODUCT = "endDateTime produto";
    public static final String RELEASED_PRODUCT = "released produto";
    public static final String TEST_STATUS_PRODUCT = "testStatus produto";
    public static final String FAILURE_CODE_PRODUCT = "failureCode produto";
    public static final String FAILURE_CODE_SAP_PRODUCT = "failureCodeSap produto";
    public static final String FAILURE_DESCRIPTION_PRODUCT = "failureDescription produto";

    public static final String TEST_RESULT_DIELECTRIC = "testResult dieletrico";
    public static final String START_DATE_TIME_DIELECTRIC = "startDateTime dieletrico";
    public static final String END_DATE_TIME_DIELECTRIC = "endDateTime dieletrico";
    public static final String VOLTAGE_SETPOINT_IN_MA_DIELECTRIC = "voltageSetpointInMa dieletrico";
    public static final String CURRENT_SETPOINT_IN_MA_DIELECTRIC = "currentSetpointInMa dieletrico";
    public static final String TEST_DURATION_DIELECTRIC = "testDuration dieletrico";
    public static final String DURATION_TIME_UNIT_DIELECTRIC = "durationTimeUnit dieletrico";
    public static final String EMPLOYEE_CODE_DIELECTRIC = "employeeCode dieletrico";

    public static final String TEST_RESULT_FUNCTIONAL = "testResult funcional";
    public static final String START_DATE_TIME_FUNCTIONAL = "startDateTime funcional";
    public static final String END_DATE_TIME_FUNCTIONAL = "endDateTime funcional";
    public static final String TEST_DURATION_FUNCTIONAL = "testDuration funcional";
    public static final String TIME_UNIT_FUNCTIONAL = "timeUnit funcional";
    public static final String EMPLOYEE_CODE_FUNCTIONAL = "employeeCode funcional";
    public static final String TEST_POSITION_FUNCTIONAL = "testPosition funcional";
    public static final String FAULT_DESCRIPTION_FUNCTIONAL = "faultDescription funcional";

    public static final String TEST_RESULT_LOAD = "testResult carga";
    public static final String START_DATE_TIME_LOAD = "startDateTime carga";
    public static final String END_DATE_TIME_LOAD = "endDateTime carga";
    public static final String TEST_DURATION_LOAD = "testDuration carga";
    public static final String TIME_UNIT_LOAD = "timeUnit carga";
    public static final String EMPLOYEE_CODE_LOAD = "employeeCode carga";
    public static final String TEST_POSITION_LOAD = "testPosition carga";
    public static final String VOLTAGE_SETPOINT_LOAD = "voltageSetpoint carga";
    public static final String DERATING_PERCENTAGE_LOAD = "deratingPercentage carga";
    public static final String SPEED_SETPOINT_IN_HZ_LOAD = "speedSetpointInHz carga";
    public static final String SPEED_SETPOINT_IN_RPM_LOAD = "speedSetpointInRpm carga";
    public static final String CURRENT_SETPOINT_LOAD = "currentSetpoing carga";
    public static final String TIME_UNTIL_FAILURE_LOAD = "timeUntilFailure carga";
    public static final String START_DERATING_TEMPERATURE_SETPOINT_LOAD = "startDeratingTemperatureSetpoint carga";
    public static final String CSV_BIN_FILE_LOAD = "csvBinFile carga";
    public static final String FAULT_DESCRIPTION_LOAD = "faultDescription carga";

    public static final String TEST_RESULT_RENEABLE = "testResult renovavel";
    public static final String TEST_DURATION_RENEABLE = "testDuration renovavel";
    public static final String EMPLOYEE_CODE_RENEABLE = "employeeCode renovavel";
    public static final String POSITION_RENEABLE = "position renovavel";
    public static final String UNIT_RENEABLE = "unit renovavel";
    public static final String START_DATE_TIME_RENEABLE = "startDateTime renovavel";
    public static final String END_DATE_TIME_RENEABLE = "endDateTime renovavel";
    public static final String VOLTAGE_MAIN_RENEABLE = "voltageMain renovavel";
    public static final String NETWORK_FREQUENCY_RENEABLE = "networkFrequency renovavel";
    public static final String RECTIFIER_VOLTAGE_RENEABLE = "rectifierVoltage renovavel";
    public static final String RECTIFIER_FREQUENCY_RENEABLE = "rectifierFrequency renovavel";
    public static final String COOLANT_TEMPERATURE_RENEABLE = "coolantTemperature renovavel";
    public static final String COOLANT_SYSTEM_PRESSURE_RENEABLE = "coolantSystemPressure renovavel";
    public static final String WDC_TIME_UNTIL_FAILURE_RENEABLE = "wdcTimeUntilFailure renovavel";
    public static final String DURATION_TIME_UNIT_RENEABLE = "durationTimeUnit renovavel";
    public static final String DEFECT_RENEABLE = "defect renovavel";
    */

