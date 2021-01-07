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
    const url = this.apiURL + '/user/comment/create?msg=' + comment + '&film=' + filmId;
    const httpOtions = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.post(url, {}, httpOtions);
  }

  getCommentByFilm(filmId: string, page: number): Observable<any> {
    const url = this.apiURL + '/common/film/' + filmId + '/commentPage' + (page - 1);
    return this.http.get(url);
  }

  likeComment(commentId: string, token: string): Observable<any> {
    const url = this.apiURL + '/user/comment/' + commentId + '/like';
    const headers = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.put(url, {}, headers);
  }

  deleteComment(commentId: string, token: string): Observable<any> {
    const url = this.apiURL + '/user/comment/' + commentId + '/remove';
    const headers = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.delete(url, headers);
  }
}
