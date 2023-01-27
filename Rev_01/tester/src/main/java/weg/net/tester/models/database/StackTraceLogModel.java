package weg.net.tester.models.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

@Document
@Getter
public class StackTraceLogModel {
    @Id
    private String timeStamp;
    private String cadastro;
    private String errorMessage;

    public StackTraceLogModel(String errorMessage, String timeStamp, String cadastro) {
        this.errorMessage = errorMessage;
        this.timeStamp = timeStamp;
        this.cadastro = cadastro;
    }
}
