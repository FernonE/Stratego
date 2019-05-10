package nl.koffiepot.Stratego.model;

import nl.koffiepot.Stratego.model.Speelstukken.*;
import nl.koffiepot.Stratego.model.data.BordData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Bord {


    static private Blokkade blokkade = new Blokkade();



    static boolean checkValidPiece(int pionYlocation, int pionXLocation, int team, BordData bordData){
        bordData.getSpeelBord();
        Object gekozenStuk = bordData.getSpeelBord()[pionYlocation][pionXLocation];
        if(gekozenStuk == blokkade){
            System.out.println("Je hebt een blokkade gekozen");
            return false;
        } else if (gekozenStuk == null){
            System.out.println("Je hebt een lege plek gekozen");
            return false;
        } else {
            Speelstuk gekozenSpeelStuk = (Speelstuk)gekozenStuk; //casten naar een Speelstuk object
            if (gekozenSpeelStuk.getTeam() != team){
                System.out.println("het gekozen speelstuk is niet van jouw team");
                return false;
            } else {
                return true;
            }
        }
    }



    private static boolean movementCheck (int pionYLocation, int pionXLocation, BordData bordData) {
        //Check of de nieuwe plaats wel op het bord ligt
        if (pionYLocation < 0 || pionYLocation > 10 || pionXLocation < 0 || pionXLocation > 10) {
            System.out.println("Deze locatie zit buiten het bord");
            return false;
        }
        //Check of de nieuwe plaats wel beschikbaar is om heen te gaan
        else if (bordData.getSpeelBord()[pionYLocation][pionXLocation] instanceof Speelstuk) {
            System.out.println("Dit kan nog niet, hier staat een andere Speelstuk");
            return false;
        } else if (bordData.getSpeelBord()[pionYLocation][pionXLocation] instanceof Blokkade) {
            System.out.println("Hier kun je niet doorheen!");
            return false;
        } else {
            return true;
        }

    }

    //Deze code verplaatst de stukken, maar kan alleen aangeroepen worden nadat de movement check is uitgevoerd
    //Daarom is deze ook private!
    private static void movePiece (int pionYLocationNew, int pionXLocationNew, int pionYLocationOld, int pionXLocationOld, BordData bordData){
        //Sla het speelstuk op de nieuwe plaats op
        bordData.getSpeelBord()[pionYLocationNew][pionXLocationNew] = bordData.getSpeelBord()[pionYLocationOld][pionXLocationOld];
        //Gooi de oude weg
        bordData.getSpeelBord()[pionYLocationOld][pionXLocationOld] = null;
    }

    public static boolean moveChooser(int pionYLocation, int pionXLocation, BordData bordData) {
        Scanner scanner = new Scanner(System.in);
        String movementDirection = scanner.next();

        //Kijk of de input voldoet aan een van de volgende cases "u,d,r,l"
        switch (movementDirection) {
            case "u":
                //Check of hij wel in deze richting kan bewegen, zo ja: voer move uit, zo nee: nieuwe input vragen
                if (movementCheck(pionYLocation - 1,pionXLocation, bordData)){
                    movePiece(pionYLocation - 1,pionXLocation,pionYLocation,pionXLocation, bordData);
                    return true;
                } else {
                    return false;
                }
            case "d":
                if (movementCheck(pionYLocation + 1,pionXLocation, bordData)){
                    movePiece(pionYLocation + 1,pionXLocation,pionYLocation,pionXLocation, bordData);
                    return true;
                } else {
                    return false;
                }
            case "r":
                if (movementCheck(pionYLocation,pionXLocation + 1, bordData)){
                    movePiece(pionYLocation,pionXLocation + 1,pionYLocation,pionXLocation, bordData);
                    return true;
                } else {
                    return false;
                }
            case "l":
                if (movementCheck(pionYLocation,pionXLocation - 1, bordData)){
                    movePiece(pionYLocation,pionXLocation - 1,pionYLocation,pionXLocation, bordData);
                    return true;
                } else{
                    return false;
                }
            default:
                //Als geen geldige input wordt ingevuld, als "w,a,s" of "dr", dan komt hij hier in terecht en
                //vraagt hij om nieuwe input.
                System.out.println("U heeft een ongeldige richting gekozen, kies uit: Up (u), Down (d), Left (l), Right (r)");
                return false;
        }

    }


    //hieronder wordt het hele bord geprint, zie volgende methode voor team specifiek bord printen

    public static void bordPrinten(BordData bordData){
        StringBuilder bordstring = new StringBuilder();
        bordstring.append("X: | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |10 | \n"); //deze coordinaten worden geprint boven het bord
        bordstring.append("   -----------------------------------------\n"); // dit is een afscheiding van coordinaten tov gevulde matrix
        bordstring.append("Y: +---+---+---+---+---+---+---+---+---+---+\n");
            for (int y = 0; y < 10; y++) {
                int yCoordinaat = y+1;//deze y-coordinaat wordt gedefinieerd zodat deze geprint kan worden als coordinatenstelsel
                if(yCoordinaat<10) {
                    bordstring.append(" ");//getallen kleiner dan 10, krijgen extra spatie (voor uitlijning)
                }
                    bordstring.append(yCoordinaat + " ");
                    for (int x = 0; x < 10; x++) { //deze forloop voegt voor ieder vakje de value van het spelstuk toe of een "o" als het vakje leeg is.
                    String spelstukString;
                    if (bordData.getSpeelBord()[y][x] instanceof Speelstuk) {
                        Speelstuk speelstuk = (Speelstuk) bordData.getSpeelBord()[y][x];
                        if (speelstuk.getValue() < 10) {
                                spelstukString = "| " + speelstuk.getValue() + " "; //een extra spatie toevoegen als de waarde kleiner is dan tien, zodat de uitlijning mooi klopt.
                            } else {
                                spelstukString = "|" + speelstuk.getValue() + " ";
                            }

                    } else if (bordData.getSpeelBord()[y][x] instanceof Blokkade) { //Als er een String wordt gevonden dan is het een blokkade
                        spelstukString = "| x ";
                    } else { //Leeg stuk ruimte waar heen gelopen kan worden
                        spelstukString = "|   ";
                    }
                    bordstring.append(spelstukString);
                }
                bordstring.append("|\n");//Aan het einde komt nog een rechtstreepje en dan een niewline character
                bordstring.append("   +---+---+---+---+---+---+---+---+---+---+\n");
            }

        System.out.println(bordstring);
    }



    //met onderstaande methode wordt het bord geprint teamspecifiek, je geeft dan het team mee in de methode

    public static void bordPrinten(int huidigeTeam, BordData bordData){

        StringBuilder bordstring = new StringBuilder();
        bordstring.append("X: | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |10 | \n"); //deze coordinaten worden geprint boven het bord
        bordstring.append("   -----------------------------------------\n"); // dit is een afscheiding van coordinaten tov gevulde matrix
        bordstring.append("Y: +---+---+---+---+---+---+---+---+---+---+\n");
        for (int y = 0; y < 10; y++) {
            int yCoordinaat = y+1; //deze y-coordinaat wordt gedefinieerd zodat deze geprint kan worden als coordinatenstelsel
            if(yCoordinaat<10) {
                bordstring.append(" ");//getallen kleiner dan 10, krijgen extra spatie (voor uitlijning)
            }
            bordstring.append(yCoordinaat + " ");
            for (int x = 0; x < 10; x++) { //deze forloop voegt voor ieder vakje de value van het spelstuk toe of een "o" als het vakje leeg is.
                String spelstukString;
                if (bordData.getSpeelBord()[y][x] instanceof Speelstuk) {
                    Speelstuk speelstuk = (Speelstuk) bordData.getSpeelBord()[y][x];
                    if (speelstuk.getTeam()==huidigeTeam){ //als team gelijk is aan huidigeteam --> print de values van speelstuk
                        if (speelstuk.getValue() < 10) {
                            spelstukString = "| " + speelstuk.getValue() + " "; //een extra spatie toevoegen als de waarde kleiner is dan tien, zodat de uitlijning mooi klopt.
                        } else {
                            spelstukString = "|" + speelstuk.getValue() + " ";
                        }
                    }else {
                        spelstukString = "|xxx"; //print xx voor speelstuk van tegenstander (andere team)
                    }
                } else if (bordData.getSpeelBord()[y][x] instanceof Blokkade) { //Als er een String wordt gevonden dan is het een blokkade
                    spelstukString = "| x ";
                } else { //Leeg stuk ruimte waar heen gelopen kan worden
                    spelstukString = "|   ";
                }
                bordstring.append(spelstukString);
            }
            bordstring.append("|\n");//Aan het einde komt nog een rechtstreepje en dan een niewline character
            bordstring.append("   +---+---+---+---+---+---+---+---+---+---+\n");
        }
        System.out.println(bordstring);
    }
}





