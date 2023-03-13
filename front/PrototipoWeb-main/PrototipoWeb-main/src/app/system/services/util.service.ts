import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const BASE_URL: string = 'http://localhost:8080/util/';

@Injectable({
  providedIn: 'root'
})
export class UtilService {

  constructor(private http: HttpClient) { }

  public getAllSAP(): Observable<string[]> {
    let sapPath = "sapFields"
    return this.http.get<string[]>(BASE_URL+sapPath)
  }
}
