import {Genre} from './Genre';
import {Actor} from './Actor';
import {Session} from './Session';
import {Cinema} from './Cinema';

export class Film {
  id: string;
  title: string;
  released: string;
  plot: string;
  runtime: number;
  director: string;
  year: string;
  like: number;
  genres: Genre[];
  rating: number;
  poster: string;
  header: string;
  actors: Actor[];
  session: Session[];
  cinemas: Cinema[];
}
