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
  totalPages: number;
  filmTotal: number;
  currentPage = 1;

  constructor(private route: ActivatedRoute,
              private tickerApiService: TicketApiService,
              private router: Router,
              private fb: FormBuilder) { }

  ngOnInit(): void {
    this.filterForm = this.fb.group({
      genre: new FormControl(null),
      year:  new FormControl(null)
    });
    this.loadMovies(1);

  }

  loadMovies(page: number): void {
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
      case 'actor':
        const actor = this.route.snapshot.paramMap.get('id');
        this.getByActor(actor, page);
        break;
      case 'director':
        const director = this.route.snapshot.paramMap.get('id');
        this.getByDirector(director, page);
        break;
      case 'search':
        const query = this.route.snapshot.paramMap.get('id');
        if (query) {
          this.filterForm.reset();
          this.search(query, page);
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

  private getByActor(actor: string, page: number): void {
    this.tickerApiService.getActor(actor, page).subscribe( response => {
      if (response.status === 200) {
        this.films = (response.data as Film[]);
      }
    });

  }

  private getByDirector(director: string, page: number): void {
    this.tickerApiService.getDirector(director, page).subscribe( response => {
      if (response.status === 200) {
        this.films = (response.data as Film[]);
      }
    });

  }

  private search(query: string, page: number): void {
    this.tickerApiService.search(query, page).subscribe(response => {
      if (response.status === 200) {
        response = response.data;
        this.filmTotal = response.totalElements;
        this.totalPages = response.totalPages;
        this.films = (response.data as Film[]);
      }
    });
  }

  selectYear(): void {
    window.location.href = '/movielist/year/' + this.filterForm.value.year;
  }

  selectGenre(): void {
    window.location.href = '/movielist/genre/' + this.filterForm.value.genre;

  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.loadMovies(this.currentPage);
    }
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.loadMovies(this.currentPage);
    }
  }


}
