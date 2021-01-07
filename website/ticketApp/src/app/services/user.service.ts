import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../models/User';
import {CinemaUser} from '../models/CinemaUser';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
  observe: 'response' as 'response',
};

@Injectable({
  providedIn: 'root'
})

export class UserService {

  private apiURL = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  public login(email: string, userPassword: string): Observable<any> {
    const url = this.apiURL + '/login';
    return this.http.post(url, {userEmail: email, password: userPassword}, httpOptions);
  }

  public register(user: User): Observable<any> {
    const url = this.apiURL + '/common/register';
    return this.http.post(url, user, httpOptions);
  }

  public registerCinema(cinema: CinemaUser): Observable<any> {
    const url = this.apiURL + '/common/register/cinema';
    return this.http.post(url, cinema, httpOptions);
  }

  public confirm(token: string, code: string): Observable<any> {
    const url = this.apiURL + '/common/confirm/' + code;
    const httpOtions = {headers: new HttpHeaders({'Content-Type': 'application/json', registerToken: token})};
    return this.http.post(url, {}, httpOtions);
  }

  public confirmCinema(token: string, code: string): Observable<any> {
    const url = this.apiURL + '/common/confirm/cinema/' + code;
    const httpOtions = {headers: new HttpHeaders({'Content-Type': 'application/json', registerToken: token})};
    return this.http.post(url, {}, httpOtions);
  }

  public getNotifications(token: string, page: number = 1): Observable<any> {
    const url = this.apiURL + '/user/notifications?page=' + page;
    const headers = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.get(url, headers);
  }

  public changePassword(token: string, password: string): Observable<any> {
    const url = this.apiURL + '/user/changepwd';
    const headers = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.put(url, {password}, headers);
  }

  public likeMovie(token: string, filmId: string): Observable<any> {
    const url = this.apiURL + '/user/add/favourite/film/' + filmId;
    const headers = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.post(url, {}, headers);
  }

}
