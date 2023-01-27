package weg.net.tester.models.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;

@Document
@Getter
public class LoginModel {
    @Id
    private String cadastro;
    private String password;


    @Override
    public String toString() {
        return "{" +
            " cadastro='" + getCadastro() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }

}
