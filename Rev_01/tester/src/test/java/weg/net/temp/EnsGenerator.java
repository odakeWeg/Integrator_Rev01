package weg.net.temp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import net.weg.wdc.ens.ProductDataEns;
import net.weg.wdc.ens.ReneableEnergyCharacteristicList;
import net.weg.wdc.ens.entity.WdcReneableEnergyTestMeasurePointType;
import net.weg.wdc.ens.entity.WdcTestResultType;
import net.weg.wdc.ens.entity.WdcTimeUnitType;
import net.weg.wdc.ens.factory.InputCharacteristicLineFactory;

public class EnsGenerator {
    @Test
    public void createAndReadEnsObj() throws IOException, ParseException {
        //1) Create and Fill Ens Obj
        int serial = 1075246609;

		ReneableEnergyCharacteristicList reneable = new ReneableEnergyCharacteristicList();
		reneable.setTestResult(WdcTestResultType.Approved);
		reneable.setTestDuration(60);
		reneable.setEmployeeCode(7881);
		reneable.setPosition(1);
		reneable.setUnit(1);
		reneable.setDurationTimeUnit(WdcTimeUnitType.SECONDS);
		reneable.setStartDateTime(new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(4)));
		reneable.setEndDateTime(new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(3)));
		reneable.setVoltageMain(220);
		reneable.setNetworkFrequency(60);
		reneable.setCoolantTemperature(25);
		reneable.setCoolantSystemPressure(10);
		reneable.setWdcTimeUntilFailure(9);
		reneable.setDefect("nope");
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentInvU, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentInvV, WdcTestResultType.Approved, 54, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentInvW, WdcTestResultType.Approved, 51, 84, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempInvU, WdcTestResultType.Approved, 51, 81, 72));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempInvV, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempInvW, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempInductor, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentRetU, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentRetV, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentRetW, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempRetU1, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempRetV1, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempRetW1, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempRetU2, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempRetV2, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempRetW2, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentParallelU, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentParallelV, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.CurrentParallelW, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempLiqArref, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.VazaoArref, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.PressArref, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempoTestSetPoint, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TensaoAlim, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.FreqAlim, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TensaoRet, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.FreqRet, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.TempLiqArrefSetPoint, WdcTestResultType.Approved, 51, 81, 75));
		reneable.getTestInputCharacteristics().add(InputCharacteristicLineFactory.create(WdcReneableEnergyTestMeasurePointType.PressArrefSetPoint, WdcTestResultType.Approved, 51, 81, 75));

		ProductDataEns productDataEns = new ProductDataEns();
		productDataEns.setSerial(serial);
		productDataEns.setStartDatetime(new Date(System.currentTimeMillis() - TimeUnit.MINUTES.toMillis(5)));
		productDataEns.setEndDatetime(new Date());
		productDataEns.setReleased(true);
		productDataEns.setTestStatus((byte) 1);
		productDataEns.setFailureCode(0);
		productDataEns.setFailureCodeSap(0);
		productDataEns.setFailureDescription("");
		productDataEns.setReneableEnergyCharacteristicList(reneable);

		System.out.println("Created and Filled - OK");

        //2) Transform in JSON
        ObjectMapper mapper = new ObjectMapper();
        String ensObj = mapper.writeValueAsString(productDataEns);

        System.out.println("Json conversion - OK");
        System.out.println(ensObj);
        mapper.writeValue(new File("src/test/resources/testEns1.json"), productDataEns);

        //3) Retrieve JSON
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("src/test/resources/testEns1.json"));
        JSONObject jsonObject = (JSONObject) obj;

        ProductDataEns dtoObject = new ProductDataEns();
        dtoObject = mapper.readValue(jsonObject.toString(), dtoObject.getClass());

        //4) Assert elements to check vericity
        System.out.println(dtoObject);
    }

    @Test
    public void createProductErasingPartOfTheJson() throws FileNotFoundException, IOException, ParseException {
        ObjectMapper mapper = new ObjectMapper();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader("src/test/resources/testEns1.json"));
        JSONObject jsonObject = (JSONObject) obj;

        ProductDataEns dtoObject = new ProductDataEns();
        dtoObject = mapper.readValue(jsonObject.toString(), dtoObject.getClass());

        //4) Assert elements to check vericity
        System.out.println(dtoObject);
    }
}
