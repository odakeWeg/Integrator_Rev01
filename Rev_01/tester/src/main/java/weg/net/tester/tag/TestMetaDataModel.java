package weg.net.tester.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.messaging.simp.SimpMessagingTemplate;

import net.weg.wdc.ens.ProductDataEns;
import weg.net.tester.utils.TagIdentifierUtil;

public class TestMetaDataModel {
    public static TagList tagList;
    public static String testName;
    public static AtomicBoolean[] isPositionEnabled;
    public static boolean exitFlag;
    public static SimpMessagingTemplate template;
    public static List<HashMap<String, String>> sapConnector;

    public static List<ProductDataEns> productDataEnsList;
    public static int initialEnsId; 

    public static NodeCommunicationTag mainCommunication;

    public static int[] testStep;

    public TestMetaDataModel(int qnt) {
        this.exitFlag = false;
        this.isPositionEnabled = initiatePosition(qnt);
        this.testStep = new int[qnt];
        this.mainCommunication = null;
        initiateProductList(qnt);
    }

    public void initiateProductList(int qnt) {
        productDataEnsList = new ArrayList<>();
        for (int i = 0; i < qnt; i++) {
            productDataEnsList.add(new ProductDataEns());
        }
    }

    public AtomicBoolean[] initiatePosition(int qnt) {
        AtomicBoolean[] array = new AtomicBoolean[qnt];
        for (int i = 0; i < array.length; i++) {
            array[i] = new AtomicBoolean(true);
        }
        return array;
    }

    public static boolean isMainCommunicationSetted() {
        if (mainCommunication==null) {
            NodeCommunicationTag communicationTag;
            for (int i = 0; i < TestMetaDataModel.tagList.getList().size(); i++) {
                if (TestMetaDataModel.tagList.getList().get(i).getTagIdentifier().equals(TagIdentifierUtil.COMMUNICATION)) {
                    communicationTag = (NodeCommunicationTag) tagList.getList().get(i);
                    if(communicationTag.isEnableCommunication()) {
                        mainCommunication = communicationTag;
                        return true;
                    }
                }
            }
            return false;
        } else {
            return true;
        }
    }
}
