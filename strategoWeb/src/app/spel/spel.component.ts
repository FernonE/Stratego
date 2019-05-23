import { Component, OnInit } from '@angular/core';
import { SpelService } from '../spel.service';
import { SpelerComponent } from '../speler/speler.component';
import { SpelerData } from '../speler-data';

@Component({
  selector: 'app-spel',
  templateUrl: './spel.component.html',
  styleUrls: ['./spel.component.css']
})
export class SpelComponent implements OnInit {

  constructor(private spelService : SpelService) { }

  ngOnInit() {
  }

  array = Array(10).fill("./assets/empty.png").map(() => Array(10).fill("./assets/empty.png"));
  

  speler1
  speler2
  stringSpeler
  speelStukken
  beurtURL
  kiezenxcoordinate
  kiezenycoordinate
  huidigespeler
  team
  running

  onClickNaarBoven(){
    this.beurtURL = "http://localhost:8080/beurt/"+(this.kiezenxcoordinate-1)+"/"+(this.kiezenycoordinate-1)+"/u/"+this.huidigespeler
    this.spelService.getBeurt(this.beurtURL).subscribe(() => this.onClickBord())
  }

  onClickNaarBeneden(){
    this.beurtURL = "http://localhost:8080/beurt/"+(this.kiezenxcoordinate-1)+"/"+(this.kiezenycoordinate-1)+"/d/"+this.huidigespeler
    this.spelService.getBeurt(this.beurtURL).subscribe(() => this.onClickBord())
  }

  onClickNaarLinks(){
    this.beurtURL = "http://localhost:8080/beurt/"+(this.kiezenxcoordinate-1)+"/"+(this.kiezenycoordinate-1)+"/l/"+this.huidigespeler
    this.spelService.getBeurt(this.beurtURL).subscribe(() => this.onClickBord())
  }

  onClickNaarRechts(){
    this.beurtURL = "http://localhost:8080/beurt/"+(this.kiezenxcoordinate-1)+"/"+(this.kiezenycoordinate-1)+"/r/"+this.huidigespeler
    this.spelService.getBeurt(this.beurtURL).subscribe(() => this.onClickBord())
  }

  onClickStartSpel(){
    this.stringSpeler = "http://localhost:8080/start/"+this.speler1+"/"+this.speler2+"/true"
    this.spelService.getStart(this.stringSpeler).subscribe(() => this.onClickBord())
    
  }

  onClickBord(){
    this.spelService.getBord().subscribe(speelStukken => {
      console.log("Ik ben in printen bord")
      this.speelStukken = speelStukken

      this.fillBord();
    })
  }

  fillBord(){
    this.array = Array(11).fill("./assets/empty.png").map(() => Array(11).fill("./assets/empty.png"));
 
    this.array[0][0] = "./assets/yx.png"
    this.array[0][1] = "./assets/1.png"
    this.array[0][2] = "./assets/2.png"
    this.array[0][3] = "./assets/3.png"
    this.array[0][4] = "./assets/4.png"
    this.array[0][5] = "./assets/5.png"
    this.array[0][6] = "./assets/6.png"
    this.array[0][7] = "./assets/7.png"
    this.array[0][8] = "./assets/8.png"
    this.array[0][9] = "./assets/9.png"
    this.array[0][10] = "./assets/10.png"

    this.array[1][0] = "./assets/1.png"
    this.array[2][0] = "./assets/2.png"
    this.array[3][0] = "./assets/3.png"
    this.array[4][0] = "./assets/4.png"
    this.array[5][0] = "./assets/5.png"
    this.array[6][0] = "./assets/6.png"
    this.array[7][0] = "./assets/7.png"
    this.array[8][0] = "./assets/8.png"
    this.array[9][0] = "./assets/9.png"
    this.array[10][0] = "./assets/10.png"
    this.array[5][3] = "./assets/blokkade.png"
    this.array[5][4] = "./assets/blokkade.png"
    this.array[6][3] = "./assets/blokkade.png"
    this.array[6][4] = "./assets/blokkade.png"
    this.array[5][7] = "./assets/blokkade.png"
    this.array[5][8] = "./assets/blokkade.png"
    this.array[6][7] = "./assets/blokkade.png"
    this.array[6][8] = "./assets/blokkade.png"

    for (let speelStukData of this.speelStukken){
      if (speelStukData.value == 1) this.array [speelStukData.ycoordinate+1][speelStukData.xcoordinate+1] = "./assets/stratego-spy.svg";
      if (speelStukData.value == 2) this.array [speelStukData.ycoordinate+1][speelStukData.xcoordinate+1] = "./assets/stratego-scout.svg";
      if (speelStukData.value == 3) this.array [speelStukData.ycoordinate+1][speelStukData.xcoordinate+1] = "./assets/stratego-miner.svg";
      if (speelStukData.value == 4) this.array [speelStukData.ycoordinate+1][speelStukData.xcoordinate+1] = "./assets/stratego-sergeant.svg";
      if (speelStukData.value == 5) this.array [speelStukData.ycoordinate+1][speelStukData.xcoordinate+1] = "./assets/stratego-lieutenant.svg";
      if (speelStukData.value == 6) this.array [speelStukData.ycoordinate+1][speelStukData.xcoordinate+1] = "./assets/stratego-captain.svg";
      if (speelStukData.value == 7) this.array [speelStukData.ycoordinate+1][speelStukData.xcoordinate+1] = "./assets/stratego-major.svg";
      if (speelStukData.value == 8) this.array [speelStukData.ycoordinate+1][speelStukData.xcoordinate+1] = "./assets/stratego-colonel.svg";
      if (speelStukData.value == 9) this.array [speelStukData.ycoordinate+1][speelStukData.xcoordinate+1] = "./assets/stratego-general.svg";
      if (speelStukData.value == 10) this.array [speelStukData.ycoordinate+1][speelStukData.xcoordinate+1] = "./assets/stratego-marshal.svg";
      if (speelStukData.value == 11) this.array [speelStukData.ycoordinate+1][speelStukData.xcoordinate+1] = "./assets/stratego-bomb.svg";
      if (speelStukData.value == 12) this.array [speelStukData.ycoordinate+1][speelStukData.xcoordinate+1] = "./assets/stratego-flag.svg";

      //this.array [speelStukData.ycoordinate+1][speelStukData.xcoordinate+1] = speelStukData.value;

    }
  }
}
