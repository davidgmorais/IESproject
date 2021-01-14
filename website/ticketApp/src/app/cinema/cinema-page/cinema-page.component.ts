import { Component, OnInit } from '@angular/core';
import {Cinema} from '../../models/Cinema';
import {catchError} from 'rxjs/operators';
import {throwError} from 'rxjs';
import {ActivatedRoute, Router} from '@angular/router';
import {CinemaService} from '../../services/cinema.service';
import {formatDate, Location} from '@angular/common';
import {Film} from '../../models/Film';
import {TicketApiService} from '../../services/ticket-api.service';
import {Premier} from '../../models/Premier';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Session} from '../../models/Session';
import {UserComment} from '../../models/UserComment';
import {CommentService} from '../../services/comment.service';


@Component({
  selector: 'app-cinema-page',
  templateUrl: './cinema-page.component.html',
  styleUrls: ['./cinema-page.component.css']
})
export class CinemaPageComponent implements OnInit {
  cinema: Cinema;
  token: string;
  email: string;
  errorMsg: string;
  premiers: Premier[];
  availableFilm: Film[];
  filmTotal: number;
  totalPages: number;
  selectedPremier: Premier;
  filmToPremier: Film;
  premierFormGroup: FormGroup;
  searchForm: FormGroup;
  sessionList: Session[] = [];
  roomList: {value: string, name: string}[] = [];
  commentGroup: FormGroup;
  comments: UserComment[];
  userEmail: string;
  totalCommentPages: number;
  totalComments: number;
  currentPage = 1;
  replies: {[key: number]: UserComment[]} = {};
  activeSubComment: number;
  toEdit: Premier;

  constructor(private route: ActivatedRoute,
              private cinemaService: CinemaService,
              private location: Location,
              private router: Router,
              private filmService: TicketApiService,
              private fb: FormBuilder,
              private commentService: CommentService) { }

  ngOnInit(): void {
    this.token = localStorage.getItem('auth_token');
    this.email = localStorage.getItem('user_email');
    if (this.token === 'null') {this.token = null; }
    this.getCinema();


    this.commentGroup = this.fb.group({
      newComment: new FormControl(''),
      replyComment: new FormControl('')
    });

    this.getComments(1);

    this.searchForm = this.fb.group({
      search: new FormControl(''),
    });


  }

  private getCinema(): void {
    const id: string = this.route.snapshot.paramMap.get('id');
    this.cinemaService.getCinema(String(id)).pipe(
      catchError(error => {
        if (error.error instanceof ErrorEvent) {
          this.errorMsg = `Error: ${error.error.message}`;
        } else {
          this.location.back();
          this.errorMsg = `Error: ${error.message}`;
        }
        return throwError(this.errorMsg);
      })
    ).subscribe(response => {
      console.log(response);
      if (response.status === 200) {      // && response.data !== ""
        this.cinema = (response.data as Cinema);
        this.premiers = this.cinema.premiers.data as Premier[];
        console.log(this.premiers);
      } else {
        this.location.back();
      }
    });
  }

  private getAvailableFilms(page: number): void {
    this.filmService.getMovies(page).subscribe(response => {
      if (response.status === 200) {
        response = response.data;
        this.filmTotal = response.totalElements;
        this.totalPages = response.totalPages;
        this.availableFilm = (response.data as Film[]);
      }
    });
  }

  addPremier(): void {
    this.selectedPremier = new Premier();
    this.getAvailableFilms(1);
    this.createForm(null);
  }


  selectedMovie(film: Film): void {
    this.filmToPremier = film;
  }

  cancelAdd(): void {
    this.selectedPremier = null;
    this.filmToPremier = null;
  }

  private createForm(premier: Premier): void {

    this.cinemaService.getCinema(this.cinema.user.id.toString()).subscribe(response => {
      if (response.status === 200) {
        for (const r of response.data.rooms) {
          this.roomList.push({value: r.id, name: r.name});
        }
      }
    });

    if (premier) {
      this.premierFormGroup = this.fb.group({
        price: [premier.price, [Validators.required, Validators.min(1)]],
        startDate: [formatDate(new Date(premier.start), 'yyyy-MM-dd', 'en'), Validators.required],
        endDate: [formatDate(new Date(premier.end), 'yyyy-MM-dd', 'en'), Validators.required],
        session1start: new FormControl(''),
        session1room: new FormControl('')
      });
    } else {
      this.premierFormGroup = this.fb.group({
        price: ['', [Validators.required, Validators.min(1)]],
        startDate: ['', Validators.required],
        endDate: ['', Validators.required],
        session1start: new FormControl(''),
        session1room: new FormControl('')
      });
    }
  }

  addSession(): void {
    if (!this.premierFormGroup.controls.session1start.dirty || !this.premierFormGroup.controls.session1room.dirty) {
      return;
    }

    const hour = this.premierFormGroup.value.session1start.split(':')[0] as number;
    const min = this.premierFormGroup.value.session1start.split(':')[1] as number;
    const date = new Date();
    date.setHours(hour, min);

    const session = new Session();
    session.startDate = date;
    session.room = this.premierFormGroup.value.session1room;

    this.sessionList.push(session);
    this.premierFormGroup.patchValue({session1start: '', session1room: ''});

  }

  deleteSession(session: Session): void {
    const s = this.sessionList.find(x => x === session);
    const index = this.sessionList.indexOf(s);
    if (index > -1) {
      this.sessionList.splice(index, 1);
    }
  }

  createPremiere(): void {
    if (this.premierFormGroup.invalid || this.sessionList.length <= 0) {
      return;
    }

    const date = new Date(this.premierFormGroup.value.startDate);

    console.log(new Date(this.premierFormGroup.value.startDate));
    console.log((this.premierFormGroup.value.startDate));

    const premiere = new Premier();
    premiere.film = this.filmToPremier.movieId;
    premiere.cinema = this.cinema.user.id;
    premiere.start = new Date(this.premierFormGroup.value.startDate);
    premiere.end = new Date(this.premierFormGroup.value.endDate);
    premiere.start = this.premierFormGroup.value.startDate;
    premiere.end = this.premierFormGroup.value.startDate;
    premiere.price = this.premierFormGroup.value.price;
    premiere.schedules = [];


    for (const schedule of this.sessionList) {
      schedule.startDate.setDate(date.getDate());
      schedule.endDate = date;
      schedule.endDate.setTime( schedule.startDate.getTime() + this.filmToPremier.runtime * 60 * 1000);
      premiere.schedules.push(schedule);

      console.log(schedule.startDate.toTimeString());

      schedule.start = premiere.start + ' ' + schedule.startDate.toTimeString().split(' ')[0];
      schedule.end = premiere.end + ' ' + schedule.endDate.toTimeString().split(' ')[0];
      // 'yyyy-MM-dd HH:mm:ss'


      this.cinemaService.createSchedule(this.token, schedule).subscribe(response => {
        console.log(schedule);
        console.log(response);
      });
    }

    console.log(premiere);
    this.cinemaService.createPremiere(this.token, premiere).subscribe(response => {
      console.log(response);
    });
    this.getCinema();

  }

  getComments(page: number): void {
    const id: string = this.route.snapshot.paramMap.get('id');
    this.commentService.getCommentByCinema(id, page ).subscribe(response => {
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
    this.commentService.createCinemaComment(this.token, message, this.cinema.user.id).subscribe(response => {
      console.log(response);
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
    console.log('parentCommentId:' + parentComment.id + ', parentuserId: ' + parentComment.author.id);
    this.commentService.createCinemaReplyComment(this.token, message, this.cinema.user.id, parentComment.id, parentComment.author.id).subscribe(
      response => {
        console.log(response);

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

  searchFilm(): void {
    if (!this.searchForm.dirty) {
      return;
    }
    this.filmService.search(this.searchForm.value.search, 1).subscribe(response => {
      if (response.status === 200) {
        response = response.data;
        this.availableFilm = response.data as Film[];
      }
    });
  }

  editPremier(premier: Premier): void {
    this.toEdit = premier;
    this.cinemaService.getCinema(this.cinema.user.id.toString()).subscribe(response => {
      if (response.status === 200) {
        for (const r of response.data.rooms) {
          this.roomList.push({value: r.id, name: r.name});
        }
      }
    });
    this.createForm(premier);
  }

}
