import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SpelService {

  constructor(private http: HttpClient) { }

  getBeurt(beurtURL){
    console.log("Ik wil een beurt uitvoeren")
    return this.http.get(beurtURL)
  }

  getStart(startURL){
    console.log("Ik probeer een spel te starten in de console")
    return this.http.get(startURL)
  }

  getBord(){
    console.log("ik wil nu bord printen")
    return this.http.get("http://localhost:8080/bordprinten")
  }
}
