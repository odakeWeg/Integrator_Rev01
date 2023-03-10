export class LoginModel {
    cadastro!: number
    password!: string

    toString() {
        return "{" + '"cadastro": ' + '"' + this.cadastro + '"' +
                ", " + '"password": ' + '"' + this.password + '"' + "}";
    }
}