import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

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
    const url = this.apiURL + '/common/film/id/' + id;
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
    return this.httpClient.get(this.apiURL + 'common/film/genre/' + genre);
  }

  public getYear(year: string): Observable<any> {
    return this.httpClient.get(this.apiURL + 'common/film/year/' + year);
  }


}
