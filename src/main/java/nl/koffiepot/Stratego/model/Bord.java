
package nl.koffiepot.Stratego.model;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Bord {

    //variables
    private long id;
    private String naam = "default";
    Object[][] speelBord = new Object[10][10];
    private Blokkade blokkade = new Blokkade();


    //Constructor(s), de default constructor
    public Bord(boolean RandomPlacement) {
        if (RandomPlacement) {
            List<Speelstuk> team1 = Speler.createteam(0); //De tijdelijk functie om een team aan te maken aante roepen
            List<Speelstuk> team2 = Speler.createteam(1); //
            Random rand = new Random();

            LOOP:
            while (true) {
                for (int y = 0; y < 4; y++) { //het bord vullen
                    for (int x = 0; x < 10; x++) {
                        int ind = rand.nextInt(team1.size());
                        speelBord[y][x] = team1.get(ind);
                        team1.remove(ind);

                        ind = rand.nextInt(team2.size()); //dit kan gelijk voor team 2, de x coordinaat wordt alleen met 6 verhoogd.
                        speelBord[y + 6][x] = team2.get(ind);
                        team2.remove(ind);
                        if (team1.isEmpty()) break LOOP;
                    }
                }
            }
        }
        //hardcoded blokkades
        speelBord[4][2] = blokkade; //coordinaten 5,3
        speelBord[4][3] = blokkade; //coordinaten 5,4
        speelBord[5][2] = blokkade;
        speelBord[5][3] = blokkade;
        speelBord[4][6] = blokkade;
        speelBord[4][7] = blokkade;
        speelBord[5][6] = blokkade;
        speelBord[5][7] = blokkade;
    }

    public void setPiece(int y, int x, Speelstuk speelstuk){
        speelBord[y][x] = speelstuk;
    }

    //method for asking pion (int y, int x)
    // calls method if pion is own team
    // calls method if pion can move in any direction

    boolean checkValidPiece(int pionYLocation, int pionXLocation, int team){
        Object gekozenStuk = speelBord[pionYLocation][pionXLocation];
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
            } else { //als het gekozen speelstuk eindelijk wel goed is, dan even kijken of er een beweegbare plek is (1 plek omheen die 'null' is)
                //if (speelBord[pionYLocation][pionXLocation+1] == null || speelBord[pionYLocation][pionXLocation-1] == null //deze check heeft een bug als een correcte speelstuk aan de rand is gekozen omdat hij dan buiten het bord check of de index 'null' is. Maar buiten het bord is er geen index dus krijg je een ArrayIndexOutOfboundsException.
                //        || speelBord[pionYLocation-1][pionXLocation] == null || speelBord[pionYLocation+1][pionXLocation] == null){
                if (gekozenSpeelStuk.getNaam().equals("bom")) {
                    System.out.println("Je kunt geen bom bewegen");
                    return false;
                } else if (gekozenSpeelStuk.getNaam().equals("vlag")) {
                    System.out.println("Je kunt geen vlag bewegen");
                    return false;
                } else if (movementCheck(pionYLocation, pionXLocation + 1, false, team) //in deze if statement wordt elke richting gecheckt, als er eentje mogelijk is dan komt er True uit en is het gekozen speelstuk een Valid Piece. Als het gekozen speelstuk van eigen team is maar geen enkele kant op kan dan komt hier false uit dus is het niet mogelijk
                        || movementCheck(pionYLocation,pionXLocation-1, false,team)
                        || movementCheck(pionYLocation + 1, pionXLocation,false,team)
                        || movementCheck(pionYLocation - 1, pionXLocation,false,team)){
                    return true;
                } else {
                    System.out.println("Je hebt een speelstuk gekozen dat niet bewogen kan worden");
                    return false;
                }
            }
        } //else return true;
    }




    private boolean movementCheck (int pionYLocation, int pionXLocation, boolean printInfo, int team) {
        //Check of de nieuwe plaats wel op het bord ligt
        //RICX: ik heb deze methode iets aangepakt zodat ik deze ook kan aanroepen in checkValidPiece om te kijken of het gekozen speelstuk uberhaupt kan bewegen.
        //nu wordt elke richting a.d.h.v. deze functie gecheck om te kijken of het mogelijk is, maar in checkValidPiec wordt enige informatie niet geprint.
        //bij movePiece wordt deze check ook uigevoerd met de ingegeven mogelijkheden en dan wordt de informatie wel gecheckt.

        if (pionYLocation < 0 || pionYLocation > 9 || pionXLocation < 0 || pionXLocation > 9) { //check even of dit niet out of bounds geeft
            if (printInfo) {
                System.out.println("Deze locatie zit buiten het bord");
            }
            return false;  // out of bounds -> dus mag je niet bewegen
        } else if (speelBord[pionYLocation][pionXLocation] instanceof Speelstuk) {
            Speelstuk tempSpeelstuk = (Speelstuk) speelBord[pionYLocation][pionXLocation];
            if (tempSpeelstuk.getTeam() == team) {
                if (printInfo) System.out.println("Hier staat je eigen pion");
                return false; //instance of speelstuk, maar eigen team --> dus je mag niet bewegen
//            } else if (speelBord[pionYLocation][pionXLocation] instanceof Vlag) { //check voor de vlag ingebouwd, maar ook hier geldt, vlag van zowel beide teams (nog team specifiek maken)
//                if (printInfo) System.out.println("JE HEBT GEWONNEN, GEFELICITEERD!!!!"); //en de game moet nog eindigen....
//                return true; //instance of speelstuk, niet van je eigen team maar wel een vlag --> dus je mag wel bewegen
            } else {
                return true; //instance of speelstuk, niet van eigen team --> je mag wel bewegen
            }
        } else if (speelBord[pionYLocation][pionXLocation] instanceof Blokkade) {
            if (printInfo) System.out.println("Hier kun je niet doorheen!");
            return false; //instance of blokkade, je mag niet bewegen
        } else {
            return true; //nieuwe positie is leeg, dus je kan wel bewegen.
        }
    }

    //Deze code verplaatst de stukken, maar kan alleen aangeroepen worden nadat de movement check is uitgevoerd
    //Daarom is deze ook private!
    //kijkt eerst of de nieuwe locatie een speelstuk bevat, zo ja, vergelijkt die de values met elkaar MOET NOG TEAM SPECIFIEK MAKEN, NU KAN JE OOK JE EIGEN SPEELSTUK AANVALLEN
    private void movePiece (int pionYLocationNew, int pionXLocationNew, int pionYLocationOld, int pionXLocationOld, Speler speler) {
        if (speelBord[pionYLocationNew][pionXLocationNew] instanceof Speelstuk) {
            Speelstuk enemy = (Speelstuk) speelBord[pionYLocationNew][pionXLocationNew];
            Speelstuk eigenSpeelstuk = (Speelstuk) speelBord[pionYLocationOld][pionXLocationOld];
            System.out.println("-----      ATTACK!      -----" + '\n' + "Je valt aan met " + eigenSpeelstuk.getNaam() + "(" + eigenSpeelstuk.getValue() + ")");

            switch (enemy.getNaam()) {
            case "vlag":
                System.out.println("Je hebt een vlag aangevallen " + '\n' + "----- U heeft gewonnen! -----");
                speelBord[pionYLocationNew][pionXLocationNew] = speelBord[pionYLocationOld][pionXLocationOld];
                speelBord[pionYLocationOld][pionXLocationOld] = null;
                speler.setGewonnen(true);
                break;
            case "bom":
                if (eigenSpeelstuk.getNaam().equals("mineur")) {
                    System.out.println("Je hebt een bom aangevallen " + '\n' + "-----     YOU WIN!!     -----");
                    speelBord[pionYLocationNew][pionXLocationNew] = speelBord[pionYLocationOld][pionXLocationOld];
                } else {
                    System.out.println("Je hebt een bom aangevallen " + '\n' + "-----     YOU LOST!     -----");
                }
                speelBord[pionYLocationOld][pionXLocationOld] = null;
                break;
            case "maarschalk":
                if (eigenSpeelstuk.getNaam().equals("spion")) {
                    System.out.println("Je hebt een " + enemy.getNaam() + " aangevallen " + "(" + enemy.getValue() + ")" + '\n' + "-----     YOU WIN!!     -----");
                    speelBord[pionYLocationNew][pionXLocationNew] = speelBord[pionYLocationOld][pionXLocationOld];
                } else {
                    System.out.println("Je hebt een " + enemy.getNaam() + " aangevallen " + "(" + enemy.getValue() + ")" + '\n' + "-----     YOU LOST!     -----");
                }
                speelBord[pionYLocationOld][pionXLocationOld] = null;
                break;
            default:
                if (eigenSpeelstuk.getValue() > enemy.getValue()) {
                    System.out.println("Je hebt een " + enemy.getNaam() + " aangevallen " + "(" + enemy.getValue() + ")" + '\n' + "-----     YOU WIN!!     -----");
                    speelBord[pionYLocationNew][pionXLocationNew] = speelBord[pionYLocationOld][pionXLocationOld];
                } else if (eigenSpeelstuk.getValue() < enemy.getValue()) {
                    System.out.println("Je hebt een " + enemy.getNaam() + " aangevallen " + "(" + enemy.getValue() + ")" + '\n' + "-----     YOU LOST!     -----");
                } else if (eigenSpeelstuk.getValue() == enemy.getValue()) { //Deze kijkt als hij gelijk is en maakt beide posities leeg
                    System.out.println("Je hebt een " + enemy.getNaam() + " aangevallen " + "(" + enemy.getValue() + ")" + '\n' + "-----    BOTH LOOSE!    -----");
                    speelBord[pionYLocationNew][pionXLocationNew] = null;
                } else {
                    speelBord[pionYLocationNew][pionXLocationNew] = speelBord[pionYLocationOld][pionXLocationOld];
                }
                speelBord[pionYLocationOld][pionXLocationOld] = null;
                break;
            }
        } else {
        speelBord[pionYLocationNew][pionXLocationNew] = speelBord[pionYLocationOld][pionXLocationOld];
        speelBord[pionYLocationOld][pionXLocationOld] = null;
        }
    }



    public boolean FrondEndMove(int pionXLocation, int pionYLocation, String direction, Speler speler) {
        switch (direction) {
            case "u":
                //Check of hij wel in deze richting kan bewegen, zo ja: voer move uit, zo nee: nieuwe input vragen
                if (movementCheck(pionYLocation - 1, pionXLocation, true, speler.getSpelerTeam())) {
                    movePiece(pionYLocation - 1, pionXLocation, pionYLocation, pionXLocation, speler);
                    return true;
                } else {
                    return false;
                }
            case "d":
                if (movementCheck(pionYLocation + 1, pionXLocation, true, speler.getSpelerTeam())) {
                    movePiece(pionYLocation + 1, pionXLocation, pionYLocation, pionXLocation, speler);
                    return true;
                } else {
                    return false;
                }
            case "r":
                if (movementCheck(pionYLocation, pionXLocation + 1, true, speler.getSpelerTeam())) {
                    movePiece(pionYLocation, pionXLocation + 1, pionYLocation, pionXLocation, speler);
                    return true;
                } else {
                    return false;
                }
            case "l":
                if (movementCheck(pionYLocation, pionXLocation - 1, true, speler.getSpelerTeam())) {
                    movePiece(pionYLocation, pionXLocation - 1, pionYLocation, pionXLocation, speler);
                    return true;
                } else {
                    return false;
                }
            default:
                return false;
        }
    }

    //methode om movement te kiezen en te checken

    public boolean moveChooser(int pionYLocation, int pionXLocation, Speler speler) {
        Scanner scanner = new Scanner(System.in);
        String movementDirection = scanner.next();

        //Kijk of de input voldoet aan een van de volgende cases "u,d,r,l"
        switch (movementDirection) {
            case "u":
                //Check of hij wel in deze richting kan bewegen, zo ja: voer move uit, zo nee: nieuwe input vragen
                if (movementCheck(pionYLocation - 1,pionXLocation,true,speler.getSpelerTeam())){
                    movePiece(pionYLocation - 1, pionXLocation, pionYLocation, pionXLocation, speler);
                    return true;
                } else {
                    return false;
                }
            case "d":
                if (movementCheck(pionYLocation + 1,pionXLocation,true,speler.getSpelerTeam())){
                    movePiece(pionYLocation + 1, pionXLocation, pionYLocation, pionXLocation, speler);
                    return true;
                } else {
                    return false;
                }
            case "r":
                if (movementCheck(pionYLocation,pionXLocation + 1,true,speler.getSpelerTeam())){
                    movePiece(pionYLocation, pionXLocation + 1, pionYLocation, pionXLocation, speler);
                    return true;
                } else {
                    return false;
                }
            case "l":
                if (movementCheck(pionYLocation,pionXLocation - 1,true,speler.getSpelerTeam())){
                    movePiece(pionYLocation, pionXLocation - 1, pionYLocation, pionXLocation, speler);
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

    public void bordPrinten(){
        StringBuilder bordstring = new StringBuilder();
        bordstring.append("X: | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |10 | \n"); //deze coordinaten worden geprint boven het bord
        bordstring.append("   -----------------------------------------\n"); // dit is een afscheiding van coordinaten tov gevulde matrix
        bordstring.append("Y: +---+---+---+---+---+---+---+---+---+---+\n");
        for (int y = 0; y < 10; y++) {
            int yCoordinaat = y + 1;//deze y-coordinaat wordt gedefinieerd zodat deze geprint kan worden als coordinatenstelsel
            if (yCoordinaat < 10) {
                bordstring.append(" ");//getallen kleiner dan 10, krijgen extra spatie (voor uitlijning)
            }
            bordstring.append(yCoordinaat + " ");
            for (int x = 0; x < 10; x++) { //deze forloop voegt voor ieder vakje de value van het spelstuk toe of een "o" als het vakje leeg is.
                String spelstukString;
                if (speelBord[y][x] instanceof Speelstuk) {
                    Speelstuk speelstuk = (Speelstuk) speelBord[y][x];
                    if (speelstuk.getValue() < 10) {
                        spelstukString = "| " + speelstuk.getValue() + " "; //een extra spatie toevoegen als de waarde kleiner is dan tien, zodat de uitlijning mooi klopt.
                    } else {
                        spelstukString = "|" + speelstuk.getValue() + " ";
                    }

                } else if (speelBord[y][x] instanceof Blokkade) { //Als er een String wordt gevonden dan is het een blokkade
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

    public String bordPrinten2(){
        StringBuilder bordstring = new StringBuilder();
        bordstring.append("X: | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 |10 | \n"); //deze coordinaten worden geprint boven het bord
        bordstring.append("   -----------------------------------------\n"); // dit is een afscheiding van coordinaten tov gevulde matrix
        bordstring.append("Y: +---+---+---+---+---+---+---+---+---+---+\n");
        for (int y = 0; y < 10; y++) {
            int yCoordinaat = y + 1;//deze y-coordinaat wordt gedefinieerd zodat deze geprint kan worden als coordinatenstelsel
            if (yCoordinaat < 10) {
                bordstring.append(" ");//getallen kleiner dan 10, krijgen extra spatie (voor uitlijning)
            }
            bordstring.append(yCoordinaat + " ");
            for (int x = 0; x < 10; x++) { //deze forloop voegt voor ieder vakje de value van het spelstuk toe of een "o" als het vakje leeg is.
                String spelstukString;
                if (speelBord[y][x] instanceof Speelstuk) {
                    Speelstuk speelstuk = (Speelstuk) speelBord[y][x];
                    if (speelstuk.getValue() < 10) {
                        spelstukString = "| " + speelstuk.getValue() + " "; //een extra spatie toevoegen als de waarde kleiner is dan tien, zodat de uitlijning mooi klopt.
                    } else {
                        spelstukString = "|" + speelstuk.getValue() + " ";
                    }

                } else if (speelBord[y][x] instanceof Blokkade) { //Als er een String wordt gevonden dan is het een blokkade
                    spelstukString = "| x ";
                } else { //Leeg stuk ruimte waar heen gelopen kan worden
                    spelstukString = "|   ";
                }
                bordstring.append(spelstukString);
            }
            bordstring.append("|\n");//Aan het einde komt nog een rechtstreepje en dan een niewline character
            bordstring.append("   +---+---+---+---+---+---+---+---+---+---+\n");
        }


        return bordstring.toString();
    }



    //met onderstaande methode wordt het bord geprint teamspecifiek, je geeft dan het team mee in de methode

    public void bordPrinten(int huidigeTeam){

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
                if (speelBord[y][x] instanceof Speelstuk) {
                    Speelstuk speelstuk = (Speelstuk) speelBord[y][x];
                    if (speelstuk.getTeam()==huidigeTeam){ //als team gelijk is aan huidigeteam --> print de values van speelstuk
                        if (speelstuk.getValue() < 10) {
                            spelstukString = "| " + speelstuk.getValue() + " "; //een extra spatie toevoegen als de waarde kleiner is dan tien, zodat de uitlijning mooi klopt.
                        } else {
                            spelstukString = "|" + speelstuk.getValue() + " ";
                        }
                    }else {
                        spelstukString = "|xxx"; //print xx voor speelstuk van tegenstander (andere team)
                    }
                } else if (speelBord[y][x] instanceof Blokkade) { //Als er een String wordt gevonden dan is het een blokkade
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



    //getters and setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Object[][] getSpeelBord() {
        return speelBord;
    }

}



class Blokkade{}


