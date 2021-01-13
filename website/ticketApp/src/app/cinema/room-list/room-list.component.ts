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
      console.log(response);
      if (response.status === 200) {
        this.rooms = response.data.rooms as Room[];
      }
    });
  }

}
