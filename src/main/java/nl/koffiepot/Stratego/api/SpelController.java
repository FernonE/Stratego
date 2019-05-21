package nl.koffiepot.Stratego.api;

import nl.koffiepot.Stratego.model.Spel;
import nl.koffiepot.Stratego.model.Speler;
import nl.koffiepot.Stratego.model.data.SpelData;
import nl.koffiepot.Stratego.model.data.SpelerData;
import nl.koffiepot.Stratego.service.SpeelStukDataService;
import nl.koffiepot.Stratego.service.SpelDataService;
import nl.koffiepot.Stratego.service.SpelerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class SpelController {

    @Autowired
    private SpelerDataService spelerDataService;

    @Autowired
    private SpelDataService spelDataService;

    @Autowired
    private SpeelStukDataService speelStukDataService;

    private SpelData spelData;
    private Speler speler1;
    private Speler speler2;
    private Spel spel = new Spel();

    @GetMapping
    public Iterable<SpelerData> getAllPlayers(){
        return spelerDataService.findAll();
    }


    @GetMapping("/start/{tempSpelerNaam1}/{tempSpelerNaam2}/{randomplacement}")
    public void startSpel(@PathVariable String tempSpelerNaam1, @PathVariable String tempSpelerNaam2, @PathVariable String randomplacement) {
        boolean Randomplacement = Boolean.parseBoolean(randomplacement);

        //eerst de spelers inladen als ze bestaan.
        Optional<SpelerData> speler1Data = spelerDataService.findBySpelerNaam(tempSpelerNaam1);
        if (speler1Data.isPresent()){
            speler1 = new Speler(tempSpelerNaam1, 0);
        } else {
            speler1 = new Speler("unnamed 1" , 0); // als de speler niet bestaat, maak dan een temporary speler
        }

        Optional<SpelerData> speler2Data = spelerDataService.findBySpelerNaam(tempSpelerNaam2);
        if (speler2Data.isPresent()){
            speler2 = new Speler(tempSpelerNaam2, 1);
        } else {
            speler2 = new Speler("unnamed 2", 1);
        }
        System.out.println(speler1);
        System.out.println(speler2);

        spel.newGame(speler1, speler2, Randomplacement);
        spel.doGame();
        spel.saveGame(spelDataService, spelerDataService, speelStukDataService);
    }

}
