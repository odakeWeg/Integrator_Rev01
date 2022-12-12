package weg.net.tester.tag;

import java.util.ArrayList;
import java.util.List;

public class TagList /*implements BaseTagList*/ {

    private List<BaseTag> list = new ArrayList<>();

    public List<BaseTag> getList() {
        return this.list;
    }

    public void setList(List<BaseTag> list) {
        this.list = list;
    }

    public int qntOfProductInTest() {
        int qnt = 0;
        for (int i = 0; i < this.list.size(); i++) {
            if (this.list.get(i).getPosition() > qnt) {
                qnt = this.list.get(i).getPosition();
            }
        }
        return qnt;
    }
}
