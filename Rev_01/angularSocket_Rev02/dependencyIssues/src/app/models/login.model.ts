export class LoginModel {
    cadastro!: string
    password!: string

    toString() {
        return "{" + '"cadastro": ' + '"' + this.cadastro + '"' +
                ", " + '"password": ' + '"' + this.password + '"' + "}";
    }
}