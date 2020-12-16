import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HomepageComponent } from './homepage/homepage.component';
import { MoviepageComponent } from './movies/moviepage/moviepage.component';
import { MovielistComponent } from './movies/movielist/movielist.component';
import { FiltersComponent } from './filters/filters.component';
import { BuyticketComponent } from './movies/buyticket/buyticket.component';
import { TicketlistComponent } from './tickets/ticketlist/ticketlist.component';
import { LoginComponent } from './account/login/login.component';
import { ProfileComponent } from './account/profile/profile.component';
import { SignupComponent } from './account/signup/signup.component';
import { CinemalistComponent } from './cinemas/cinemalist/cinemalist.component';
import {HttpClientModule} from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    HomepageComponent,
    MoviepageComponent,
    MovielistComponent,
    FiltersComponent,
    BuyticketComponent,
    TicketlistComponent,
    LoginComponent,
    ProfileComponent,
    SignupComponent,
    CinemalistComponent,
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
