import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

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
}
