import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';
import 'rxjs/add/operator/map';
import { FormGroup, FormControl } from '@angular/forms';

import { AuthentificationComponent } from '../authentification/authentification.component';

@Component({
  selector: 'app-proposition',
  templateUrl: './proposition.component.html',
  styleUrls: ['./proposition.component.css']
})
export class PropositionComponent implements OnInit {

  constructor(private http : Http) { 
    this.show = false;
  }
  
    httpdata;
    httpdata2;
    formdata;
    show:boolean;
    showFormMetier()
    {
      if(this.show)
        this.show = false;
      else
        this.show = true;
    }
    onClickSubmit(data)
    {
      
      this.http.post('http://'+AuthentificationComponent.url+':8085/webservice_appi-1.0.0-BUILD-SNAPSHOT/metier/ajouterMetier?titre='+data.titre+'&description='+data.description,{
        }).subscribe(data => {
        try{
          
          this.chargerMetiers();
          this.showFormMetier();
          console.log(data.json());
          
        }catch(Exception)
        {
            console.log(Exception.message);
        }   
        });

      this.formdata.reset();
    }
    chargerpropositios()
    {
      this.http.get("http://"+AuthentificationComponent.url+":8085/webservice_appi-1.0.0-BUILD-SNAPSHOT/proposition/propositions").
      map(
         (response) => response.json()
      ).
      subscribe(
        
         (data) => {this.displaydata(data);}
      )
    }
    chargerMetiers()
    {
      this.http.get("http://"+AuthentificationComponent.url+":8085/webservice_appi-1.0.0-BUILD-SNAPSHOT/metier/consulter").
      map(
         (response) => response.json()
      ).
      subscribe(
        
         (data) => {this.displaydata2(data);}
      )
    }
  ngOnInit() {
    this.formdata = new FormGroup({
      description: new FormControl(''),
      titre: new FormControl('')
   });
    this.chargerpropositios();
    this.chargerMetiers();
  }
  
  public submit(event,data) {
    let rep : boolean;
    switch(event) {
      case 'YES' :   
        rep = true;
        break;
      case 'NO' : 
        rep = false;
        break;
      default : break;
    }
    let p = new Prop(data.titre, data.description,data.id.cin,data.id.id,rep);
    
    var x = JSON.stringify(p);
    this.http.post('http://'+AuthentificationComponent.url+':8085/webservice_appi-1.0.0-BUILD-SNAPSHOT/proposition/accepter',{
      id:{cin:p.id.cin,id:p.id.id},
      titre:p.titre,
      description : p.description,
      reponse : p.reponse
    }).subscribe(data => {
      try{
        this.chargerpropositios();
        this.chargerMetiers();
        console.log(data.json());
        
      }catch(Exception)
      {
          console.log(Exception.message);
      }   
      });
  }
  displaydata(data) {
    this.httpdata = data;
  }
  displaydata2(data) {
    this.httpdata2 = data;
  }
}
class PropId
{
  id : string;
  cin : string;
  constructor(id : string ,cin : string)
  {
    this.id = id;
    this.cin = cin;
  }
}
class Prop {
  titre: string;
  description: string;
  id : PropId;
  reponse : boolean;
  constructor(t: string, d: string,cin:string,id:string,r:boolean) {
    this.titre = t;
    this.description = d;
    let aux = new PropId(id,cin);
    this.reponse = r;
    this.id = aux;
  }
}