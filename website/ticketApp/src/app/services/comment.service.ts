import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {UserComment} from '../models/UserComment';
import {Observable} from 'rxjs';

const httpOptions = {
  header: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private apiURL = 'http://localhost:8080';

  constructor(private http: HttpClient) { }

  public createComment(token: string, comment: string, filmId: string): Observable<any> {
    const url = this.apiURL + '/user/comment/create';
    const httpOtions = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    const body = {
      msg: comment,
      film: filmId,
    };
    return this.http.post(url, body, httpOtions);
  }

}
