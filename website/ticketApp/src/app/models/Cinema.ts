import {Film} from './Film';
import {Session} from './Session';

export class Cinema {
  country: string;
  address: string;
  phoneNumber: string;
  email: string;
  films: Map<Film, Session[]>;

  constructor(country: string, address: string, phoneNumber: string, email: string, films: Map<Film, Session[]>) {
    this.country = country;
    this.address = address;
    this.phoneNumber = phoneNumber;
    this.email = email;
    this.films = films;
  }
}
