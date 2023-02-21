package weg.net.tester.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

@Document
@Getter
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
