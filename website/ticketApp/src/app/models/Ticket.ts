import {Film} from './Film';
import {Session} from './Session';

export class Ticket {
  id: number;
  film: Film;
  price: number;
  sold: number;
  session: Session;
  seats: string[];
  quantity: number;

  constructor(id: number, film: Film, price: number, sold: number, session: Session, seats: string[], quantity: number) {
    this.id = id;
    this.film = film;
    this.price = price;
    this.sold = sold;
    this.session = session;
    this.seats = seats;
    this.quantity = quantity;
  }
}
