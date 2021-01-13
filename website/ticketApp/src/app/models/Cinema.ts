import {Film} from './Film';
import {Session} from './Session';
import {User} from './User';
import {UserComment} from './UserComment';

export class Cinema {
  id: number;
  location: string;
  email: string;
  user: User;
  description: string;
  followers: number;
  notification: number;
  comments: UserComment[];
  premiers: {
    data: Film[];
    totalElements: number,
    totalPages: number
  };
  rooms: any[];

}
