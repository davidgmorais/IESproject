import { Component, OnInit } from '@angular/core';
import {Cinema} from '../../models/Cinema';
import {CinemaService} from '../../services/cinema.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-cinema-list',
  templateUrl: './cinema-list.component.html',
  styleUrls: ['./cinema-list.component.css']
})
export class CinemaListComponent implements OnInit {
  cinemaList: Cinema[] = [];
  totalElements: number;
  totalPages: number;
  currentPage = 1;

  constructor(private cinemaService: CinemaService, private router: Router) { }

  ngOnInit(): void {
    this.getCinemaList(1);
  }

  private getCinemaList(page: number): void {
    this.cinemaService.getCinemas(page).subscribe( response => {
      if (response.status === 200) {
        response = response.data;
        this.cinemaList = response.data as Cinema[];
        this.totalElements = response.totalElements;
        this.totalPages = response.totalPages;
      }
    });

  }

  click(cinema: Cinema): void {
    this.router.navigateByUrl('/cinema/' + cinema.user.id);
  }

  nextPage(): void {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.getCinemaList(this.currentPage);
    }
  }

  previousPage(): void {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.getCinemaList(this.currentPage);
    }
  }
}
