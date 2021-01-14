import {Film} from './Film';
import {Session} from './Session';
import {User} from './User';
import {UserComment} from './UserComment';
import {Premier} from './Premier';

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
    data: Premier[];
    totalElements: number,
    totalPages: number
  };
  rooms: any[];

}
