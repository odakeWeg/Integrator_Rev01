package weg.net.tester.models;

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

    public CommandLog(String testResult, String errorMessage, String descricao, String log, String action, String testName, boolean finished) {
        this.testResult = testResult;
        this.errorMessage = errorMessage;
        this.descricao = descricao;
        this.log = log;
        this.action = action;
        this.testName = testName;
        this.finished = finished;
    }

    public CommandLog(String testResult, String action, boolean finished) {
        this.testResult = testResult;
        this.action = action;
        this.finished = finished;
    }

    public CommandLog(String descricao, String action) {
        this.descricao = descricao;
        this.action = action;
    }

    public CommandLog() {
    }
}
