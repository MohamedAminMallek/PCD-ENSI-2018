import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl } from '@angular/forms';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';

import { AuthentificationComponent } from '../authentification/authentification.component';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  formdata;
  httpdata;
  ndate;
  constructor(private http: Http) { }
  dateFormat(date)
  {
    var time = new Date(date);
    return time.getDate()+"/"+time.getMonth()+"/"+time.getFullYear();
  }
  
  ngOnInit() {
    this.ndate = new Date();
    this.formdata = new FormGroup({
      jours : new FormControl('')
    });
    this.chargerUsers();
  }
  onClickSubmit(data,r) {
    
    this.http.post('http://'+AuthentificationComponent.url+':8085/webservice_appi-1.0.0-BUILD-SNAPSHOT/user/bannir?cin='+data[0]+'&jours='+r.jours+'',{}).subscribe(data => {
      try{
        console.log(data.json());
        this.formdata.reset();
        this.chargerUsers();
      }catch(Exception)
      {
          console.log(Exception.message);
      }   
      });
  }
 
  chargerUsers()
  {
    this.http.get("http://"+AuthentificationComponent.url+":8085/webservice_appi-1.0.0-BUILD-SNAPSHOT/user/").
    map(
       (response) => response.json()
    ).
    subscribe(
      
       (data) => {this.displaydata(data);}
    )
  }
  displaydata(data) {
    this.httpdata = data;
  }

}