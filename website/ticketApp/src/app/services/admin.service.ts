import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

const httpOptions = {
  header: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiURL = 'http://localhost:8080/admin/';

  constructor(private http: HttpClient) { }

  public getAllRequest(token: string, page: number = 1): Observable<any> {
    const url = this.apiURL + 'requests?page=' + page;
    const header = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.get(url, header);
  }

  public getRequest(token: string, requestId: number): Observable<any> {
    const url = this.apiURL + 'requests/' + requestId;
    const header = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.get(url, header);
  }

  public acceptRequest(token: string, requestId: number): Observable<any> {
    console.log(requestId);
    const url = this.apiURL + 'requests/accepted/' + requestId;
    const header = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.put(url, {}, header);
  }

  public refuseRequest(token: string, requestId: number): Observable<any> {
    const url = this.apiURL + 'requests/refuse/' + requestId;
    const header = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.put(url, {}, header);
  }
}
