import { Component, OnInit } from '@angular/core';
import {catchError} from 'rxjs/operators';
import {throwError} from 'rxjs';
import {Cinema} from '../../models/Cinema';
import {ActivatedRoute, Router} from '@angular/router';
import {CinemaService} from '../../services/cinema.service';
import {Location} from '@angular/common';
import {TicketApiService} from '../../services/ticket-api.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Room} from '../../models/Room';
import {Seat} from '../../models/Seat';

@Component({
  selector: 'app-room-list',
  templateUrl: './room-list.component.html',
  styleUrls: ['./room-list.component.css']
})
export class RoomListComponent implements OnInit {
  token: string;
  role: string;
  cinemaId: string;
  rooms: Room[] = [];
  selectedRoom: Room;
  seats: Seat[][] = [];


  constructor(private route: ActivatedRoute,
              private router: Router,
              private cinemaService: CinemaService,
              private location: Location,
              private filmService: TicketApiService,
              private fb: FormBuilder) {}

  ngOnInit(): void {
    this.token = localStorage.getItem('auth_token');
    if (this.token === 'null') {this.token = null; }
    this.role = localStorage.getItem('user_role');
    this.cinemaId = localStorage.getItem('user_id');

    if (this.cinemaId == null || this.role !== '1') {
      this.router.navigateByUrl('/');
    }

    this.getRooms();
  }


  private getRooms(): void {
    this.cinemaService.getCinema(this.cinemaId).subscribe(response => {
      if (response.status === 200) {
        this.rooms = response.data.rooms as Room[];
        this.rooms = this.rooms.filter(x => x.positions.length !== 0 && x.positions.filter(y => y.x !== 'undefined' && y.y !== 'undefined').length !== 0);
      }
    });
  }

  showRoom(id: number): void {
    this.selectedRoom = this.rooms.filter(x => x.id === id)[0];
    console.log(this.selectedRoom);
    this.parseSeats();
  }

  private parseSeats(): void {
    this.seats = [];
    const listSeats = [];
    for (const a of this.selectedRoom.positions) {
      const seat: Seat = new Seat();
      seat.col = a.x;
      seat.row = a.y;
      listSeats.push(seat);
    }

    let row = [];
    let column;
    let count = 0;
    const seats: Seat[][] = [];

    while (count < this.selectedRoom.seats) {
      row = listSeats.filter( x => x.row === count.toString());
      count++;
      if (row.length !== 0) {
        seats.push(row);
      }
    }

    const columnNr = (Math.max.apply(Math, seats.map( (row) => Math.max.apply(Math, row.map( col => col.col )))));
    const rowNr = (Math.max.apply(Math, seats.map( (row) => Math.max.apply(Math, row.map( col => col.row )))));

    for (let r = 0; r <= rowNr; r++) {
      let row = [];
      for (let c = 0; c <= columnNr; c++) {
        row.push(null);
      }
      this.seats.push(row);
    }


    for (let r = 0; r <= rowNr; r++) {
      for (let c = 0; c <= columnNr; c++) {
        const s = listSeats.filter(x => x.row === r.toString() && x.col === c.toString());
        if (s.length !== 0) {
          this.seats[r][c] = s[0];
        }
      }
    }

  }
}
