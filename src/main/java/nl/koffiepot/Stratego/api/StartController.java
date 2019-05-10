package nl.koffiepot.Stratego.api;

import nl.koffiepot.Stratego.model.Bord;
import nl.koffiepot.Stratego.model.Speler;
import nl.koffiepot.Stratego.model.data.SpelerData;
import nl.koffiepot.Stratego.service.SpelerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("start")
public class StartController {

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

        Bord spelerBord = new Bord();

        spelerBord.bordPrinten();
        spelerBord.bordPrinten(0);

        speler1.beurt(spelerBord);
        spelerBord.bordPrinten(0);
    }
}
