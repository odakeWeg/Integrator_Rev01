package weg.net.tester.models.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Document
@Getter
public class UserModel {
    @Id
    private int id;
    private int line;
    private String nome;
    private int cadastro;
    private String password;
    private String perfil;

    public UserModel(int id, int line, String nome, int cadastro, String password, String perfil) {
        this.id = id;
        this.line = line;
        this.nome = nome;
        this.cadastro = cadastro;
        this.password = password;
        this.perfil = perfil;
    }
   
}

/* 
public class UserModel {
    @Id
    private String cadastro;
    private String password;
    private String perfil;

    public UserModel(String cadastro, String password, String perfil) {
        this.cadastro = cadastro;
        this.password = password;
        this.perfil = perfil;
    }

    public String getCadastro() {
        return this.cadastro;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPerfil() {
        return this.perfil;
    }
}
*/