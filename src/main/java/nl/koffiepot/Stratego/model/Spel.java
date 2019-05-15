package nl.koffiepot.Stratego.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Spel {
    private long id;

    private String name;

    public static void main(String[] args) {
        boolean Randomplacement = false; //op true zetten om willekeurig een bord te maken, dit is mss makkelijker met testen.
        Scanner scanner = new Scanner(System.in);
        System.out.print("Voer naam speler 1 in:");
        Speler speler1 = new Speler(scanner.nextLine(), 0);
        System.out.print("Voer naam speler 2 in:");
        Speler speler2 = new Speler(scanner.nextLine(), 1);

        //De spelers uitprinten
        System.out.println(speler1);
        System.out.println(speler2);

        //de spelers in een lijst zetten
        List<Speler> spelers = new ArrayList<>();
        spelers.add(speler1);
        spelers.add(speler2);

        //Een nieuw bord aanmaken
        Bord spelerBord = new Bord(Randomplacement);

        if(!Randomplacement) {
            speler1.zetTeam(spelerBord);
            speler2.zetTeam(spelerBord);
        }



        int turn = 0;
        boolean gamerunning = true;

        while (gamerunning) {
            Speler huidigespeler = spelers.get(turn);
            spelerBord.bordPrinten(huidigespeler.getSpelerTeam());
            huidigespeler.beurt(spelerBord, huidigespeler);
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



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
