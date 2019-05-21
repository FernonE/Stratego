import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class SpelerService {

  constructor(private http: HttpClient) { }


  send(text) {
    console.log("in message service send " + text)

    this.http.post("http://localhost:8081/message", text).subscribe() //subscribe komt het resultaat in terug
  }

  getAll() {
    return this.http.get("http://localhost:8081/message")//subscribe() kan je niet doen, vanuit service kna je niet naar HTML en je wilt wel laten zien op HTML dus gaj e via component //kan je ontvangen wat je hebt gestuurd, die is nu verplaatst naar component
  }
}
