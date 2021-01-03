import {Component, OnInit} from '@angular/core';
import * as $ from 'jquery';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute, Route, Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent implements OnInit {
  title = 'ticketApp';
  filterForm: FormGroup;
  token: string;
  userEmail: string;

  constructor(private route: Router, private location: ActivatedRoute) {}

  ngOnInit(): void {

    this.filterForm = new FormBuilder().group(
      {search: ''}
    );

    this.token = localStorage.getItem('auth_token');
    this.userEmail = localStorage.getItem('user_email');

    $(document).scroll( function() {
      if ($(document).scrollTop() === 0){
        $('.navbar').removeAttr('style');
      } else {
        $('.navbar').attr('style', 'background-color: #2e2e2e');
      }
    });

    $('.navbar-toggler').click( function() {
      if ($('.navbar').hasClass('bg-dark')) {
        $('.navbar').removeClass('bg-dark');
      } else {
        $('.navbar').addClass('bg-dark');
      }
    });
  }

  submit(): void {
    this.route.navigateByUrl('/movielist?search=' + this.filterForm.value.search);
  }

  logout(): void {
    this.token = null;
    this.userEmail = null;
    localStorage.setItem('auth_token', this.token);
    localStorage.setItem('user_email', this.userEmail);
    this.route.navigateByUrl('/');
  }

}
