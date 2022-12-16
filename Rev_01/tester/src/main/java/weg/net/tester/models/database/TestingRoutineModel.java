package weg.net.tester.models.database;

import org.json.simple.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

@Document
@Getter
public class TestingRoutineModel {
    @Id
    private String fileName;
    private JSONObject routineJson;
}