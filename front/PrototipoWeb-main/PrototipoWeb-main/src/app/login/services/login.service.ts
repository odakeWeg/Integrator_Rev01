import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/shared';

const LS_CHAVE: string = "userSession";
const BASE_URL: string = 'http://localhost:8080/config/user'; //@TODO: global

@Injectable({
  providedIn: 'root'
})
export class LoginService { //@Todo: apply this  same localStorage consept in the routine executor software

  constructor(private http: HttpClient) { }

  public isCredentialsValid(userRef: User, user: User): boolean {

    if(userRef.senha==user.senha && userRef.cadastro==user.cadastro) {
      this.startUserSession(userRef)
      return true
    }
    return false
  }

  private startUserSession(user: User): void {
    localStorage[LS_CHAVE] = JSON.stringify(user)
  }

  /*
  public getUserByCadastro(cadastro: number): void {
    let userBuffer

    this.http.get<User[]>(`${BASE_URL}/${cadastro}`).subscribe(data => {userBuffer = data; this.userToValidade = userBuffer[0];});
  }
  */

  public getUserByCadastro(cadastro: number): Observable<User[]> {
    return this.http.get<User[]>(`${BASE_URL}/${cadastro}`)
  }
}
