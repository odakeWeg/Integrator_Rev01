package net.weg.wdc.model;

public class CommandLog {
    private String testResult;
    private String errorMessage;
    private String descricao;
    private String log;
    private String action;

    public CommandLog(String testResult, String errorMessage, String descricao, String log, String action) {
        this.testResult = testResult;
        this.errorMessage = errorMessage;
        this.descricao = descricao;
        this.log = log;
        this.action = action;
    }
}
