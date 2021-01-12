import { Component, OnInit } from '@angular/core';
import {Cinema} from '../../models/Cinema';
import {catchError} from 'rxjs/operators';
import {throwError} from 'rxjs';
import * as $ from 'jquery';
import {ActivatedRoute} from '@angular/router';
import {CinemaService} from '../../services/cinema.service';
import {Location} from '@angular/common';
import {OwlCarousel} from 'ngx-owl-carousel';


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

  constructor(private route: ActivatedRoute, private cinemaService: CinemaService, private location: Location) { }

  ngOnInit(): void {
    this.token = localStorage.getItem('auth_token');
    this.email = localStorage.getItem('user_email');
    if (this.token === 'null') {this.token = null; }
    this.getCinema();


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
      if (response.status === 200) {      // && response.data !== ""
        this.cinema = (response.data as Cinema);
      } else {
        this.location.back();
      }
    });
  }



}
