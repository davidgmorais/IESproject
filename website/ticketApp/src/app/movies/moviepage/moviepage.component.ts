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

  constructor(private ticketApiService: TicketApiService,
              private route: ActivatedRoute,
              private location: Location,
              private router: Router) {}

  ngOnInit(): void {
    this.token = localStorage.getItem('auth_token');
    this.getMovie();
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
        if (response.status === 200) {
          this.film = (response.data as Film);
          this.film.comments = [new UserComment(1, 1, 'tau', new Date())];
          this.cast = this.film.actors.slice(0, 10);
          console.log(this.film);
          this.renderPie('pieChart', this.film.rating);

        } else {
          this.location.back();
        }
      }
    );
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

  MoreCast(): void {
    this.cast = this.film.actors.slice(0, this.cast.length + 10);
    console.log(this.cast.length);
    console.log(this.film.actors.length);
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

  }

  delete(id: number): void {

  }
}
