import { Component, OnInit } from '@angular/core';
import { SpelerService } from '../speler.service';

@Component({
  selector: 'app-speler',
  templateUrl: './speler.component.html',
  styleUrls: ['./speler.component.css']
})
export class SpelerComponent implements OnInit {



  constructor(private spelerService: SpelerService) { }

  ngOnInit() {
    this.onClickGet()
  }

  spelerNaam

  spelers

  onClickSend(){
    this.spelerService.sendSpeler(this.spelerNaam).subscribe(() => this.onClickGet())
  }

  onClickGet(){
    this.spelerService.getAllSpelers().subscribe(spelers => this.spelers = spelers)      
  }
}
