import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/shared';

const BASE_URL: string = 'http://localhost:8082/config/user/';

@Injectable({
  providedIn: 'root'
})
export class CadastroService {

  constructor(private http: HttpClient) { }

  public getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(BASE_URL)
  }

  public updateUser(user: User): Observable<User> {
    console.log(user)
    return this.http.put(`${BASE_URL} + ${user.line}`, user)
  }

  public addUser(user: User): Observable<User> {
    console.log(user)
    return this.http.post(`${BASE_URL}`, user)
  }

  public remove(user: User): Observable<User> {
    return this.http.delete(`${BASE_URL} + ${user.line}`)
  }
}
