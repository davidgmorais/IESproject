import { Component, OnInit } from '@angular/core';
import {Premier} from '../../models/Premier';
import {CinemaService} from '../../services/cinema.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-premier-details',
  templateUrl: './premier-details.component.html',
  styleUrls: ['./premier-details.component.css']
})
export class PremierDetailsComponent implements OnInit {
  premier: Premier;
  token: string;
  id: string;

  constructor(private cinemaService: CinemaService, private location: ActivatedRoute) { }

  ngOnInit(): void {
    this.token = localStorage.getItem('auth_token');
    this.getId();
    this.getPremier();
  }

  private getPremier(): void {
    this.cinemaService.getPremier(this.id).subscribe(response => {
      if (response.status === 200) {
        this.premier = response.data as Premier;
      }
    });
  }

  private getId(): void {
    this.location.paramMap.subscribe(params => {
      this.id = params.get('id');
    });
  }
}
