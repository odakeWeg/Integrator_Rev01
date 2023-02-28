package weg.net.temp;

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
import weg.net.tester.exception.TestUnmarshalingException;
import weg.net.tester.tag.LeafEnsSetupTag;
import weg.net.tester.tag.LeafEthernetCommunicationTag;
import weg.net.tester.tag.LeafRegisterCompareTag;
import weg.net.tester.tag.LeafTestTag;
import weg.net.tester.tag.LeafUserConfirmationTag;
import weg.net.tester.tag.LeafUserInputTag;
import weg.net.tester.tag.LeafVariableCompareTag;
import weg.net.tester.tag.LeafVariableWriteTag;
import weg.net.tester.tag.LeafWriteTag;
import weg.net.tester.tag.TagList;
import weg.net.tester.utils.EnsParametersUtil;

public class RoutineGenerator {
    @Test
    public void jsonGenerator() throws TestUnmarshalingException, StreamWriteException, DatabindException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, ParseException {
        TagList tagList = new TagList();
        tagList.setList(new ArrayList<>());
        LeafTestTag leafTestTag = new LeafTestTag();
        leafTestTag.setId(0);
        leafTestTag.setTestName("teste 1");;
        leafTestTag.setTimeout(1000);
        leafTestTag.setPosition(1);
        tagList.getList().add(leafTestTag);
        LeafEthernetCommunicationTag leafEthernetCommunicationTag = new LeafEthernetCommunicationTag();
        leafEthernetCommunicationTag.setId(1);
        leafEthernetCommunicationTag.setCommunicationName("PLC");
        leafEthernetCommunicationTag.setIp("192.168.0.10");
        leafEthernetCommunicationTag.setPort(502);
        leafEthernetCommunicationTag.setAddress(255);
        leafEthernetCommunicationTag.setTimeout(10);
        leafEthernetCommunicationTag.setPosition(1);
        tagList.getList().add(leafEthernetCommunicationTag);
        LeafTestTag leafTestTag2 = new LeafTestTag();
        leafTestTag2.setId(2);
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
        leafRegisterCompareTag.setId(3);
        leafRegisterCompareTag.setTimeout(10);
        leafRegisterCompareTag.setPosition(1);
        tagList.getList().add(leafRegisterCompareTag);
        LeafWriteTag leafWriteTag = new LeafWriteTag();
        leafWriteTag.setId(4);
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
        leafRegisterCompareTag2.setId(5);
        leafRegisterCompareTag2.setTimeout(10);
        leafRegisterCompareTag2.setPosition(1);
        tagList.getList().add(leafRegisterCompareTag2);
        

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/test4.json"), tagList);

        JsonObjConverter converter = new JsonObjConverter();
        File file = new File("src/test/resources/test4.json");
        TagList tagList2 = converter.convertFromJsonToObj(file);
        Assert.assertEquals(tagList.getList().get(0).getId(), tagList2.getList().get(0).getId());
        Assert.assertEquals(tagList.getList().get(0).getTagName(), tagList2.getList().get(0).getTagName());
        Assert.assertEquals(tagList.getList().get(0).getTagIdentifier(), tagList2.getList().get(0).getTagIdentifier());
    }

    @Test
    public void userConfirmationTagTest() throws StreamWriteException, DatabindException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, ParseException {
        TagList tagList = new TagList();
        tagList.setList(new ArrayList<>());
        LeafTestTag leafTestTag = new LeafTestTag();
        leafTestTag.setId(0);
        leafTestTag.setTestName("teste 1");;
        leafTestTag.setTimeout(1000);
        leafTestTag.setPosition(1);
        tagList.getList().add(leafTestTag);
        LeafUserConfirmationTag leafUserConfirmationTag = new LeafUserConfirmationTag();
        leafUserConfirmationTag.setId(1);
        leafUserConfirmationTag.setMessageToDisplay("My confirmation message");
        leafUserConfirmationTag.setTimeout(10);
        leafUserConfirmationTag.setPosition(1);
        tagList.getList().add(leafUserConfirmationTag);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/test5.json"), tagList);

        JsonObjConverter converter = new JsonObjConverter();
        File file = new File("src/test/resources/test5.json");
        TagList tagList2 = converter.convertFromJsonToObj(file);
        Assert.assertEquals(tagList.getList().get(0).getId(), tagList2.getList().get(0).getId());
        Assert.assertEquals(tagList.getList().get(0).getTagName(), tagList2.getList().get(0).getTagName());
        Assert.assertEquals(tagList.getList().get(0).getTagIdentifier(), tagList2.getList().get(0).getTagIdentifier());
    }

    @Test
    public void userInputTagTest() throws StreamWriteException, DatabindException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, ParseException {
        TagList tagList = new TagList();
        tagList.setList(new ArrayList<>());
        LeafTestTag leafTestTag = new LeafTestTag();
        leafTestTag.setId(0);
        leafTestTag.setTestName("teste 1");;
        leafTestTag.setTimeout(1000);
        leafTestTag.setPosition(1);
        tagList.getList().add(leafTestTag);
        LeafEthernetCommunicationTag leafEthernetCommunicationTag = new LeafEthernetCommunicationTag();
        leafEthernetCommunicationTag.setId(1);
        leafEthernetCommunicationTag.setCommunicationName("PLC");
        leafEthernetCommunicationTag.setIp("192.168.0.10");
        leafEthernetCommunicationTag.setPort(502);
        leafEthernetCommunicationTag.setAddress(255);
        leafEthernetCommunicationTag.setTimeout(10);
        leafEthernetCommunicationTag.setPosition(1);
        tagList.getList().add(leafEthernetCommunicationTag);
        LeafUserInputTag leafUserInputTag = new LeafUserInputTag();
        leafUserInputTag.setId(2);
        leafUserInputTag.setMessageToDisplay("My Input message");
        leafUserInputTag.setTimeout(10);
        leafUserInputTag.setPosition(1);
        leafUserInputTag.setCommunicationNameRef("PLC");
        leafUserInputTag.setRegisterRef(8000);
        leafUserInputTag.setCalculateBy("Absolute");
        leafUserInputTag.setTolerancy(10);
        leafUserInputTag.setWaitAfter(1);
        leafUserInputTag.setWaitBefore(1);
        tagList.getList().add(leafUserInputTag);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/test6.json"), tagList);

        JsonObjConverter converter = new JsonObjConverter();
        File file = new File("src/test/resources/test6.json");
        TagList tagList2 = converter.convertFromJsonToObj(file);
        Assert.assertEquals(tagList.getList().get(0).getId(), tagList2.getList().get(0).getId());
        Assert.assertEquals(tagList.getList().get(0).getTagName(), tagList2.getList().get(0).getTagName());
        Assert.assertEquals(tagList.getList().get(0).getTagIdentifier(), tagList2.getList().get(0).getTagIdentifier());
    }

    @Test
    public void variableReadTest() throws StreamWriteException, DatabindException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, ParseException {
        TagList tagList = new TagList();
        tagList.setList(new ArrayList<>());
        LeafTestTag leafTestTag = new LeafTestTag();
        leafTestTag.setId(0);
        leafTestTag.setTestName("teste 1");;
        leafTestTag.setTimeout(1000);
        leafTestTag.setPosition(1);
        tagList.getList().add(leafTestTag);
        LeafEthernetCommunicationTag leafEthernetCommunicationTag = new LeafEthernetCommunicationTag();
        leafEthernetCommunicationTag.setId(1);
        leafEthernetCommunicationTag.setCommunicationName("PLC");
        leafEthernetCommunicationTag.setIp("192.168.0.10");
        leafEthernetCommunicationTag.setPort(502);
        leafEthernetCommunicationTag.setAddress(255);
        leafEthernetCommunicationTag.setTimeout(10);
        leafEthernetCommunicationTag.setPosition(1);
        tagList.getList().add(leafEthernetCommunicationTag);
        LeafVariableCompareTag leafVariableCompareTag = new LeafVariableCompareTag();
        leafVariableCompareTag.setId(2);
        leafVariableCompareTag.setTimeout(10);
        leafVariableCompareTag.setPosition(1);
        leafVariableCompareTag.setCommunicationNameRef("PLC");
        leafVariableCompareTag.setRegisterRef(8000);
        leafVariableCompareTag.setCalculateBy("Absolute");
        leafVariableCompareTag.setTolerancy(10);
        leafVariableCompareTag.setVariableName("serial1");
        leafVariableCompareTag.setWaitAfter(1);
        leafVariableCompareTag.setWaitBefore(1);
        tagList.getList().add(leafVariableCompareTag);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/test7.json"), tagList);

        JsonObjConverter converter = new JsonObjConverter();
        File file = new File("src/test/resources/test7.json");
        TagList tagList2 = converter.convertFromJsonToObj(file);
        Assert.assertEquals(tagList.getList().get(0).getId(), tagList2.getList().get(0).getId());
        Assert.assertEquals(tagList.getList().get(0).getTagName(), tagList2.getList().get(0).getTagName());
        Assert.assertEquals(tagList.getList().get(0).getTagIdentifier(), tagList2.getList().get(0).getTagIdentifier());
    }

    @Test
    public void variableWriteTest() throws StreamWriteException, DatabindException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, ParseException {
        TagList tagList = new TagList();
        tagList.setList(new ArrayList<>());
        LeafTestTag leafTestTag = new LeafTestTag();
        leafTestTag.setId(0);
        leafTestTag.setTestName("teste 1");;
        leafTestTag.setTimeout(1000);
        leafTestTag.setPosition(1);
        tagList.getList().add(leafTestTag);
        LeafEthernetCommunicationTag leafEthernetCommunicationTag = new LeafEthernetCommunicationTag();
        leafEthernetCommunicationTag.setId(1);
        leafEthernetCommunicationTag.setCommunicationName("PLC");
        leafEthernetCommunicationTag.setIp("192.168.0.10");
        leafEthernetCommunicationTag.setPort(502);
        leafEthernetCommunicationTag.setAddress(255);
        leafEthernetCommunicationTag.setTimeout(10);
        leafEthernetCommunicationTag.setPosition(1);
        tagList.getList().add(leafEthernetCommunicationTag);
        LeafVariableWriteTag leafVariableWriteTag = new LeafVariableWriteTag();
        leafVariableWriteTag.setId(2);
        leafVariableWriteTag.setTimeout(10);
        leafVariableWriteTag.setPosition(1);
        leafVariableWriteTag.setCommunicationNameRef("PLC");
        leafVariableWriteTag.setRegisterRef(8000);
        leafVariableWriteTag.setVariableName("serial1");
        leafVariableWriteTag.setWaitAfter(1);
        leafVariableWriteTag.setWaitBefore(1);
        tagList.getList().add(leafVariableWriteTag);
        LeafVariableCompareTag leafVariableCompareTag = new LeafVariableCompareTag();
        leafVariableCompareTag.setId(2);
        leafVariableCompareTag.setTimeout(10);
        leafVariableCompareTag.setPosition(1);
        leafVariableCompareTag.setCommunicationNameRef("PLC");
        leafVariableCompareTag.setRegisterRef(8000);
        leafVariableCompareTag.setCalculateBy("Absolute");
        leafVariableCompareTag.setTolerancy(10);
        leafVariableCompareTag.setVariableName("serial1");
        leafVariableCompareTag.setWaitAfter(1);
        leafVariableCompareTag.setWaitBefore(1);
        tagList.getList().add(leafVariableCompareTag);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/test7.json"), tagList);

        JsonObjConverter converter = new JsonObjConverter();
        File file = new File("src/test/resources/test7.json");
        TagList tagList2 = converter.convertFromJsonToObj(file);
        Assert.assertEquals(tagList.getList().get(0).getId(), tagList2.getList().get(0).getId());
        Assert.assertEquals(tagList.getList().get(0).getTagName(), tagList2.getList().get(0).getTagName());
        Assert.assertEquals(tagList.getList().get(0).getTagIdentifier(), tagList2.getList().get(0).getTagIdentifier());
    }

    @Test
    public void testingMultiThreading() throws StreamWriteException, DatabindException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, ParseException {
        TagList tagList = new TagList();
        tagList.setList(new ArrayList<>());
        LeafTestTag leafTestTag = new LeafTestTag();
        leafTestTag.setId(0);
        leafTestTag.setTestName("teste 1");;
        leafTestTag.setTimeout(1000000);
        leafTestTag.setPosition(1);
        tagList.getList().add(leafTestTag);
        LeafEthernetCommunicationTag leafEthernetCommunicationTag = new LeafEthernetCommunicationTag();
        leafEthernetCommunicationTag.setId(1);
        leafEthernetCommunicationTag.setCommunicationName("PLC");
        leafEthernetCommunicationTag.setIp("192.168.0.10");
        leafEthernetCommunicationTag.setPort(502);
        leafEthernetCommunicationTag.setAddress(255);
        leafEthernetCommunicationTag.setTimeout(10000);
        leafEthernetCommunicationTag.setPosition(1);
        leafEthernetCommunicationTag.setMainCommunication(true);
        tagList.getList().add(leafEthernetCommunicationTag);
        LeafVariableWriteTag leafVariableWriteTag = new LeafVariableWriteTag();
        leafVariableWriteTag.setId(2);
        leafVariableWriteTag.setTimeout(10000);
        leafVariableWriteTag.setPosition(1);
        leafVariableWriteTag.setCommunicationNameRef("PLC");
        leafVariableWriteTag.setRegisterRef(8000);
        leafVariableWriteTag.setVariableName("serial1");
        leafVariableWriteTag.setWaitAfter(1);
        leafVariableWriteTag.setWaitBefore(1);
        tagList.getList().add(leafVariableWriteTag);
        LeafVariableCompareTag leafVariableCompareTag = new LeafVariableCompareTag();
        leafVariableCompareTag.setId(2);
        leafVariableCompareTag.setTimeout(10000);
        leafVariableCompareTag.setPosition(1);
        leafVariableCompareTag.setCommunicationNameRef("PLC");
        leafVariableCompareTag.setRegisterRef(8000);
        leafVariableCompareTag.setCalculateBy("Absolute");
        leafVariableCompareTag.setTolerancy(10);
        leafVariableCompareTag.setVariableName("serial1");
        leafVariableCompareTag.setWaitAfter(1);
        leafVariableCompareTag.setWaitBefore(1);
        tagList.getList().add(leafVariableCompareTag);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/test8.json"), tagList);

        JsonObjConverter converter = new JsonObjConverter();
        File file = new File("src/test/resources/test8.json");
        TagList tagList2 = converter.convertFromJsonToObj(file);
        Assert.assertEquals(tagList.getList().get(0).getId(), tagList2.getList().get(0).getId());
        Assert.assertEquals(tagList.getList().get(0).getTagName(), tagList2.getList().get(0).getTagName());
        Assert.assertEquals(tagList.getList().get(0).getTagIdentifier(), tagList2.getList().get(0).getTagIdentifier());
    }

    @Test
    public void userInputPlusEnsTest() throws StreamWriteException, DatabindException, IOException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, ParseException {
        TagList tagList = new TagList();
        tagList.setList(new ArrayList<>());
        LeafTestTag leafTestTag = new LeafTestTag();
        leafTestTag.setId(0);
        leafTestTag.setTestName("teste 1");;
        leafTestTag.setTimeout(1000);
        leafTestTag.setPosition(1);
        tagList.getList().add(leafTestTag);
        LeafEthernetCommunicationTag leafEthernetCommunicationTag = new LeafEthernetCommunicationTag();
        leafEthernetCommunicationTag.setId(1);
        leafEthernetCommunicationTag.setCommunicationName("PLC");
        leafEthernetCommunicationTag.setIp("192.168.0.10");
        leafEthernetCommunicationTag.setPort(502);
        leafEthernetCommunicationTag.setAddress(255);
        leafEthernetCommunicationTag.setTimeout(10);
        leafEthernetCommunicationTag.setPosition(1);
        tagList.getList().add(leafEthernetCommunicationTag);

        LeafEnsSetupTag leafEnsSetupTag = new LeafEnsSetupTag();
        leafEnsSetupTag.setId(2);
        leafEnsSetupTag.setPosition(1);
        leafEnsSetupTag.setTimeout(10000);
        leafEnsSetupTag.setSelectedItem(EnsParametersUtil.PRODUCT_DATA_ENS_TYPE.name());
        leafEnsSetupTag.setStart(true);
        leafEnsSetupTag.setSerialProduct(1234567890);
        //leafEnsSetupTag.setSerialProduct(parseLong(TestMetaDataModel.sapConnector.get(SapCaracUtil.SERIAL)));

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
        leafEnsSetupTag3.setId(3);
        leafEnsSetupTag3.setPosition(1);
        leafEnsSetupTag3.setTimeout(10000);
        leafEnsSetupTag3.setSelectedItem(EnsParametersUtil.RENEABLE_TEST_CHARACTERISTIC_LIST_TYPE.name());
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

        LeafEnsSetupTag leafEnsSetupTag5 = new LeafEnsSetupTag();
        leafEnsSetupTag5.setId(4);
        leafEnsSetupTag5.setPosition(1);
        leafEnsSetupTag5.setTimeout(10000);
        leafEnsSetupTag5.setSelectedItem(EnsParametersUtil.FUNCTIONAL_TEST_CHARACTERISTIC_LIST_TYPE.name());
        leafEnsSetupTag5.setStart(true);

        leafEnsSetupTag5.setTimeUnitFunctional(WdcTimeUnitType.MINUTES);

        tagList.getList().add(leafEnsSetupTag5);

        LeafEnsSetupTag leafEnsSetupTag7 = new LeafEnsSetupTag();
        leafEnsSetupTag7.setId(5);
        leafEnsSetupTag7.setPosition(1);
        leafEnsSetupTag7.setTimeout(10000);
        leafEnsSetupTag7.setSelectedItem(EnsParametersUtil.DIELECTRIC_TEST_CHARACTERISTIC_LIST_TYPE.name());
        leafEnsSetupTag7.setStart(true);

        leafEnsSetupTag7.setDurationTimeUnitDielectric(WdcTimeUnitType.MINUTES);
        leafEnsSetupTag7.setVoltageSetpointDielectric(190);
        leafEnsSetupTag7.setCurrentSetpointIn_mADielectric(200);
        leafEnsSetupTag7.setCurrentAcceptanceIn_mADielectric(210);
        leafEnsSetupTag7.setTestDurationDielectric(220);

        tagList.getList().add(leafEnsSetupTag7);

        LeafUserInputTag leafUserInputTag = new LeafUserInputTag();
        leafUserInputTag.setId(6);
        leafUserInputTag.setMessageToDisplay("My Input message");
        leafUserInputTag.setTimeout(10000);
        leafUserInputTag.setPosition(1);
        leafUserInputTag.setCommunicationNameRef("PLC");
        leafUserInputTag.setRegisterRef(8000);
        leafUserInputTag.setCalculateBy("Absolute");
        leafUserInputTag.setTolerancy(10);
        leafUserInputTag.setWaitAfter(1);
        leafUserInputTag.setWaitBefore(1);
        leafUserInputTag.getEnsTagConfiguration().setEnabled(true);
        leafUserInputTag.getEnsTagConfiguration().getEnsType().add(EnsParametersUtil.RENEABLE_TEST_CHARACTERISTIC_LIST_TYPE.name());
        //leafUserInputTag.getEnsTagConfiguration().getMaxValue().add("30");
        //leafUserInputTag.getEnsTagConfiguration().getMinValue().add("10");
        leafUserInputTag.getEnsTagConfiguration().getEnsVariableName().add(EnsParametersUtil.CURRENT_INV_U_RENEABLE.name());
        leafUserInputTag.getEnsTagConfiguration().getVariableToReadFrom().add("inputValue");
        tagList.getList().add(leafUserInputTag);
        LeafUserInputTag leafUserInputTag2 = new LeafUserInputTag();
        leafUserInputTag2.setId(7);
        leafUserInputTag2.setMessageToDisplay("My Input message");
        leafUserInputTag2.setTimeout(10000);
        leafUserInputTag2.setPosition(1);
        leafUserInputTag2.setCommunicationNameRef("PLC");
        leafUserInputTag2.setRegisterRef(8000);
        leafUserInputTag2.setCalculateBy("Absolute");
        leafUserInputTag2.setTolerancy(10);
        leafUserInputTag2.setWaitAfter(1);
        leafUserInputTag2.setWaitBefore(1);
        leafUserInputTag2.getEnsTagConfiguration().setEnabled(true);
        leafUserInputTag2.getEnsTagConfiguration().getEnsType().add(EnsParametersUtil.RENEABLE_TEST_CHARACTERISTIC_LIST_TYPE.name());
        //leafUserInputTag2.getEnsTagConfiguration().getMaxValue().add("40");
        //leafUserInputTag2.getEnsTagConfiguration().getMinValue().add("20");
        leafUserInputTag2.getEnsTagConfiguration().getEnsVariableName().add(EnsParametersUtil.CURRENT_INV_W_RENEABLE.name());
        leafUserInputTag2.getEnsTagConfiguration().getVariableToReadFrom().add("inputValue");
        tagList.getList().add(leafUserInputTag2);
        LeafUserInputTag leafUserInputTag3 = new LeafUserInputTag();
        leafUserInputTag3.setId(8);
        leafUserInputTag3.setMessageToDisplay("My Input message");
        leafUserInputTag3.setTimeout(10000);
        leafUserInputTag3.setPosition(1);
        leafUserInputTag3.setCommunicationNameRef("PLC");
        leafUserInputTag3.setRegisterRef(8000);
        leafUserInputTag3.setCalculateBy("Absolute");
        leafUserInputTag3.setTolerancy(10);
        leafUserInputTag3.setWaitAfter(1);
        leafUserInputTag3.setWaitBefore(1);
        leafUserInputTag3.getEnsTagConfiguration().setEnabled(true);
        leafUserInputTag3.getEnsTagConfiguration().getEnsType().add(EnsParametersUtil.RENEABLE_TEST_CHARACTERISTIC_LIST_TYPE.name());
        //leafUserInputTag3.getEnsTagConfiguration().getMaxValue().add("50");
        //leafUserInputTag3.getEnsTagConfiguration().getMinValue().add("30");
        leafUserInputTag3.getEnsTagConfiguration().getEnsVariableName().add(EnsParametersUtil.CURRENT_RET_U_RENEABLE.name());
        leafUserInputTag3.getEnsTagConfiguration().getVariableToReadFrom().add("inputValue");
        tagList.getList().add(leafUserInputTag3);
        LeafUserInputTag leafUserInputTag4 = new LeafUserInputTag();
        leafUserInputTag4.setId(9);
        leafUserInputTag4.setMessageToDisplay("My Input message");
        leafUserInputTag4.setTimeout(10000);
        leafUserInputTag4.setPosition(1);
        leafUserInputTag4.setCommunicationNameRef("PLC");
        leafUserInputTag4.setRegisterRef(8000);
        leafUserInputTag4.setCalculateBy("Absolute");
        leafUserInputTag4.setTolerancy(10);
        leafUserInputTag4.setWaitAfter(1);
        leafUserInputTag4.setWaitBefore(1);
        leafUserInputTag4.getEnsTagConfiguration().setEnabled(true);
        leafUserInputTag4.getEnsTagConfiguration().getEnsType().add(EnsParametersUtil.FUNCTIONAL_TEST_CHARACTERISTIC_LIST_TYPE.name());
        //leafUserInputTag4.getEnsTagConfiguration().getMaxValue().add("50");
        //leafUserInputTag4.getEnsTagConfiguration().getMinValue().add("30");
        leafUserInputTag4.getEnsTagConfiguration().getEnsVariableName().add(EnsParametersUtil.ANALOG_INPUTS_FUNCTIONAL.name());
        leafUserInputTag4.getEnsTagConfiguration().getVariableToReadFrom().add("inputValue");
        tagList.getList().add(leafUserInputTag4);
        LeafUserInputTag leafUserInputTag5 = new LeafUserInputTag();
        leafUserInputTag5.setId(10);
        leafUserInputTag5.setMessageToDisplay("My Input message");
        leafUserInputTag5.setTimeout(10000);
        leafUserInputTag5.setPosition(1);
        leafUserInputTag5.setCommunicationNameRef("PLC");
        leafUserInputTag5.setRegisterRef(8000);
        leafUserInputTag5.setCalculateBy("Absolute");
        leafUserInputTag5.setTolerancy(10);
        leafUserInputTag5.setWaitAfter(1);
        leafUserInputTag5.setWaitBefore(1);
        leafUserInputTag5.getEnsTagConfiguration().setEnabled(true);
        leafUserInputTag5.getEnsTagConfiguration().getEnsType().add(EnsParametersUtil.FUNCTIONAL_TEST_CHARACTERISTIC_LIST_TYPE.name());
        //leafUserInputTag5.getEnsTagConfiguration().getMaxValue().add("50");
        //leafUserInputTag5.getEnsTagConfiguration().getMinValue().add("30");
        leafUserInputTag5.getEnsTagConfiguration().getEnsVariableName().add(EnsParametersUtil.ANALOG_OUTPUTS_FUNCTIONAL.name());
        leafUserInputTag5.getEnsTagConfiguration().getVariableToReadFrom().add("inputValue");
        tagList.getList().add(leafUserInputTag5);
        LeafUserInputTag leafUserInputTag6 = new LeafUserInputTag();
        leafUserInputTag6.setId(11);
        leafUserInputTag6.setMessageToDisplay("My Input message");
        leafUserInputTag6.setTimeout(10000);
        leafUserInputTag6.setPosition(1);
        leafUserInputTag6.setCommunicationNameRef("PLC");
        leafUserInputTag6.setRegisterRef(8000);
        leafUserInputTag6.setCalculateBy("Absolute");
        leafUserInputTag6.setTolerancy(10);
        leafUserInputTag6.setWaitAfter(1);
        leafUserInputTag6.setWaitBefore(1);
        leafUserInputTag6.getEnsTagConfiguration().setEnabled(true);
        leafUserInputTag6.getEnsTagConfiguration().getEnsType().add(EnsParametersUtil.FUNCTIONAL_TEST_CHARACTERISTIC_LIST_TYPE.name());
        //leafUserInputTag6.getEnsTagConfiguration().getMaxValue().add("50");
        //leafUserInputTag6.getEnsTagConfiguration().getMinValue().add("30");
        leafUserInputTag6.getEnsTagConfiguration().getEnsVariableName().add(EnsParametersUtil.LINK_VOLTAGE_INDICATION_FUNCTIONAL.name());
        leafUserInputTag6.getEnsTagConfiguration().getVariableToReadFrom().add("inputValue");
        tagList.getList().add(leafUserInputTag6);

        LeafEnsSetupTag leafEnsSetupTag8 = new LeafEnsSetupTag();
        leafEnsSetupTag8.setId(12);
        leafEnsSetupTag8.setPosition(1);
        leafEnsSetupTag8.setTimeout(10000);
        leafEnsSetupTag8.setSelectedItem(EnsParametersUtil.DIELECTRIC_TEST_CHARACTERISTIC_LIST_TYPE.name());
        leafEnsSetupTag8.setStart(false);
        tagList.getList().add(leafEnsSetupTag8);

        LeafEnsSetupTag leafEnsSetupTag6 = new LeafEnsSetupTag();
        leafEnsSetupTag6.setId(13);
        leafEnsSetupTag6.setPosition(1);
        leafEnsSetupTag6.setTimeout(10000);
        leafEnsSetupTag6.setSelectedItem(EnsParametersUtil.FUNCTIONAL_TEST_CHARACTERISTIC_LIST_TYPE.name());
        leafEnsSetupTag6.setStart(false);
        tagList.getList().add(leafEnsSetupTag6);

        LeafEnsSetupTag leafEnsSetupTag4 = new LeafEnsSetupTag();
        leafEnsSetupTag4.setId(14);
        leafEnsSetupTag4.setPosition(1);
        leafEnsSetupTag4.setTimeout(10000);
        leafEnsSetupTag4.setStart(false);
        leafEnsSetupTag4.setSelectedItem(EnsParametersUtil.RENEABLE_TEST_CHARACTERISTIC_LIST_TYPE.name());
        tagList.getList().add(leafEnsSetupTag4);

        LeafEnsSetupTag leafEnsSetupTag2 = new LeafEnsSetupTag();
        leafEnsSetupTag2.setId(15);
        leafEnsSetupTag2.setPosition(1);
        leafEnsSetupTag2.setTimeout(10000);
        leafEnsSetupTag2.setStart(false);
        leafEnsSetupTag2.setSelectedItem(EnsParametersUtil.PRODUCT_DATA_ENS_TYPE.name());
        tagList.getList().add(leafEnsSetupTag2);

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("src/test/resources/test9.json"), tagList);

        JsonObjConverter converter = new JsonObjConverter();
        File file = new File("src/test/resources/test9.json");
        TagList tagList2 = converter.convertFromJsonToObj(file);
        Assert.assertEquals(tagList.getList().get(0).getId(), tagList2.getList().get(0).getId());
        Assert.assertEquals(tagList.getList().get(0).getTagName(), tagList2.getList().get(0).getTagName());
        Assert.assertEquals(tagList.getList().get(0).getTagIdentifier(), tagList2.getList().get(0).getTagIdentifier());
    }
}
