package weg.net.tester.models.web;

public class PopUpLog {
    private String message;
    private String action;
    private int timeout;

    public PopUpLog(String message, String action, int timeout) {
        this.message = message;
        this.action = action;
        this.timeout = timeout;
    }
}
