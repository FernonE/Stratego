import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SpelerData } from './speler-data';

@Injectable({
  providedIn: 'root'
})
export class SpelerService {

  constructor(private http: HttpClient) { }

  spelerData: SpelerData
  speler

  getAllSpelers(){
    console.log("Ik probeer spelers op te vragen met getall")
    return this.http.get("http://localhost:8080/speler")
  }

  sendSpeler(spelerNaam){
    console.log("Ik ga nu een speler toevoegen groetjes fernon")
    this.speler = new SpelerData; 
    this.speler.spelerNaam = spelerNaam
    return this.http.post("http://localhost:8080/speler",this.speler)
  }
}
