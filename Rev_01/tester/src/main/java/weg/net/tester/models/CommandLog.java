package weg.net.tester.models;

import lombok.Getter;

@Getter
public class CommandLog {
    private String testResult;
    private String errorMessage;
    private String descricao;
    private String log;
    private String action;
    private boolean finished;

    public CommandLog(String testResult, String errorMessage, String descricao, String log, String action, boolean finished) {
        this.testResult = testResult;
        this.errorMessage = errorMessage;
        this.descricao = descricao;
        this.log = log;
        this.action = action;
        this.finished = finished;
    }

    public CommandLog(String testResult, String action, boolean finished) {
        this.testResult = testResult;
        this.action = action;
        this.finished = finished;
    }

    public CommandLog() {
    }
}
