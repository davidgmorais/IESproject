import { Component, OnInit } from '@angular/core';
import * as Chart from 'chart.js';

@Component({
  selector: 'app-moviepage',
  templateUrl: './moviepage.component.html',
  styleUrls: ['./moviepage.component.css']
})
export class MoviepageComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {

    function renderPie(id: string, rating: number): void {
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

          ctx.fillText(text, textX, textY);
          ctx.fillStyle = 'white';
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
          legend: {
            display: false
          },
          tooltips: {
            enabled: false
          }
        },
      });
      ctx.fillText('5.8', ctx.canvas.width / 2 - 20, ctx.canvas.width / 2, 200);
    }

    const r = 5.812;
    renderPie('pieChart', r);
  }

}
