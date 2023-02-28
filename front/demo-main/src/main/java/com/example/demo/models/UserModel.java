package com.example.demo.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document
@Getter
@Setter
public class UserModel {
    @Id
    private int id;
    private int line;
    private int cadastro;
    private String nome;
    private String senha;
    private String perfil;

    public UserModel(int id, int line, int cadastro, String nome, String senha, String perfil) {
        this.id = id;
        this.line = line;
        this.cadastro = cadastro;
        this.nome = nome;
        this.senha = senha;
        this.perfil = perfil;
    }
}   
