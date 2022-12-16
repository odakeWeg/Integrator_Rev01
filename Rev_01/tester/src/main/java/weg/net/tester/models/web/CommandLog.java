package weg.net.tester.models.web;

import lombok.Getter;

@Getter
public class CommandLog {
    private String testResult;
    private String errorMessage;
    private String descricao;
    private String log;
    private String action;
    private String testName;
    private boolean finished;
    private int position;

    //@Todo: implemend send command here instead of in the TestExecutor script
    public CommandLog(String testResult, String errorMessage, String descricao, String log, String action, String testName, boolean finished, int position) {
        this.testResult = testResult;
        this.errorMessage = errorMessage;
        this.descricao = descricao;
        this.log = log;
        this.action = action;
        this.testName = testName;
        this.finished = finished;
        this.position = position;
    }

    public CommandLog(String testResult, String action, boolean finished, int position) {
        this.testResult = testResult;
        this.action = action;
        this.finished = finished;
        this.position = position;
    }

    public CommandLog(String descricao, String action, int position) {
        this.descricao = descricao;
        this.action = action;
        this.position = position;
    }

    public CommandLog() {
    }
}
