import {Component, OnInit} from '@angular/core';
import * as $ from 'jquery';
import {FormBuilder, FormGroup} from '@angular/forms';
import {Router} from '@angular/router';
import {UserService} from './services/user.service';
import {Notifications} from './models/Notifications';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = '2SeeOrNot';
  filterForm: FormGroup;
  token: string;
  userRole: string;
  notifications: Notifications[] = [];
  userId: string;

  constructor(private route: Router, private userService: UserService) {}

  ngOnInit(): void {
    this.filterForm = new FormBuilder().group(
      {search: ''}
    );

    this.token = localStorage.getItem('auth_token');
    if (this.token === 'null') {this.token = null; }
    this.userRole = localStorage.getItem('user_role');
    this.userId = localStorage.getItem('user_id');

    this.getNotifications();

    $(document).scroll( () => {
      if ($(document).scrollTop() === 0){
        $('.navbar').removeAttr('style');
      } else {
        $('.navbar').attr('style', 'background-color: #2e2e2e');
      }
    });

    $('.navbar-toggler').click( () => {
      if ($('.navbar').hasClass('bg-dark')) {
        $('.navbar').removeClass('bg-dark');
      } else {
        $('.navbar').addClass('bg-dark');
      }
    });
  }

  submit(): void {
    window.location.href = '/movielist/search/' + this.filterForm.value.search;
  }

  logout(): void {
    this.token = null;
    this.userRole = null;
    this.userId = null;
    localStorage.setItem('auth_token', null);
    localStorage.setItem('user_email', null);
    localStorage.setItem('user_role', null);
    localStorage.setItem('password', null);
    localStorage.setItem('user_id', null);
    this.route.navigateByUrl('/');
  }

  private getNotifications(): void {
    if (this.token){
      this.userService.getNotifications(this.token).subscribe(response => {
        this.notifications = response.data as Notifications[];
      });
    }
  }

  loadMore(): void {
    if (this.notifications.length % 10 === 0) {
      const pageNr = this.notifications.length / 10 + 1;
      this.userService.getNotifications(this.token, pageNr).subscribe(response => {
        this.notifications = this.notifications.concat( response.data as Notifications[]);
      });
    }
  }

  countUnread(): number {
    let count = 0;
    for (const notif of this.notifications) {
      if (notif.read === false) {
        count++;
      }
    }
    return count;
  }

}
