import { Component, OnInit } from '@angular/core';
import {Genre} from '../models/Genre';
import {Actor} from '../models/Actor';
import {TicketApiService} from '../ticket-api.service';
import {Film} from '../models/Film';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.css']
})
export class HomepageComponent implements OnInit {
  recentFilms: Film[] = [];
  headerFilms: Film[] = [];
  popularFilms: Film[] = [];

  constructor(private ticketApiService: TicketApiService) { }

  ngOnInit(): void {
    this.getRecentMovies()
    this.recentFilms.slice(8);
    this.getPopularMovies()
  }

  private getRecentMovies(): void {
    this.ticketApiService.getRecent()
      .subscribe(data => {
        for (let entry of data){
          let genreList: Genre[] = [];
          let cast: Map<string, Actor> = new Map<string, Actor>();

          Object.entries(entry['genres']).forEach(
            ([key, value]) => genreList.push(new Genre(<string>value))
          );

         /* Object.entries(entry['actors']).forEach(
            ([key, value]) => {
              if (cast.size != 2 ) {
                cast.set(value['character_name'], new Actor(value['name']));
              }
            }
          );*/

          this.recentFilms.push( new Film(
            entry['movieId'],
            entry['title'],
            entry['released'],
            entry['plot'],
            entry['runtime'],
            entry['director'],
            entry['year'],
            entry['like'],
            genreList,
            entry['rating'],
            entry['picture'],
            entry['header'],
            cast,
            null,
            null)
          );

          if (this.recentFilms.length == 8) break;

        }
        this.headerFilms = this.recentFilms.slice(0,3);
        console.log(this.headerFilms)
      });
  }

  private getPopularMovies() {
    this.ticketApiService.getPopular()
      .subscribe(data => {
        for (let entry of data['data']['movies']){
          let genreList: Genre[] = [];
          let cast: Map<string, Actor> = new Map<string, Actor>();

          Object.entries(entry['genres']).forEach(
            ([key, value]) => genreList.push(new Genre(<string>value))
          );

          /* Object.entries(entry['actors']).forEach(
             ([key, value]) => {
               if (cast.size != 2 ) {
                 cast.set(value['character_name'], new Actor(value['name']));
               }
             }
           );*/

          this.popularFilms.push( new Film(
            entry['movieId'],
            entry['title'],
            entry['released'],
            entry['plot'],
            entry['runtime'],
            entry['director'],
            entry['year'],
            entry['like'],
            genreList,
            entry['rating'],
            entry['picture'],
            entry['header'],
            cast,
            null,
            null)
          );

          if (this.popularFilms.length == 8) break;

        }
      });
  }
}
