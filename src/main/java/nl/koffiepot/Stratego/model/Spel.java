package nl.koffiepot.Stratego.model;

import nl.koffiepot.Stratego.model.data.SpeelStukData;
import nl.koffiepot.Stratego.model.data.SpelData;
import nl.koffiepot.Stratego.model.data.SpelerData;
import nl.koffiepot.Stratego.service.SpeelStukDataService;
import nl.koffiepot.Stratego.service.SpelDataService;
import nl.koffiepot.Stratego.service.SpelerDataService;

import java.util.ArrayList;
import java.util.List;

public class Spel {
    // Fields
    private Speler speler1;
    private Speler speler2;
    private Speler huidigeSpeler;
    private Speler vorigeSpeler;
    private Bord spelBord;
    private List<Speler> spelers;
    private List<SpeelStukData> speelStukDataList;
    private String spelNaam;
    private int turn;
    private boolean gamerunning;
    private SpelData spelData;

    //Constructor
    public Spel() {
    }

    //Methods
    public void newGame(Speler speler1, Speler speler2, boolean RandomPlacement) {
        spelNaam = speler1.getSpelerNaam() + speler2.getSpelerNaam();
        spelData = new SpelData();
        spelData.setSpelNaam(spelNaam);
        spelBord = new Bord(RandomPlacement);
        if (!RandomPlacement) {
            speler1.zetTeam(spelBord);
            speler2.zetTeam(spelBord);
        }

        spelers = new ArrayList<>();
        spelers.add(speler1);
        clearScreen();
        spelers.add(speler2);
    }

    public void doGame() {
        gamerunning = true;
        //vorigeSpeler = spelers.get(++turn);
        while (gamerunning) {
            huidigeSpeler = spelers.get(turn);
            clearScreen();
            spelBord.bordPrinten(huidigeSpeler.getSpelerTeam());
            huidigeSpeler.beurt(spelBord);
            if (huidigeSpeler.isGewonnen() || huidigeSpeler.isSaveGame()) {
                gamerunning = false;
                spelData.setCurrentTurn(turn);
                //endgame logic here
            } else {
                turn++;
                vorigeSpeler = huidigeSpeler;
                if (turn == spelers.size()) {
                    turn = 0;
                }
            }
        }
    }

    public void saveGame(SpelDataService spelDataService, SpelerDataService spelerDataService, SpeelStukDataService speelStukDataService) {
        speelStukDataList = generateList(spelBord, spelNaam);
        //alle data opslaan
        //speler die won
        SpelerData spelerDieWon = spelerDataService.findBySpelerNaam(huidigeSpeler.getSpelerNaam()).get();
        spelerDieWon.setSpelerWins(spelerDieWon.getSpelerWins() + 1);
        //spelerDataService.save(spelerDieWon); //dit update de speler die won in de database, aangezien eerst een speler wordt geladen met een bepaalde id, waarna 1 field wordt veranderd en daarna weer wordt opgeslagen. Omdat de id hetzelfde is wordt de rij in de database opgeslagen i.p.v. een nieuwe rij aangemaakt

        //speler die verloor
        if (vorigeSpeler == null) {
            vorigeSpeler = spelers.get(1);
        } // assign vorigeSpeler, mocht de eerste beurt al meteen willen opslaan
        SpelerData spelerDieVerloor = spelerDataService.findBySpelerNaam(vorigeSpeler.getSpelerNaam()).get();
        spelerDieVerloor.setSpelerLosses(spelerDieVerloor.getSpelerLosses() + 1);
        //spelerDataService.save(spelerDieVerloor);

        // dit loopt over alle speelstukData's en voegt ze toe aan de correcte speler.
        for (SpeelStukData speelStukData : speelStukDataList) {
            if (speelStukData.getTeam() == turn) {
                spelerDieWon.addSpeelStukData(speelStukData);
            } else {
                spelerDieVerloor.addSpeelStukData(speelStukData);
            }
        }
        speelStukDataService.saveAll(speelStukDataList);
        spelerDataService.save(spelerDieWon);
        spelerDataService.save(spelerDieVerloor);

        //het spel zelf
        spelData.addSpelerData(spelerDieWon);
        spelData.addSpelerData(spelerDieVerloor);
        spelDataService.save(spelData);
    }

    private List<SpeelStukData> generateList(Bord spelBord, String spelNaam) {
        Object[][] bord = spelBord.getSpeelBord();
        List<SpeelStukData> speelStukDataList = new ArrayList<>();
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (bord[y][x] instanceof Speelstuk) {
                    SpeelStukData speelStukData = new SpeelStukData();
                    speelStukData.setAllData((Speelstuk) bord[y][x], x, y, spelNaam);
                    speelStukDataList.add(speelStukData);
                }
            }
        }
        return speelStukDataList;
    }

    public void clearScreen() {
        System.out.println("\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n"
                + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n"
                + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n"
                + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n"
                + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n"
                + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n"
                + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n"
                + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n"
                + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n"
                + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n" + "\n");
    }


    //Getters and Setters
    public Speler getSpeler1() {
        return speler1;
    }

    public void setSpeler1(Speler speler1) {
        this.speler1 = speler1;
    }

    public Speler getSpeler2() {
        return speler2;
    }

    public void setSpeler2(Speler speler2) {
        this.speler2 = speler2;
    }

    public Bord getSpelBord() {
        return spelBord;
    }

    public void setSpelBord(Bord spelBord) {
        this.spelBord = spelBord;
    }
}
