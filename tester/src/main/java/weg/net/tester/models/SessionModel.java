package weg.net.tester.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

//TODO: Verificar o que adicionar aqui
@Document
@Getter
public class SessionModel {
    @Id
    private long id;
    private String timestamp;
    private String cadastro;
    private long sessionTime;
    private int executingTestTime;
    private int totalTestExecuted;
    private int totalTestApproved;
    private int totalTestFailed;
    private int totalTestCanceled;


    public SessionModel(long id, String cadastro, String timestamp, long sessionTime, int executingTestTime, int totalTestExecuted, int totalTestApproved, int totalTestFailed, int totalTestCanceled) {
        this.id = id;
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
