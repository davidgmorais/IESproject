import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';

const httpOptions = {
  header: new HttpHeaders({'Content-Type': 'application/json'})
};


@Injectable({
  providedIn: 'root'
})
export class CinemaService {

  private apiURL = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  public getCinema(cinemaId: string): Observable<any> {
    const url = this.apiURL + '/common/cinema/' + cinemaId;
    return this.http.get(url);
  }
}
