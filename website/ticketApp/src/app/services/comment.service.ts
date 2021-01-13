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

  createCinemaComment(token: string, comment: string, cinemaId: number): Observable<any> {
    const url = this.apiURL + '/user/comment/create?cinema=' + cinemaId + '&msg=' + comment;
    const httpOtions = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.post(url, {}, httpOtions);
  }

  createReplyComment(token: string, comment: string, filmId: string, parentCommentId: number, parentUserId: number): Observable<any> {
    const url = this.apiURL + '/user/comment/create?msg=' + comment + '&film=' + filmId + '&parent='
      + parentCommentId + '&reply=' + parentUserId;
    const headers = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.post(url, {}, headers);
  }

  createCinemaReplyComment(token: string, comment: string, cinemaId: number, parentId: number, parentUserId: number): Observable<any> {
    const url = this.apiURL + '/user/comment/create?msg=' + comment + '&cinema=' + cinemaId + '&parent='
      + parentId + '&reply=' + parentUserId;
    const headers = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.post(url, {}, headers);

  }

  getCommentByFilm(filmId: string, page: number): Observable<any> {
    const url = this.apiURL + '/common/film/' + filmId + '/commentPage' + page;
    return this.http.get(url);
  }

  getCommentByCinema(cinemaId: string, page: number): Observable<any> {
    const url = this.apiURL + '/common/cinema/' + cinemaId + '/commentPage' + page;
    return this.http.get(url);
  }

  getReplies(parentId: number, page: number): Observable<any> {
    const url = this.apiURL + '/common/comment/' + parentId + '/second/level?page=' + page;
    return this.http.get(url);
  }

  likeComment(commentId: string, token: string): Observable<any> {
    const url = this.apiURL + '/user/comment/' + commentId + '/like';
    const headers = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.put(url, {}, headers);
  }

  deleteComment(commentId: string, token: string): Observable<any> {
    const url = this.apiURL + '/user/comment/' + commentId + '/remove/';
    const headers = {headers: new HttpHeaders({'Content-Type': 'application/json', Authentication: token})};
    return this.http.delete(url, headers);
  }


}
