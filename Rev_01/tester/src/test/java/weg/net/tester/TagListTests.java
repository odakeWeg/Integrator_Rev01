package weg.net.tester;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.weg.wdc.ens.entity.WdcTimeUnitType;
import weg.net.tester.converter.JsonObjConverter;
import weg.net.tester.tag.LeafEnsSetupTag;
import weg.net.tester.tag.LeafEthernetCommunicationTag;
import weg.net.tester.tag.LeafRegisterCompareTag;
import weg.net.tester.tag.LeafTestTag;
import weg.net.tester.tag.LeafWriteTag;
import weg.net.tester.tag.TagList;
import weg.net.tester.utils.EnsParametersUtil;

public class TagListTests {
    @Test
    public void smallRoutineTest() throws StreamWriteException, DatabindException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, ParseException {
        TagList tagList = new TagList();
        tagList.setList(new ArrayList<>());
        LeafTestTag leafTestTag = new LeafTestTag();
        leafTestTag.setId(0);
        leafTestTag.setTestName("teste 1");;
        leafTestTag.setTimeout(10);
        leafTestTag.setPosition(1);
        tagList.getList().add(leafTestTag);
        LeafEnsSetupTag leafEnsSetupTag = new LeafEnsSetupTag();
        leafEnsSetupTag.setId(1);
        leafEnsSetupTag.setPosition(1);
        leafEnsSetupTag.setTimeout(10000);
        leafEnsSetupTag.setSelectedItem(EnsParametersUtil.PRODUCT_DATA_ENS_TYPE);
        leafEnsSetupTag.setStart(true);
        leafEnsSetupTag.setSerialProduct(1234567890);

        leafEnsSetupTag.setVoltageSetpointDielectric(10);
        leafEnsSetupTag.setCurrentAcceptanceIn_mADielectric(20);
        leafEnsSetupTag.setCurrentSetpointIn_mADielectric(30);
        leafEnsSetupTag.setTestDurationDielectric(40);
        leafEnsSetupTag.setDurationTimeUnitDielectric(WdcTimeUnitType.SECONDS);
        //leafEnsSetupTag.setEmployeeCodeDielectric(7881);

        leafEnsSetupTag.setTimeUnitFunctional(WdcTimeUnitType.MINUTES);
        //leafEnsSetupTag.setEmployeeCodeFunctional(7881);

        leafEnsSetupTag.setTimeUnitLoad(WdcTimeUnitType.MINUTES);
        //leafEnsSetupTag.setEmployeeCodeLoad(7881);
        leafEnsSetupTag.setVoltageSetpointLoad(50);
        leafEnsSetupTag.setTemperatureSetpointLoad(60);
        leafEnsSetupTag.setDeratingPercentageLoad(70);
        leafEnsSetupTag.setSpeedSetpointInHzLoad(80);
        leafEnsSetupTag.setSpeedSetpointInRpmLoad(90);
        leafEnsSetupTag.setCurrentSetpointLoad(100);
        leafEnsSetupTag.setStartDeratingTemperatureSetpointLoad(110);

        //leafEnsSetupTag.setEmployeeCodeReneable(7881);
        leafEnsSetupTag.setUnitReneable(120);
        leafEnsSetupTag.setVoltageMainReneable(130);
        leafEnsSetupTag.setNetworkFrequencyReneable(140);
        leafEnsSetupTag.setRectifierVoltageReneable(150);
        leafEnsSetupTag.setRectifierFrequencyReneable(160);
        leafEnsSetupTag.setCoolantTemperatureReneable(170);
        leafEnsSetupTag.setCoolantSystemPressureReneable(180);
        leafEnsSetupTag.setDurationTimeUnitReneable(WdcTimeUnitType.MINUTES);

        tagList.getList().add(leafEnsSetupTag);
        LeafEnsSetupTag leafEnsSetupTag3 = new LeafEnsSetupTag();
        leafEnsSetupTag3.setId(2);
        leafEnsSetupTag3.setPosition(1);
        leafEnsSetupTag3.setTimeout(10000);
        leafEnsSetupTag3.setSelectedItem(EnsParametersUtil.RENEABLE_TEST_CHARACTERISTIC_LIST_TYPE);
        leafEnsSetupTag3.setStart(true);

        leafEnsSetupTag3.setDurationTimeUnitReneable(WdcTimeUnitType.MINUTES);
        leafEnsSetupTag3.setUnitReneable(1);
        //leafEnsSetupTag3.setEmployeeCodeReneable(7881);
        leafEnsSetupTag3.setUnitReneable(120);
        leafEnsSetupTag3.setVoltageMainReneable(130);
        leafEnsSetupTag3.setNetworkFrequencyReneable(140);
        leafEnsSetupTag3.setRectifierVoltageReneable(150);
        leafEnsSetupTag3.setRectifierFrequencyReneable(160);
        leafEnsSetupTag3.setCoolantTemperatureReneable(170);
        leafEnsSetupTag3.setCoolantSystemPressureReneable(180);

        tagList.getList().add(leafEnsSetupTag3);
        LeafEthernetCommunicationTag leafEthernetCommunicationTag = new LeafEthernetCommunicationTag();
        leafEthernetCommunicationTag.setId(3);
        leafEthernetCommunicationTag.setCommunicationName("PLC");
        leafEthernetCommunicationTag.setIp("192.168.0.10");
        leafEthernetCommunicationTag.setPort(502);
        leafEthernetCommunicationTag.setAddress(255);
        leafEthernetCommunicationTag.setTimeout(10);
        leafEthernetCommunicationTag.setTrials(1);
        leafEthernetCommunicationTag.setPosition(1);
        tagList.getList().add(leafEthernetCommunicationTag);
        LeafTestTag leafTestTag2 = new LeafTestTag();
        leafTestTag2.setId(4);
        leafTestTag2.setTestName("teste 2");;
        leafTestTag2.setTimeout(10);
        leafTestTag2.setPosition(1);
        tagList.getList().add(leafTestTag2);
        LeafRegisterCompareTag leafRegisterCompareTag = new LeafRegisterCompareTag();
        leafRegisterCompareTag.setCommunicationNameOnTest("PLC");
        leafRegisterCompareTag.setCommunicationNameRef("PLC");
        leafRegisterCompareTag.setRegisterOnTest(8000);
        leafRegisterCompareTag.setRegisterRef(8000);
        leafRegisterCompareTag.setCalculateBy("Absolute");
        leafRegisterCompareTag.setTolerancy(10);
        leafRegisterCompareTag.setWaitBefore(10);
        leafRegisterCompareTag.setWaitAfter(10);
        leafRegisterCompareTag.setId(5);
        leafRegisterCompareTag.setTimeout(10);
        leafRegisterCompareTag.setPosition(1);

        leafRegisterCompareTag.getEnsTagConfiguration().setEnabled(true);
        leafRegisterCompareTag.getEnsTagConfiguration().getEnsType().add(EnsParametersUtil.RENEABLE_TEST_CHARACTERISTIC_LIST_TYPE);
        leafRegisterCompareTag.getEnsTagConfiguration().getMaxValue().add("30");
        leafRegisterCompareTag.getEnsTagConfiguration().getMinValue().add("10");
        leafRegisterCompareTag.getEnsTagConfiguration().getEnsVariableName().add(EnsParametersUtil.CURRENT_INV_U_RENEABLE);
        leafRegisterCompareTag.getEnsTagConfiguration().getVariableToReadFrom().add("valueOnTest");
        //
        leafRegisterCompareTag.getEnsTagConfiguration().getEnsType().add(EnsParametersUtil.RENEABLE_TEST_CHARACTERISTIC_LIST_TYPE);
        leafRegisterCompareTag.getEnsTagConfiguration().getMaxValue().add("40");
        leafRegisterCompareTag.getEnsTagConfiguration().getMinValue().add("10");
        leafRegisterCompareTag.getEnsTagConfiguration().getEnsVariableName().add(EnsParametersUtil.CURRENT_PARALLEL_U_RENEABLE);
        leafRegisterCompareTag.getEnsTagConfiguration().getVariableToReadFrom().add("valueRef");
        tagList.getList().add(leafRegisterCompareTag);
        LeafWriteTag leafWriteTag = new LeafWriteTag();
        leafWriteTag.setId(6);
        leafWriteTag.setCommunicationName(null);
        leafWriteTag.setCommunicationName("PLC");
        leafWriteTag.setRegister(8004);
        leafWriteTag.setValue(1);
        leafWriteTag.setWaitBefore(10);
        leafWriteTag.setWaitAfter(10);
        leafWriteTag.setTimeout(10);
        leafWriteTag.setPosition(1);
        tagList.getList().add(leafWriteTag);
        LeafRegisterCompareTag leafRegisterCompareTag2 = new LeafRegisterCompareTag();
        leafRegisterCompareTag2.setCommunicationNameOnTest("PLC");
        leafRegisterCompareTag2.setCommunicationNameRef("PLC");
        leafRegisterCompareTag2.setRegisterOnTest(8000);
        leafRegisterCompareTag2.setRegisterRef(8000);
        leafRegisterCompareTag2.setCalculateBy("Absolute");
        leafRegisterCompareTag2.setTolerancy(100);
        leafRegisterCompareTag2.setWaitBefore(10);
        leafRegisterCompareTag2.setWaitAfter(10);
        leafRegisterCompareTag2.setId(7);
        leafRegisterCompareTag2.setTimeout(10);
        leafRegisterCompareTag2.setPosition(1);
        tagList.getList().add(leafRegisterCompareTag2);
        LeafEnsSetupTag leafEnsSetupTag4 = new LeafEnsSetupTag();
        leafEnsSetupTag4.setId(8);
        leafEnsSetupTag4.setPosition(1);
        leafEnsSetupTag4.setTimeout(10000);
        leafEnsSetupTag4.setStart(false);
        leafEnsSetupTag4.setSelectedItem(EnsParametersUtil.RENEABLE_TEST_CHARACTERISTIC_LIST_TYPE);
        tagList.getList().add(leafEnsSetupTag4);
        LeafEnsSetupTag leafEnsSetupTag2 = new LeafEnsSetupTag();
        leafEnsSetupTag2.setId(9);
        leafEnsSetupTag2.setPosition(1);
        leafEnsSetupTag2.setTimeout(10000);
        leafEnsSetupTag2.setStart(false);
        leafEnsSetupTag2.setSelectedItem(EnsParametersUtil.PRODUCT_DATA_ENS_TYPE);
        tagList.getList().add(leafEnsSetupTag2);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/test8.json"), tagList);

        JsonObjConverter converter = new JsonObjConverter();
        File file = new File("src/test/resources/test8.json");
        TagList tagList2 = converter.convertFromJsonToObj(file);
        Assert.assertEquals(tagList.getList().get(0).getId(), tagList2.getList().get(0).getId());
        Assert.assertEquals(tagList.getList().get(0).getTagName(), tagList2.getList().get(0).getTagName());
        Assert.assertEquals(tagList.getList().get(0).getTagIdentifier(), tagList2.getList().get(0).getTagIdentifier());
    }
}
