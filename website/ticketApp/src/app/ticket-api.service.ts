import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TicketApiService {

  private apiURL = 'localhost:8080/';

  constructor(private httpClient: HttpClient) { }

  public getMovieDetails(id: string): Observable<any> {
      return this.httpClient.get(this.apiURL + 'common/movie?id=' + id );
  }

  public getRecent(): Observable<any> {
      return this.httpClient.get(this.apiURL + 'common/movie/recent');
  }

  public getPopular(): Observable<any> {
      return this.httpClient.get(this.apiURL + 'common/movie/popular');
  }

}
