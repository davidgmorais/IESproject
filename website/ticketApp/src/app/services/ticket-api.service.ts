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

  public getMovies(page: number): Observable<any> {
    return this.httpClient.get(this.apiURL + '/common/film/recent?page=' + page);
  }

  public getRecent(page: number): Observable<any> {
      return this.httpClient.get(this.apiURL + '/common/film/recent?page=' + page);
  }

  public getPopular(page: number): Observable<any> {
      return this.httpClient.get(this.apiURL + '/common/film/popular?page=' + page);
  }

  public getGenre(genre: string, page: number): Observable<any> {
    const url = this.apiURL + '/common/film/genre/' + genre + '?page=' + page;
    return this.httpClient.get(url);
  }

  public getYear(year: string, page: number): Observable<any> {
    const url = this.apiURL + '/common/film/year/' + year + '?page=' + page;
    return this.httpClient.get(url);
  }

  public getActor(actorName: string, page: number): Observable<any> {
    const url = this.apiURL + '/common/film/actor/' + actorName.split(' ').join('%20') + '?page=' + page;
    console.log(url)
    return this.httpClient.get(url);
  }

  public getDirector(directorName: string, page: number): Observable<any> {
    const url = this.apiURL + '/common/film/director/' + directorName.split(' ').join('%20') + '?page=' + page;
    console.log(url);
    return this.httpClient.get(url);
  }

  public search(query: string, page: number): Observable<any> {
    const url = this.apiURL + '/common/film/title/' + query + '?page=' + page;
    return this.httpClient.get(url);
  }

  public getGenres(): Observable<any> {
    const url = this.apiURL + '/common/genres';
    return this.httpClient.get(url);
  }

  public getPremiers(movieId: string): Observable<any> {
    const url = this.apiURL + '/common/film/' + movieId;
    return this.httpClient.get(url);
  }


}
