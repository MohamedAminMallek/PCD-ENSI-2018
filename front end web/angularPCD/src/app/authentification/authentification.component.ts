import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import {Router} from "@angular/router";
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';
@Component({
  selector: 'app-authentification',
  templateUrl: './authentification.component.html',
  styleUrls: ['./authentification.component.css']
})
export class AuthentificationComponent implements OnInit {

  username;
  password;
  formdata;
  static url="localhost";
  
  constructor(private http: Http,private router: Router) { }

  ngOnInit() {

    this.formdata = new FormGroup({
      username: new FormControl("AminMallek"),
      password: new FormControl("pass")
   });

  }

  onClickSubmit(data) {
    this.username = data.username;
    this.password = data.password;
    
    this.http.post('http://'+AuthentificationComponent.url+':8085/webservice_appi-1.0.0-BUILD-SNAPSHOT/admin/authentification', {
      userName: this.username,
      passWord: this.password,
      id: ''
    }).subscribe(data => {
      try{
        console.log(data.json());
        
        localStorage.setItem('session','true');

        this.router.navigate(['home']);
      }catch(Exception)
      {
          alert("Check your data");
          console.log(Exception.message);
      }   
      });
    
  }
  
}
