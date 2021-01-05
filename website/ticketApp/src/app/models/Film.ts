import {Genre} from './Genre';
import {Actor} from './Actor';
import {Session} from './Session';
import {Cinema} from './Cinema';

export class Film {
  movieId: string;
  title: string;
  released: string;
  plot: string;
  runtime: number;
  director: string;
  year: string;
  like: number;
  genres: Genre[];
  rating: number;
  picture: string;
  header: string;
  actors: Actor[];
  session: Session[];
  cinemas: Cinema[];
  comments: Comment[];
}
