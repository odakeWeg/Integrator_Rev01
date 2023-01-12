package weg.net.tester.models.web;

public class SessionLog {
    private String response;
    private String action;


    public SessionLog(String response, String action) {
        this.response = response;
        this.action = action;
    }
}