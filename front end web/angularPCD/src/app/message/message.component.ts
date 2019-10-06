import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';
import { FormGroup, FormControl } from '@angular/forms';
import {Router} from "@angular/router";
import {Observable} from 'rxjs';

import { AuthentificationComponent } from '../authentification/authentification.component';
@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {

  constructor(private http : Http,private router:Router) { }
  obs = Observable.interval(5000);
  httpdata;
  formdata;
  chargerMessage()
  {
    this.http.get("http://"+AuthentificationComponent.url+":8085/webservice_appi-1.0.0-BUILD-SNAPSHOT/message/consulterMessage").
    map(
       (response) => response.json()
    ).
    subscribe(
      
       (data) => {this.displaydata(data);}
    )
  }
  onClickSubmit(data,r) {

    
    this.http.post('http://'+AuthentificationComponent.url+':8085/webservice_appi-1.0.0-BUILD-SNAPSHOT/reponse/repondre?'+
    'message_id='+data.id.message_id+'&emetteur_id='+data.id.emetteur_id+'&description='+r.reponse+'&titre='+data.titre+'',{}).subscribe(data => {
      try{
        
        
        this.chargerMessage();
        this.formdata.reset();
        console.log(data.json());
        
        
      }catch(Exception)
      {
          console.log(Exception.message);
      }   
      });
  
  }



  ngOnInit() {
    this.formdata = new FormGroup({
      reponse : new FormControl('')
    });
    this.chargerMessage();
    this.obs.subscribe(x=>this.chargerMessage());
 }
  displaydata(data) {
    this.httpdata = data;
  }
  
}
