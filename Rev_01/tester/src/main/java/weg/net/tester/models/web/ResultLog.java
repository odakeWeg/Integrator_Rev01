package weg.net.tester.models.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import weg.net.tester.utils.ActionCommandUtil;

@Getter
@Setter
@NoArgsConstructor
public class ResultLog {
    private String result;
    private String log;
    private boolean finished;
    private String status;
    private String action;
    private int position;

    public ResultLog(String result, String log, boolean finished, String status, int position) {
        this.result = result;
        this.log = log;
        this.finished = finished;
        this.status = status;
        this.action = ActionCommandUtil.SHOW_FINAL_RESULT;
        this.position = position;
    }
}