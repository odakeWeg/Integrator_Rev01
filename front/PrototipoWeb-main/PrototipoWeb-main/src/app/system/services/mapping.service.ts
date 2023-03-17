import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Mapping } from 'src/app/shared';

const BASE_URL: string = 'http://localhost:8082/system/';

//Having problem using this service because of pushing and pulling data from database
//Solutions: Using a query to catch data or treat it better in this class

@Injectable({
  providedIn: 'root'
})
export class MappingService {
  constructor(private http: HttpClient) { }

  public getAllMappings(): Observable<Mapping[]> {
    return this.http.get<Mapping[]>(BASE_URL)
  }

  public updateMapping(mapping: Mapping): Observable<Mapping> {
    console.log(mapping)
    return this.http.put(`${BASE_URL} + ${mapping.id}`, mapping)
  }

  public addMapping(mapping: Mapping): Observable<Mapping> {
    return this.http.post(`${BASE_URL}`, mapping)
  }

  public remove(mapping: Mapping): Observable<Mapping> {
    return this.http.delete(`${BASE_URL} + ${mapping.id}`)
  }
}
