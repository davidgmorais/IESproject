import { NgModule } from '@angular/core';
import {RouterModule, Routes} from '@angular/router';

import {HomepageComponent} from './homepage/homepage.component';
import {MoviepageComponent} from './movies/moviepage/moviepage.component';
import {MovielistComponent} from './movies/movielist/movielist.component';
import {BuyticketComponent} from './movies/buyticket/buyticket.component';
import {TicketlistComponent} from './tickets/ticketlist/ticketlist.component';
import {LoginComponent} from './account/login/login.component';
import {SignupComponent} from './account/signup/signup.component';
import {ProfileComponent} from './account/profile/profile.component';
import {CinemalistComponent} from './cinemas/cinemalist/cinemalist.component';
import {AddCinemaComponent} from './account/add-cinema/add-cinema.component';
import {DashboardComponent} from './admin/dashboard/dashboard.component';
import {ManageRequestComponent} from './admin/manage-request/manage-request.component';
import {CinemaPageComponent} from './cinema/cinema-page/cinema-page.component';

const routes: Routes = [
  {path: '' , component: HomepageComponent},
  {path: 'movie/:id' , component: MoviepageComponent},
  {path: 'movielist' , component: MovielistComponent},
  {path: 'movielist/:filter' , component: MovielistComponent},
  {path: 'movielist/:filter/:id' , component: MovielistComponent},
  {path: 'buyticket', component: BuyticketComponent},
  {path: 'ticketlist', component: TicketlistComponent},
  {path: 'cinemalist', component: CinemalistComponent},
  {path: 'login', component: LoginComponent},
  {path: 'signup', component: SignupComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'registercinema', component: AddCinemaComponent},
  {path: 'admin', component: DashboardComponent},
  {path: 'admin/requests', component: ManageRequestComponent},
  {path: 'cinema/:id', component: CinemaPageComponent},

  {path: '**', redirectTo: '/'}
];

@NgModule({
  declarations: [],
  exports: [
    RouterModule
  ],
  imports: [
    RouterModule.forRoot(routes, {scrollPositionRestoration: 'enabled'})
  ]
})
export class AppRoutingModule { }
