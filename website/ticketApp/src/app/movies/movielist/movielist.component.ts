import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Film} from '../../models/Film';
import {TicketApiService} from '../../services/ticket-api.service';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';

@Component({
  selector: 'app-movielist',
  templateUrl: './movielist.component.html',
  styleUrls: ['./movielist.component.css']
})


export class MovielistComponent implements OnInit {

  years = [
      {value: new Date().getFullYear(), name: new Date().getFullYear()},
      {value: new Date().getFullYear() - 1, name: new Date().getFullYear() - 1},
      {value: new Date().getFullYear() - 2, name: new Date().getFullYear() - 2}
    ];

  genres = [
      {value: 'action', name: 'Action'}
    ];

  filterForm: FormGroup;
  films: Film[] = [];
  filter: string;

  constructor(private route: ActivatedRoute,
              private tickerApiService: TicketApiService,
              private router: Router,
              private fb: FormBuilder) { }

  ngOnInit(): void {
    this.filterForm = this.fb.group({
      genre: new FormControl(null),
      year:  new FormControl(null)
    });

    this.filter = this.route.snapshot.paramMap.get('filter');
    switch (this.filter) {
      case null:
        this.getMovies();
        break;
      case 'popular':
        this.getPopular();
        break;
      case 'recent':
        this.getRecent();
        break;
      case 'year':
        const year = this.route.snapshot.paramMap.get('id');
        this.getByYear(year);
        this.filterForm.patchValue({year: +year});
        break;
      case 'genre':
        const genre = this.route.snapshot.paramMap.get('id');
        this.getByGenre(genre);
        this.filterForm.patchValue({genre});
        break;
      case 'search':
        const query = this.route.snapshot.paramMap.get('id');
        if (query) {
          this.filterForm.reset();
          this.search(query);
        } else {
          window.location.href = '/movielist';
        }
        break;
      default:
        this.router.navigateByUrl('/');
    }

  }

  private getMovies(): void {
    this.tickerApiService.getMovies().subscribe(response => {
        if (response.status === 200) {
          this.films = (response.data as Film[]);
        }
    });
  }

  private getRecent(): void {
    this.tickerApiService.getRecent().subscribe(response => {
        if (response.status === 200) {
          this.films = (response.data as Film[]);
        }
    });
  }

  private getPopular(): void {
    this.tickerApiService.getPopular().subscribe(response => {
        if (response.status === 200) {
          this.films = (response.data as Film[]);
        }
    });
  }

  private getByGenre(genre: string): void {
    this.tickerApiService.getGenre(genre).subscribe(response => {
        if (response.status === 200) {
          this.films = (response.data as Film[]);
        }
    });
  }

  private getByYear(year: string): void {
    this.tickerApiService.getYear(year).subscribe(response => {
      if (response.status === 200) {
        if (response.data !== '') {
          this.films = (response.data as Film[]);
        }
      }
    });
  }

  private search(query: string): void {
    this.tickerApiService.search(query).subscribe(response => {
      if (response.status === 200) {
        this.films = (response.data as Film[]);
      }
    });
  }

  /*private orderBy(order: string): void{
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
    }

  }
*/

  selectYear(): void {
    window.location.href = '/movielist/year/' + this.filterForm.value.year;
  }

  selectGenre(): void {
    window.location.href = '/movielist/genre/' + this.filterForm.value.genre;

  }
}
