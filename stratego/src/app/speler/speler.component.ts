import { Component, OnInit } from '@angular/core';
import { SpelerService } from '../speler.service';

@Component({
  selector: 'app-speler',
  templateUrl: './speler.component.html',
  styleUrls: ['./speler.component.css']
})
export class SpelerComponent implements OnInit {

  myText = "speler"

  testMyText; 

  speler; 

  constructor(private spelerService: SpelerService) { }

  ngOnInit() {
  }

  onClick() { 
    this.testMyText = this.myText
    this.spelerService.send(this.myText)

    //message service send
  }

}
