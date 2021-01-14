import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import * as Chart from 'chart.js';
import {TicketApiService} from '../../services/ticket-api.service';
import {Film} from '../../models/Film';

import {ActivatedRoute, Router} from '@angular/router';
import {Location} from '@angular/common';
import {catchError} from 'rxjs/operators';
import {throwError} from 'rxjs';
import {Actor} from '../../models/Actor';
import {UserComment} from '../../models/UserComment';
import {CommentService} from '../../services/comment.service';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../services/user.service';
import {User} from '../../models/User';
import {Premier} from '../../models/Premier';
import {CinemaService} from '../../services/cinema.service';

@Component({
  selector: 'app-moviepage',
  templateUrl: './moviepage.component.html',
  styleUrls: ['./moviepage.component.css']
})
export class MoviepageComponent implements OnInit {
  @Input()
  film: Film;
  errorMsg: string;
  cast: Actor[];
  activeSubComment: number;
  token: string;
  commentGroup: FormGroup;
  comments: UserComment[];
  userEmail: string;
  totalPages: number;
  totalComments: number;
  currentPage = 1;
  replies: {[key: number]: UserComment[]} = {};
  isFavorite: boolean;
  premiers: Premier[];

  constructor(private ticketApiService: TicketApiService,
              private route: ActivatedRoute,
              private commentService: CommentService,
              private location: Location,
              private userService: UserService,
              private cinemaService: CinemaService,
              private fb: FormBuilder,
              private router: Router) {}

  ngOnInit(): void {
    this.commentGroup = this.fb.group({
      newComment: new FormControl(''),
      replyComment: new FormControl('')
    });

    this.token = localStorage.getItem('auth_token');
    if (this.token === 'null') {this.token = null; }
    this.userEmail = localStorage.getItem('user_email');
    this.getMovie();
    this.getComments(1);
  }

  getMovie(): void {
    const id: string = this.route.snapshot.paramMap.get('id');
    this.ticketApiService.getMovie(String(id)).pipe(
      catchError(error => {
        if (error.error instanceof ErrorEvent) {
          this.errorMsg = `Error: ${error.error.message}`;
        } else {
          this.location.back();
          this.errorMsg = `Error: ${error.message}`;
        }
        return throwError(this.errorMsg);
      })
    ).subscribe(
      response => {
        console.log(response);
        if (response.status === 200) {
          this.film = (response.data as Film);
          this.cast = this.film.actors.slice(0, 10);
          this.renderPie('pieChart', this.film.rating);
          // this.checkIfIsFavorite();
          this.premiers = response.data.premiers.data as Premier[];
          this.getSchedulte();
          console.log(this.premiers);
        } else {
          this.location.back();
        }
      }
    );
  }

  getComments(page: number): void {
    const id: string = this.route.snapshot.paramMap.get('id');
    this.commentService.getCommentByFilm(id, page ).subscribe(response => {
      if (response.status === 200) {
        response = response.data;
        this.totalComments = response.totalElements;
        this.totalPages = response.totalPages;
        if (!this.comments || this.comments.length === 0) {
          this.comments = response.data as UserComment[];
        } else {
          this.comments.concat(response.data as UserComment[]);
        }

        if (this.comments && this.comments.length > 0 ){
          for (const comment of this.comments) {
            this.getReplies(comment);
          }
        }

      }
    });
  }


  private renderPie(id: string, rating: number): void {
    const canvas = document.getElementById(id) as HTMLCanvasElement;
    let ctx = canvas.getContext('2d');
    Chart.pluginService.register({
      beforeDraw(chart): void {
        const width = chart.width;
        const height = chart.height;
        ctx = chart.ctx;

        ctx.restore();
        const fontSize = (height / 114).toFixed(2);
        ctx.font = fontSize + 'em sans-serif';
        ctx.textBaseline = 'middle';

        const text = rating.toFixed(1).toString();
        const textX = Math.round((width - ctx.measureText(text).width) / 2);
        const textY = height / 2;

        ctx.fillStyle = 'white';
        ctx.fillText(text, textX, textY);
        ctx.save();
      }
    });

    const myChart = new Chart(ctx, {
      type: 'doughnut',
      data: {
        labels: ['r'],
        datasets: [{
          label: 'r',
          data: [rating, 10 - rating],
          backgroundColor: ['#f0ad4e'],
          borderWidth: 0
        }]
      },
      options: {
        cutoutPercentage: 55,
        animation: {
          duration: 0
        },
        legend: {
          display: false
        },
        tooltips: {
          enabled: false
        }
      },
    });
  }

  favourite(): void {
    if (this.isFavorite) {
      this.userService.dislikeMovie(this.token, this.film.movieId).subscribe(response => {
        this.isFavorite = false;
        this.getMovie();
      });
    } else {
      this.userService.likeMovie(this.token, this.film.movieId).subscribe(response => {
        this.isFavorite = true;
        this.getMovie();
      });

    }
  }

  MoreCast(): void {
    this.cast = this.film.actors.slice(0, this.cast.length + 10);
  }

  subcomment(id: number): void {
    if (this.token) {
      this.commentGroup.patchValue({replyComment: ''});
      if (this.activeSubComment === id) {
        this.activeSubComment = null;
      } else {
        this.activeSubComment = id;
      }
    }
  }

  like(id: number): void {
    this.commentService.likeComment( String(id), this.token).subscribe(response => {
      console.log(response);
    });
  }

  delete(id: number): void {
    this.commentService.deleteComment( String(id), this.token).subscribe(response => {
      console.log(response);
    });
  }

  moreComments(): void {
    this.getComments(this.currentPage + 1);
  }

  postComment(): void {
    const message = this.commentGroup.value.newComment;
    this.commentService.createComment(this.token, message, this.film.movieId).subscribe(response => {
      if (response.status === 200) {
        this.commentGroup.patchValue({newComment: ''});

        if (!this.comments) {
          this.getComments(1);
        } else if (this.comments.length % 10 === 0) {
          this.moreComments();
        } else {
          this.comments = this.comments.slice(0, Math.floor(this.comments.length / 10));
          this.currentPage--;
          this.getComments(this.currentPage + 1);
        }
      }
    });
  }

  reply(): void {
    const message = this.commentGroup.value.replyComment;
    const parentComment = this.comments.find(x => x.id === this.activeSubComment);
    this.commentService.createReplyComment(this.token, message, this.film.movieId, parentComment.id, parentComment.author.id).subscribe(
      response => {
        this.getReplies(parentComment);
      }
    );
  }

  private getReplies(comment: UserComment): void {
    this.commentService.getReplies(comment.id, 1).subscribe(response => {
      if (response.status === 200) {
        response = response.data;
        this.replies[comment.id] = response.data as UserComment[];
      }
    });
  }

  private checkIfIsFavorite(): void {
    this.userService.getFavorites(this.token, 1).subscribe(response => {
      if (response.status === 200 && response.data) {
        response = response.data;
        const favFilm = (response.data as Film[]).filter(x => x.title === this.film.title);
        this.isFavorite = favFilm.length !== 0;
      } else {
        this.isFavorite = false;
      }
    });
  }

  private getSchedulte(): void {
    this.cinemaService.getPremier(this.premiers[0].id).subscribe(response => {
      console.log('aaaaaaaaaaaaa');
      console.log(response);
    } );
  }
}
