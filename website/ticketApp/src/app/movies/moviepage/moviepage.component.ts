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

  constructor(private ticketApiService: TicketApiService,
              private route: ActivatedRoute,
              private commentService: CommentService,
              private location: Location,
              private userService: UserService,
              private fb: FormBuilder,
              private router: Router) {}

  ngOnInit(): void {
    this.commentGroup = this.fb.group({
      newComment: new FormControl('')
    });

    this.token = localStorage.getItem('auth_token');
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
        } else {
          this.location.back();
        }
      }
    );
  }

  getComments(page: number): void {
    const id: string = this.route.snapshot.paramMap.get('id');
    this.commentService.getCommentByFilm(id, page - 1).subscribe(response => {
      if (response.status === 200) {
        response = response.data;
        this.comments = response.data as UserComment[];
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
    this.userService.likeMovie(this.token, this.film.movieId).subscribe(response => {
      console.log(response);
    });
    this.getMovie();
  };

  MoreCast(): void {
    this.cast = this.film.actors.slice(0, this.cast.length + 10);
  }

  subcomment(id: number): void {
    if (this.token) {
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

  postComment(): void {
    const message = this.commentGroup.value.newComment;
    this.commentService.createComment(this.token, message, this.film.movieId).subscribe(response => {
      console.log(response);
      if (response.status === 200) {
        this.commentGroup.patchValue({newComment: ''});
      }
    });
  }
}
