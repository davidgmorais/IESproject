import {Component, Input, OnInit} from '@angular/core';
import * as Chart from 'chart.js';
import {TicketApiService} from '../../ticket-api.service';
import {Film} from '../../models/Film';

import {ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';

@Component({
  selector: 'app-moviepage',
  templateUrl: './moviepage.component.html',
  styleUrls: ['./moviepage.component.css']
})
export class MoviepageComponent implements OnInit {
  @Input()
  film: Film;

  constructor(private ticketApiService: TicketApiService,
              private route: ActivatedRoute,
              private location: Location) {}

  ngOnInit(): void {
    this.getMovie();
  }

  getMovie(): void {
    const id: string = this.route.snapshot.paramMap.get('id');
    this.ticketApiService.getMovie(String(id)).subscribe(
      response => {
        console.log(response);
        if (response.status === 200) {
          this.film = (response.data as Film);
          this.renderPie('pieChart', this.film.rating);
          console.log(this.film);
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

}
