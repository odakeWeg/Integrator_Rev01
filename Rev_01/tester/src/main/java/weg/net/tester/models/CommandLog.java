package weg.net.tester.models;

import lombok.Getter;

@Getter
public class CommandLog {
    public String testResult;
    public String errorMessage;
    public String descricao;
    public String log;
    public String action;

    public CommandLog(String testResult, String errorMessage, String descricao, String log, String action) {
        this.testResult = testResult;
        this.errorMessage = errorMessage;
        this.descricao = descricao;
        this.log = log;
        this.action = action;
    }


    public CommandLog() {
    }
}
