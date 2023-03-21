import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const BASE_URL: string = 'http://localhost:8080/fileList/';

@Injectable({
  providedIn: 'root'
})
export class RoutineFilesService {

  constructor(private http: HttpClient) { }

  public getAllfiles(): Observable<string[]> {
    return this.http.get<string[]>(BASE_URL)
  }
}
