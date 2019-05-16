package nl.koffiepot.Stratego.api;

import nl.koffiepot.Stratego.model.Bord;
import nl.koffiepot.Stratego.model.Speler;
import nl.koffiepot.Stratego.model.data.SpeelStukData;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("start")
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
    private String spelNaam;

    @GetMapping
    public Iterable<SpelerData> getAllPlayers(){
        return spelerDataService.findAll();
    }

    @GetMapping("/{tempSpelerNaam1}/{tempSpelerNaam2}")
    public void startSpel(@PathVariable String tempSpelerNaam1, @PathVariable String tempSpelerNaam2){
        clearScreen();
        boolean Randomplacement = true;
        spelNaam = tempSpelerNaam1 + tempSpelerNaam2;
        spelData.setSpelNaam(spelNaam); // het spelnaam opslaan in het object

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
            huidigeSpeler.beurt(spelerBord);

            /* In een aanval wordt gecheckt of de vlag is aangevallen en zet de waarde gewonnen op true voor die speler
            Daarna wordt hier uitgelezen of zijn status gewonnen is. Zoja dan telt hij er 1 op bij gewonnen en de
            verliezer bij ++ bij verloren. Gamerunning wordt daarna gestopt.
             */
            if (huidigeSpeler.isGewonnen()){
                gamerunning = false;

                //alle data opslaan
                //speler die won
                SpelerData spelerDieWon = spelerDataService.findBySpelerNaam(huidigeSpeler.getSpelerNaam()).get();
                spelerDieWon.setSpelerWins(spelerDieWon.getSpelerWins()+1);
                spelerDataService.save(spelerDieWon); //dit update de speler die won in de database, aangezien eerst een speler wordt geladen met een bepaalde id, waarna 1 field wordt veranderd en daarna weer wordt opgeslagen. Omdat de id hetzelfde is wordt de rij in de database opgeslagen i.p.v. een nieuwe rij aangemaakt

                //speler die verloor
                SpelerData spelerDieVerloor = spelerDataService.findBySpelerNaam(vorigeSpeler.getSpelerNaam()).get();
                spelerDieVerloor.setSpelerLosses(spelerDieVerloor.getSpelerLosses()+1);
                spelerDataService.save(spelerDieVerloor);

                //het spel zelf
                spelDataService.save(spelData);

                //de speelStukData
                /*
                Een probleem hierbij is dat we de speelStukData loskoppelen van het bord. dus er moet een functie gemaakt worden die het bord omzet in een lijst van speelStukData objecten.
                mogelijke oplosssingen:
                    - de x en y coordinaten bijhouden in Speelstuk tijdens het spelen
                    - bij het opslaan een lijst van SpeelStukData aanmaken door over het bord te loopen en deze data aan te maken, en dan op te slaan.
                        - hier kies ik voor /\
                 */
                List<SpeelStukData> speelStukDataList = speelStukDataService.generateList(spelerBord, spelNaam); // genereer een lijst van speelStukData objecten
                speelStukDataService.saveAll(speelStukDataList); //en gebruik dan saveAll om deze op te slaan.

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
        spelDataService.save(spelData);
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
