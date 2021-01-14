import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CinemaService} from '../../services/cinema.service';
import {Location} from '@angular/common';
import {TicketApiService} from '../../services/ticket-api.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Seat} from '../../models/Seat';
import {Room} from '../../models/Room';


@Component({
  selector: 'app-add-room-layout',
  templateUrl: './add-room-layout.component.html',
  styleUrls: ['./add-room-layout.component.css']
})
export class AddRoomLayoutComponent implements OnInit {
  token: string;
  role: string;
  cinemaId: string;
  roomForm: FormGroup;
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

    this.createForm();
  }


  generate(): void {
    if (this.roomForm.invalid) {
      return;
    }

    this.seats = [];

    const cols = this.roomForm.value.cols;
    const rows = this.roomForm.value.rows;

    let seat;
    let tempRow: Seat[];
    for (let r = 0; r < rows; r++) {
      tempRow = [];
      for (let c = 0; c < cols; c++) {
        seat = new Seat();
        seat.row = r;
        seat.col = c;
        seat.selected = true;
        tempRow.push(seat);
      }
      this.seats.push(tempRow);
    }
  }

  select(seat: Seat): void {
    if (seat) {
      seat.selected = !seat.selected;
    }
  }

  private createForm(): void {
    this.roomForm = this.fb.group({
      name: ['', Validators.required],
      cols: ['', [Validators.required, Validators.min(1)]],
      rows: ['', [Validators.required, Validators.min(1)]],
    });
  }

  save(): void {
    const room = new Room();

    const positions = [];

    for (const r of this.seats) {
      for (const seat of r) {
        if (seat.selected) {
          positions.push(seat.x + ',' + seat.y);
        }
      }
    }

    room.name = this.roomForm.value.name;
    room.positions = positions;
    room.seats = positions.length;
    room.cinema = +this.cinemaId;
    console.log(room);

    this.cinemaService.createRoom(this.token, room).subscribe(response => {
      console.log(response);
      if (response.status === 200) {
        this.router.navigateByUrl('/cinema/rooms/my');
      }
    });
  }
}
