package weg.net.tester.models.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Document
@Getter
@Setter
public class SessionModel {
    @Id
    private String timestamp;
    private int cadastro;
    private long sessionTime;
    private long executingTestTime;
    private long totalTestExecuted;
    private long totalTestApproved;
    private long totalTestFailed;
    private long totalTestCanceled;


    public SessionModel(int cadastro, String timestamp, long sessionTime, long executingTestTime, long totalTestExecuted, long totalTestApproved, long totalTestFailed, long totalTestCanceled) {
        this.cadastro = cadastro;
        this.timestamp = timestamp;
        this.sessionTime = sessionTime;
        this.executingTestTime = executingTestTime;
        this.totalTestExecuted = totalTestExecuted;
        this.totalTestApproved = totalTestApproved;
        this.totalTestFailed = totalTestFailed;
        this.totalTestCanceled = totalTestCanceled;
    }
}
