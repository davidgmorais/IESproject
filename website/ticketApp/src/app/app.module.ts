import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HomepageComponent } from './homepage/homepage.component';
import { MoviepageComponent } from './movies/moviepage/moviepage.component';
import { MovielistComponent } from './movies/movielist/movielist.component';
import { BuyticketComponent } from './movies/buyticket/buyticket.component';
import { TicketlistComponent } from './tickets/ticketlist/ticketlist.component';
import { LoginComponent } from './account/login/login.component';
import { ProfileComponent } from './account/profile/profile.component';
import { SignupComponent } from './account/signup/signup.component';
import { CinemalistComponent } from './cinemas/cinemalist/cinemalist.component';
import {HttpClientModule} from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AddCinemaComponent } from './account/add-cinema/add-cinema.component';


@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    MoviepageComponent,
    MovielistComponent,
    BuyticketComponent,
    TicketlistComponent,
    LoginComponent,
    ProfileComponent,
    SignupComponent,
    CinemalistComponent,
    AddCinemaComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        ReactiveFormsModule,
        FormsModule,
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
