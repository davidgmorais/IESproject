import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {CinemaService} from '../../services/cinema.service';
import {Film} from '../../models/Film';
import {Seat} from '../../models/Seat';

@Component({
  selector: 'app-buyticket',
  templateUrl: './buyticket.component.html',
  styleUrls: ['./buyticket.component.css']
})
export class BuyticketComponent implements OnInit {
  premiereId: string;
  film: Film;
  cinema: string;
  date: string;
  time: string;
  toPlace: number = 1;
  totalPrice: number;
  total = 1;
  price: number;
  room: Seat[][] = [];
  constructor(private location: ActivatedRoute, private cinemaService: CinemaService) { }

  ngOnInit(): void {
    this.getId();
  }

  private getId(): void {
    this.location.paramMap.subscribe(params => {
      this.premiereId = params.get('id');
      console.log(this.premiereId);
      this.cinemaService.getPremier(this.premiereId).subscribe(response => {
        if (response.status === 200) {
          console.log(response);
          this.film = response.data.film as Film[];
          this.date = response.data.start.slice(0,10);
          this.price = response.data.price;
          this.totalPrice = this.price;
          this.time = response.data.start.slice(11,16);
          const cinemaId = response.data.cinema.id;

          this.cinemaService.getCinema(cinemaId).subscribe(res => {
            console.log(res);
            this.cinema = res.data.user.userName;
          });

        }
      });
    });
  }

  remove(): void {
    if (this.total > 1){
      this.total--;
      this.totalPrice -= this.price;
      if (this.totalPrice > 0){
        this.toPlace -= 1;
      }
    }
  }

  add(): void {
    this.total++;
    this.totalPrice += this.price;
    this.toPlace++;

  }

  pay(): void {
    if (this.toPlace !== 0) {
      return;
    }
  }
}
