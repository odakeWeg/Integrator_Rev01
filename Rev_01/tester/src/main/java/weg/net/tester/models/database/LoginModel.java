package weg.net.tester.models.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Document
@Getter
@NoArgsConstructor
public class LoginModel {
    @Id
    private int cadastro;
    private String password;

    public LoginModel(int cadastro, String password) {
        this.cadastro = cadastro;
        this.password = password;
    }

    @Override
    public String toString() {
        return "{" +
            " cadastro='" + getCadastro() + "'" +
            ", password='" + getPassword() + "'" +
            "}";
    }

}
