import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  token: string;
  role: string;

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.token = localStorage.getItem('auth_token');
    this.role = localStorage.getItem('user_role');
    if (this.token === 'null' && this.role !== '2' ) {
      this.router.navigateByUrl('');
    }

  }

}
