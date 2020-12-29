import {Component, OnInit} from '@angular/core';
import * as $ from 'jquery';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'ticketApp';

  ngOnInit(): void {
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
}
