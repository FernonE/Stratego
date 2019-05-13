package nl.koffiepot.Stratego.api;

import nl.koffiepot.Stratego.model.Bord;
import nl.koffiepot.Stratego.model.Speler;
import nl.koffiepot.Stratego.model.data.SpelerData;
import nl.koffiepot.Stratego.service.SpelerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("start")
public class SpelController {

    @Autowired
    private SpelerService spelerService;

    @GetMapping
    public Iterable<SpelerData> getAllPlayers(){
        return spelerService.findAll();
    }

    @GetMapping("/{tempSpelerNaam1}/{tempSpelerNaam2}")
    public void startSpel(@PathVariable String tempSpelerNaam1, @PathVariable String tempSpelerNaam2){
        boolean Randomplacement = true;

        Speler speler1 = new Speler(tempSpelerNaam1,0);
        Speler speler2 = new Speler(tempSpelerNaam2,1);
        System.out.println(speler1);
        System.out.println(speler2);

        Bord spelerBord = new Bord(Randomplacement);

        if(!Randomplacement) {
            speler1.zetTeam(spelerBord);
            speler2.zetTeam(spelerBord);
        }

        List<Speler> spelers = new ArrayList<>();
        spelers.add(speler1);
        spelers.add(speler2);


        int turn = 0;
        boolean gamerunning = true;

        while (gamerunning) {
            Speler huidigespeler = spelers.get(turn);
            spelerBord.bordPrinten(huidigespeler.getSpelerTeam());
            huidigespeler.beurt(spelerBord);
            /*
            if [einde spel] {
                gamerunning = false
            }
             */
            turn++;
            if (turn == spelers.size()) {
                turn = 0;
            }
        }
    }
}
