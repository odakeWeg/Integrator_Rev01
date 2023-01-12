package weg.net.tester.models.web;

import lombok.Getter;
import lombok.Setter;
import weg.net.tester.utils.ActionCommandUtil;

@Getter
@Setter
public class ProductLog {
    private String serial;
    private String material;
    private String produto;
    private String descricao;
    private String action;
    private int position;

    public ProductLog(String serial, String material, String produto, String descricao, int position) {
        this.serial = serial;
        this.material = material;
        this.produto = produto;
        this.descricao = descricao;
        this.action = ActionCommandUtil.STARTING_INFO;
        this.position = position;
    }

    public ProductLog() {
        this.action = ActionCommandUtil.STARTING_INFO;
    }
}
