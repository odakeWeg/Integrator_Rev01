package weg.net.tester.tag;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import weg.net.tester.utils.FilePathUtil;

@XmlRootElement(name = "root")
@XmlAccessorType (XmlAccessType.FIELD)
public class TagList /*implements BaseTagList*/ {

    @XmlElement(name = "modbusCommunication")
    private List<LeafModbusCommunicationTag> communicationTagList = new ArrayList<>();
    @XmlElement(name = "IOLinkCommunication")
    private List<LeafIOLinkCommunicationTag> communicationIOLinkTagList = new ArrayList<>();
    @XmlElement(name = "ethernetCommunication")
    private List<LeafEthernetCommunicationTag> communicationEthernetTagList = new ArrayList<>();

    @XmlElement(name = "write")
    private List<LeafWriteTag> writeTagList = new ArrayList<>();

    @XmlElement(name = "registerCompare")
    private List<LeafRegisterCompareTag> registerCompareTagList = new ArrayList<>();
    @XmlElement(name = "userConfirmation")
    private List<LeafUserConfirmationTag> userConfirmationTagList = new ArrayList<>();
    @XmlElement(name = "userInput")
    private List<LeafUserInputTag> userInputTagList = new ArrayList<>();

    @XmlElement(name = "test")
    private List<LeafTestTag> testTagList = new ArrayList<>();
    @XmlElement(name = "forTest")
    private List<LeafForTestTag> forTestTagList = new ArrayList<>();

    List<BaseTag> list = new ArrayList<>();

    //@Override
    public List<BaseTag> getList() {
        concatenateTagList();
        sortById();
        return this.list;
    }

    public TagList() {
    }

    public TagList(List<BaseTag> tags) throws ClassNotFoundException {
        HashMap<String, List> hm = createHashMapFromLists();
        for (int i = 0; i < tags.size(); i++) {
            String className = tags.get(i).getTagName().substring(0, 1).toUpperCase() + tags.get(i).getTagName().substring(1) + "Tag";
            Class<?> tag = Class.forName(FilePathUtil.TAG_FILES_PATH + "Leaf" + className);
            hm.get(className).add(tag.cast(tags.get(i)));
        }
    }

    private HashMap<String, List> createHashMapFromLists() {
        HashMap<String, List> hm = new HashMap<>();
        hm.put("TestTag", testTagList);
        hm.put("ForTestTag", forTestTagList);
        hm.put("ModbusCommunicationTag", communicationTagList);
        hm.put("IOLinkCommunicationTag", communicationIOLinkTagList);
        hm.put("EthernetCommunicationTag", communicationEthernetTagList);
        hm.put("WriteTag", writeTagList);
        hm.put("RegisterCompareTag", registerCompareTagList);
        hm.put("UserConfirmationTag", userConfirmationTagList);
        hm.put("UserInputTag", userInputTagList);

        return hm;
    }

    private void concatenateTagList() {
        list.addAll(communicationTagList);
        list.addAll(communicationIOLinkTagList);
        list.addAll(communicationEthernetTagList);

        list.addAll(writeTagList);

        list.addAll(registerCompareTagList);
        list.addAll(userConfirmationTagList);
        list.addAll(userInputTagList);

        list.addAll(testTagList);
        list.addAll(forTestTagList);
    }

    private void sortById() {
        List<BaseTag> listSorted = new ArrayList<>();
        int counter = 0;
        while(list.size() > listSorted.size()) {
            for (int i = 0; i< list.size(); i++) {
                if(list.get(i).getId() == listSorted.size()) {
                    listSorted.add(list.get(i));
                }
            }
            if (counter > list.size()+1) {
                return; //throw error or something
            }
            counter += 1;
        }
        this.list = listSorted;
    }
}
