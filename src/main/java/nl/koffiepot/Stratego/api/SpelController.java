package nl.koffiepot.Stratego.api;

import nl.koffiepot.Stratego.model.Speelstuk;
import nl.koffiepot.Stratego.model.Spel;
import nl.koffiepot.Stratego.model.Speler;
import nl.koffiepot.Stratego.model.data.SpeelStukData;
import nl.koffiepot.Stratego.model.data.SpelData;
import nl.koffiepot.Stratego.model.data.SpelerData;
import nl.koffiepot.Stratego.service.SpeelStukDataService;
import nl.koffiepot.Stratego.service.SpelDataService;
import nl.koffiepot.Stratego.service.SpelerDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/beurt/{xcoordinate}/{ycoordinate}/{direction}/{spelernaam}")
    public List<SpeelStukData> frontEndBeurt(@PathVariable int xcoordinate, @PathVariable int ycoordinate, @PathVariable String direction, @PathVariable String spelernaam){
        spel.frontEndBeurt(xcoordinate, ycoordinate, direction, spelernaam, speler1, speler2);

        Object[][] speelbord = spel.getSpelBord().getSpeelBord();
        java.util.List<SpeelStukData> speelStukken = new ArrayList<>();
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (speelbord[y][x] instanceof Speelstuk) {
                    Speelstuk speelstukOpBord = (Speelstuk) speelbord[y][x];
                    SpeelStukData opTeSlaanStuk = new SpeelStukData();
                    opTeSlaanStuk.setSpelNaam("een spelnaam");
                    opTeSlaanStuk.setTeam(speelstukOpBord.getTeam());
                    opTeSlaanStuk.setValue(speelstukOpBord.getValue());
                    opTeSlaanStuk.setXcoordinate(x);
                    opTeSlaanStuk.setYcoordinate(y);
                    speelStukken.add(opTeSlaanStuk);
                }
            }
        }
        return speelStukken;
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

    @GetMapping("/bordprinten")
    public List<SpeelStukData> getBord() {
        Object[][] speelbord = spel.getSpelBord().getSpeelBord();
        List<SpeelStukData> speelStukken = new ArrayList<>();
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (speelbord[y][x] instanceof Speelstuk) {
                    Speelstuk speelstukOpBord = (Speelstuk) speelbord[y][x];
                    SpeelStukData opTeSlaanStuk = new SpeelStukData();
                    opTeSlaanStuk.setSpelNaam("een spelnaam");
                    opTeSlaanStuk.setTeam(speelstukOpBord.getTeam());
                    opTeSlaanStuk.setValue(speelstukOpBord.getValue());
                    opTeSlaanStuk.setXcoordinate(x);
                    opTeSlaanStuk.setYcoordinate(y);
                    speelStukken.add(opTeSlaanStuk);
                }
            }
        }
        return speelStukken;
    }
}
