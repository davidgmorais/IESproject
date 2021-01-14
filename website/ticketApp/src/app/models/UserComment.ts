import {User} from './User';

export class UserComment {

  id: number;
  parentId: number;
  author: User;
  content: string;
  created: Date;
  likes: number;
  replyTo: number;
  // .......
  cinema: number;
  film: number;
  premier: number;
  // .......
  flag: boolean;


}
