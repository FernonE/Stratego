
package nl.koffiepot.Stratego.model;

import java.util.Scanner;

public class Spel {
    private long id;

    private String name;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.print("Voer naam speler 1 in:");
        Speler speler1 = new Speler(scanner.nextLine(),0);
        System.out.print("Voer naam speler 2 in:");
        Speler speler2 = new Speler(scanner.nextLine(),1);

        System.out.println(speler1);
        System.out.println(speler2);

        Bord spelerBord = new Bord();

        //spelerBord.bordPrinten();
        spelerBord.bordPrinten(0);

        speler1.beurt(spelerBord);
        spelerBord.bordPrinten(0);

        speler1.beurt(spelerBord);
        spelerBord.bordPrinten(0);

        spelerBord.bordPrinten();

        speler1.beurt(spelerBord);
        spelerBord.bordPrinten(0);


        speler1.beurt(spelerBord);
        spelerBord.bordPrinten(0);

        speler1.beurt(spelerBord);
        spelerBord.bordPrinten(0);
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
