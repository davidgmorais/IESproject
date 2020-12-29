import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Film} from './models/Film';

const httpOptions = {
  header: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class TicketApiService {

  private apiURL = 'localhost:8080/';

  constructor(private httpClient: HttpClient) { }

  getMovie(id: string): Observable<any> {
    const url = '/common/film/id/' + id;
    return this.httpClient.get(url);
  }

  public getRecent(): Observable<any> {
      return this.httpClient.get('/common/film/recent');
  }

  public getPopular(): Observable<any> {
      return this.httpClient.get('/common/film/popular');
  }

}
