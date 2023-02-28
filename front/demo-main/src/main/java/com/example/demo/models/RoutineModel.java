package com.example.demo.models;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class RoutineModel {
    @Id
    private int id;
    private int line;
    private String nome;
    private String description;
    private List<Object> tag; //@Todo: change for tagBase or so
    private boolean enabled;

    public RoutineModel(int id, int line, String nome, String description, List<Object> tag, boolean enabled) {
        this.id = id;
        this.line = line;
        this.nome = nome;
        this.description = description;
        this.tag = tag;
        this.enabled = enabled;
    }    
}