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
import {HttpClientModule} from '@angular/common/http';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { AddCinemaComponent } from './account/add-cinema/add-cinema.component';
import { ManageRequestComponent } from './admin/manage-request/manage-request.component';
import { DashboardComponent } from './admin/dashboard/dashboard.component';
import { CinemaPageComponent } from './cinema/cinema-page/cinema-page.component';
import { AddRoomLayoutComponent } from './cinema/add-room-layout/add-room-layout.component';
import { RoomListComponent } from './cinema/room-list/room-list.component';
import { CinemaListComponent } from './cinema/cinema-list/cinema-list.component';
import { PremierDetailsComponent } from './cinema/premier-details/premier-details.component';

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
    AddCinemaComponent,
    ManageRequestComponent,
    DashboardComponent,
    CinemaPageComponent,
    AddRoomLayoutComponent,
    RoomListComponent,
    CinemaListComponent,
    PremierDetailsComponent,
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
