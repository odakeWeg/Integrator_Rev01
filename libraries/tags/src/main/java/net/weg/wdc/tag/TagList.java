package net.weg.wdc.tag;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "root")
@XmlAccessorType (XmlAccessType.FIELD)
public class TagList implements BaseTagList {

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

    List<BaseTag> list = new ArrayList<>();

    @Override
    public List<BaseTag> getList() {
        concatenateTagList();
        sortById();
        return this.list;
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
