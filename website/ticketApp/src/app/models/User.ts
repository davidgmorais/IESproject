import {Film} from './Film';

export class User {
  id: number;
  password: string;
  userEmail: string;
  userName: string;
  role: number;
  avatar: string;
  flag: number;

  constructor(userEmail: string, userName: string, password: string) {
    this.password = password;
    this.userEmail = userEmail;
    this.userName = userName;
  }
}
