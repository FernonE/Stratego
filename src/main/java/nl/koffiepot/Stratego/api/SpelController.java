package nl.koffiepot.Stratego.api;

import nl.koffiepot.Stratego.model.Bord;
import nl.koffiepot.Stratego.model.Speler;
import nl.koffiepot.Stratego.model.data.SpelData;
import nl.koffiepot.Stratego.model.data.SpelerData;
import nl.koffiepot.Stratego.service.SpelService;
import nl.koffiepot.Stratego.service.SpelerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("start")
public class SpelController {

    @Autowired
    private SpelerService spelerService;

    @Autowired
    private SpelService spelService;

    private SpelData spelData;

    @GetMapping
    public Iterable<SpelerData> getAllPlayers(){
        return spelerService.findAll();
    }

    @GetMapping("/{tempSpelerNaam1}/{tempSpelerNaam2}")
    public void startSpel(@PathVariable String tempSpelerNaam1, @PathVariable String tempSpelerNaam2){
        clearScreen();
        boolean Randomplacement = true;

        Speler speler1 = new Speler(tempSpelerNaam1,0);
        Speler speler2 = new Speler(tempSpelerNaam2,1);
        System.out.println(speler1);
        System.out.println(speler2);

        Bord spelerBord = new Bord(Randomplacement);

        if(!Randomplacement) {
            speler1.zetTeam(spelerBord);
            clearScreen();
            speler2.zetTeam(spelerBord);
        }

        List<Speler> spelers = new ArrayList<>();
        spelers.add(speler1);
        spelers.add(speler2);


        int turn = 0;
        boolean gamerunning = true;

        /* Om de naamgeving duidelijk te houden heb ik vorige speler toegevoegd. Als je wint, dan wint natuurlijk
        de huidige speler. De vorige speler verliest dan logischer wijze. */
        Speler vorigeSpeler = null;

        while (gamerunning) {
            Speler huidigeSpeler = spelers.get(turn);
            spelerBord.bordPrinten(huidigeSpeler.getSpelerTeam());
            huidigeSpeler.beurt(spelerBord, huidigeSpeler);

            /* In een aanval wordt gecheckt of de vlag is aangevallen en zet de waarde gewonnen op true voor die speler
            Daarna wordt hier uitgelezen of zijn status gewonnen is. Zoja dan telt hij er 1 op bij gewonnen en de
            verliezer bij ++ bij verloren. Gamerunning wordt daarna gestopt.
             */
            if (huidigeSpeler.isGewonnen()){
                gamerunning = false;
                SpelerData spelerDieWon = spelerService.findBySpelerNaam(huidigeSpeler.getSpelerNaam()).get();
                spelerDieWon.setSpelerWins(spelerDieWon.getSpelerWins()+1);
                spelerService.save(spelerDieWon);

                SpelerData spelerDieVerloor = spelerService.findBySpelerNaam(vorigeSpeler.getSpelerNaam()).get();
                spelerDieVerloor.setSpelerLosses(spelerDieVerloor.getSpelerLosses()+1);
                spelerService.save(spelerDieVerloor);

                spelerBord.bordPrinten();
                System.out.println("Gewonnen: "+ huidigeSpeler);
                System.out.println("Verloren: "+ vorigeSpeler);
            } else {
                clearScreen();
                turn++;
                if (turn == spelers.size()) {
                    turn = 0;
                }
            }
            vorigeSpeler = huidigeSpeler; //Aan het einde van de beurt wordt de huidige speler als vorige speler gezet
        }
        System.out.println("Het spel is afgelopen");
    }

    public static void clearScreen() {
        System.out.println("\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"
                +"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"
                +"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"
                +"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"
                +"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"
                +"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"
                +"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"
                +"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"
                +"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"
                +"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n"+"\n");
    }
}
