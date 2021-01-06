import { Component, OnInit } from '@angular/core';
import {Genre} from '../models/Genre';
import {Actor} from '../models/Actor';
import {TicketApiService} from '../services/ticket-api.service';
import {Film} from '../models/Film';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  recentFilms: Film[] = [];
  popularFilms: Film[] = [];

  constructor(private ticketApiService: TicketApiService) { }

  ngOnInit(): void {
   this.getRecentMovies();
   this.getPopularMovies();
  }

  private getRecentMovies(): void {
    this.ticketApiService.getRecent().subscribe(
      response => {
        if (response.status === 200) {
          this.recentFilms = (response.data as Film[]);
        }
      }
    );
  }

  private getPopularMovies(): void {
    this.ticketApiService.getPopular().subscribe(
      response => {
        if (response.status === 200) {
          this.popularFilms = (response.data as Film[]);
        }
      }
    );
  }
}
