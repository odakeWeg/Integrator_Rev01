package com.example.demo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class MappingModel {
    @Id
    private int id;
    private int line;
    private String nome;
    private String mapping;
    private boolean enabled;

    public MappingModel(int id, int line, String nome, String mapping, boolean enabled) {
        this.id = id;
        this.line = line;
        this.nome = nome;
        this.mapping = mapping;
        this.enabled = enabled;
    }
}
