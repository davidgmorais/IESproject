import {Genre} from './Genre';
import {Actor} from './Actor';
import {Session} from './Session';
import {Cinema} from './Cinema';

export class Film {
  id: string;
  title: string;
  released: string;   //change to date
  plot: string;
  runtime: number;
  director: string;
  year: string;
  like: number;
  genre: Genre[];
  rating: number;
  poster: string;
  header: string;
  actors: Map<string, Actor>;
  session: Session[];
  cinemas: Cinema[];

  constructor(id: string, title: string, released: string, plot: string, runtime: number, director: string, year: string, like: number,
              genre: Genre[], rating: number, poster: string, header: string, actors: Map<string, Actor>, session: Session[],
              cinemas: Cinema[]) {

    this.id = id;
    this.title = title;
    this.released = released;
    this.plot = plot;
    this.runtime = runtime;
    this.director = director;
    this.year = year;
    this.like = like;
    this.genre = genre;
    this.rating = rating;
    this.poster = poster;
    this.header = header;
    this.actors = actors;
    this.session = session;
    this.cinemas = cinemas;
  }

}
