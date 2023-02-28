package com.example.demo.models;

public class BaseTag {
    public int id;
    public int line;
    public String nome;
    public String simpleDescription;
    public String description;
    public int position;
    public boolean enabled;

    public BaseTag(int id, int line, String nome, String simpleDescription, String description, int position, boolean enabled) {
        this.id = id;
        this.line = line;
        this.nome = nome;
        this.simpleDescription = simpleDescription;
        this.description = description;
        this.position = position;
        this.enabled = enabled;
    }

}
