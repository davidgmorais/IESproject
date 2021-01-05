import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Film} from '../models/Film';

const httpOptions = {
  header: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class TicketApiService {

  private apiURL = 'http://localhost:8080';

  constructor(private httpClient: HttpClient) { }

  public getMovie(id: string): Observable<any> {
    const url = this.apiURL + '/common/film/' + id;
    return this.httpClient.get(url);
  }

  public getMovies(): Observable<any> {
    return this.httpClient.get(this.apiURL + '/common/film/recent');
  }

  public getRecent(): Observable<any> {
      return this.httpClient.get(this.apiURL + '/common/film/recent');
  }

  public getPopular(): Observable<any> {
      return this.httpClient.get(this.apiURL + '/common/film/popular');
  }

  public getGenre(genre: string): Observable<any> {
    const url = this.apiURL + '/common/film/genre/' + genre;
    return this.httpClient.get(url);
  }

  public getYear(year: string): Observable<any> {
    const url = this.apiURL + '/common/film/year/' + year;
    return this.httpClient.get(url);
  }

  public search(query: string): Observable<any> {
    const url = this.apiURL + '/common/film/title/' + query;
    return  this.httpClient.get(url);
  }


}
