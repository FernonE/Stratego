package nl.koffiepot.Stratego.api;

import nl.koffiepot.Stratego.model.Bord;
import nl.koffiepot.Stratego.model.Speler;
import nl.koffiepot.Stratego.model.data.BordData;
import nl.koffiepot.Stratego.model.data.SpelerData;
import nl.koffiepot.Stratego.service.SpelerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


        Speler speler1 = new Speler(tempSpelerNaam1,0);
        Speler speler2 = new Speler(tempSpelerNaam2,1);
        System.out.println(speler1);
        System.out.println(speler2);

        BordData bordData = new BordData();

        spelerService.saveBord(bordData);
        Bord.bordPrinten(bordData);
        Bord.bordPrinten(0,bordData);
        speler1.beurt(bordData);
        Bord.bordPrinten(0,bordData);
        spelerService.saveBord(bordData);
    }

    @GetMapping("bord")
    public Iterable<BordData> getBordData(){
        return spelerService.findAllBord();
    }

    @GetMapping("/deletebord")
    public void deleteAllBord(){
        spelerService.deleteAllBord(spelerService.findAllBord());
    }
}
