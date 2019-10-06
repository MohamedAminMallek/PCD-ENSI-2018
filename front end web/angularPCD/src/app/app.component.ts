import { Component } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{

  constructor(private router : Router)
  {
    console.log(localStorage.getItem('session')=='true');
    
    if(localStorage.getItem('session')=='true'){
      this.router.navigate(['home']);  
    }else
      this.router.navigate(['login']);
  }


}
