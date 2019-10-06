import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule} from '@angular/router';


import { AppComponent } from './app.component';
import { AuthentificationComponent } from './authentification/authentification.component';
import { HomeComponent } from './home/home.component';
import { MessageComponent } from './message/message.component';
import { UsersComponent } from './users/users.component';
import { PropositionComponent } from './proposition/proposition.component';


@NgModule({
  declarations: [
    AppComponent,
    AuthentificationComponent,
    HomeComponent,
    MessageComponent,
    UsersComponent,
    PropositionComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpModule,
    RouterModule.forRoot([
      {
        path : "login",
        component : AuthentificationComponent
      },
      {
         path: 'home',
         component: HomeComponent
      }
      
   ])
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
