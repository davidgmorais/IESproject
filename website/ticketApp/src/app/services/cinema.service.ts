import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Session} from '../models/Session';
import {Premier} from '../models/Premier';
import {Room} from '../models/Room';

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

  public createSchedule(token: string, session: Session): Observable<any> {
    const  url = this.apiURL + '/cinema/create/schedule';
    const headers = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.post(url, session, headers);
  }

  public createPremiere(token: string, premiere: Premier): Observable<any> {
    const  url = this.apiURL + '/cinema/create/premier';
    const headers = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.post(url, premiere, headers);
  }

  public getRooms(token: string): Observable<any> {
    const url = this.apiURL + '/cinema/rooms';
    const headers = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.get(url, headers);
  }

  public createRoom(token: string, room: Room): Observable<any> {
    const url = this.apiURL + '/cinema/create/room';
    const headers = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.post(url, room, headers);
  }
}
