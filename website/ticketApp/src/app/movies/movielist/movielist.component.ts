import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Film} from '../../models/Film';
import {TicketApiService} from '../../services/ticket-api.service';

@Component({
  selector: 'app-movielist',
  templateUrl: './movielist.component.html',
  styleUrls: ['./movielist.component.css']
})
export class MovielistComponent implements OnInit {

  films: Film[] = [];

  constructor(private route: ActivatedRoute, private tickerApiService: TicketApiService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if (params.genre) {
        this.getByGenre(params.genre);
      } else {
        this.getMovies();
      }
    });
  }

  private getMovies(): void {
    this.tickerApiService.getMovies().subscribe(response => {
        console.log(response);
        if (response.status === 200) {
          this.films = (response.data as Film[]);
        }
    });
  }

  private getByGenre(genre: string): void {
    this.tickerApiService.getGenre(genre).subscribe(response => {
        if (response.status === 200) {
          this.films = (response.data as Film[]);
          console.log(this.films);
          console.log(response);
        }
    });
  }

  private getByYear(year: string): void {
    this.tickerApiService.getYear(year).subscribe(response => {
      if (response.status === 200) {
        this.films = (response.data as Film[]);
      }
    });
  }

  private orderBy(order: string): void{
    switch (order) {
      case 'new':
        this.films.sort( (a: Film, b: Film) => +b.year -  +a.year );
        break;
      case 'popular':
        this.films.sort( (a: Film, b: Film) => b.rating -  a.rating );
        break;
      case 'old':
        this.films.sort( (a: Film, b: Film) => +a.year -  +b.year );
        break;
      case 'alphabetically':
        this.films.sort( (a: Film, b: Film) => a.title.localeCompare(b.title) );
        break;
      case 'nearyou':
        break;
    }

  }

  receiveFilters(filter): void {
    console.log(filter);
    if (filter.clean) {
      this.getMovies();
    }
    if (filter.genre) {
      this.getByGenre(filter.genre);
    }
    if (filter.order){
      this.orderBy(filter.order);
    }
    if (filter.year){
      this.getByYear(filter.year);
    }
  }
}
