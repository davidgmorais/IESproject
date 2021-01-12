import {Film} from './Film';
import {Session} from './Session';
import {User} from './User';
import {UserComment} from './UserComment';

export class Cinema {
  location: string;
  email: string;
  user: User;
  description: string;
  followers: number;
  notification: number;
  comments: UserComment[];
  premiers: Film[];
  rooms: any[];

}
