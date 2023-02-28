package weg.net.tester.models.web;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PopUpLog {
    private String message;
    private String action;
    private int timeout;

    public PopUpLog(String message, String action, int timeout) {
        this.message = message;
        this.action = action;
        this.timeout = timeout;
    }

    public PopUpLog() {

    }
}
