package nl.koffiepot.Stratego.model;

import nl.koffiepot.Stratego.model.Speelstukken.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Speler {
    //fields
    private String spelerNaam;
    private int spelerTeam;
    private boolean gewonnen;
    private boolean saveGame;

    //constructor
    public Speler(String spelerNaam, int spelerTeam){
        this.spelerNaam = spelerNaam;
        this.spelerTeam = spelerTeam;
    }



    private Scanner scanner = new Scanner(System.in);

    //Methods
    private int[] selectCoords(){
        /*
        -vragen om user input
        -user input checken op validity
            -probeer te parsen naar 2 coordinaten --> int[]{x,y}
            -als het goed is, return int[]{x,y}
            -als het niet goed is, return int[]{-1,-1}
         */
        String answer = scanner.nextLine();
        int[] coords = new int[]{0,0};
        try{
            if (!answer.equals("save")) {
                int ind = answer.indexOf(','); // ik heb deze in de try block gezet. Als iemand ipv coordinaten een string invoert dan wordt deze exception ook gevangen.
                String first = answer.substring(0, ind);
                String second = answer.substring(ind + 1);
                coords[0] = Integer.parseInt(first) - 1;
                coords[1] = Integer.parseInt(second) - 1;
                if (coords[0] < 0 || coords[0] > 9 || coords[1] < 0 || coords[1] > 9) {
                    throw new IndexOutOfBoundsException();
                }
            }  else {
                coords[0] = -2;
                coords[1] = -2;
            }
        } catch (Exception e){
            coords[0] = -1;
            coords[1] = -1;
        }
        return coords;
    }

    private int[] selectCoordsPlacing(int spelerTeam){
        String answer = scanner.nextLine();
        int[] coords = new int[]{0,0};
        try{
            int ind = answer.indexOf(','); // ik heb deze in de try block gezet. Als iemand ipv coordinaten een string invoert dan wordt deze exception ook gevangen.
            String first = answer.substring(0,ind);
            String second = answer.substring(ind+1);
            coords[0] = Integer.parseInt(first)-1;
            coords[1] = Integer.parseInt(second)-1;
            if (coords[0] < 0 || coords[0] > 9 || coords[1] < 0 || coords[1] > 9) {
                System.out.println("Dit zit buiten het veld");
                throw new IndexOutOfBoundsException();
            }
            if (spelerTeam == 0 && coords[1] > 3 ) {
                System.out.println("Je kunt geen stukken plaatsen buiten je veld (y moet tussen 1 en 4 liggen)");
                throw new NotYourPartOfBoardException();
            }
            if (spelerTeam == 1 && coords[1] < 6) {
                System.out.println("Je kunt geen stukken plaatsen buiten je veld (y moet tussen 7 en 10 liggen)");
                throw new NotYourPartOfBoardException();
            }
        } catch (Exception e){
            coords[0] = -1;
            coords[1] = -1;
        }
        return coords;
    }


    public void beurt(Bord bord) {

        //in een do while not correct loop zetten
        boolean passed;
        boolean happy; //Als je blij bent met je keuze ga je door anders keer je terug naar kiezen
        //deze wordt aangepast
        int[] selectCoords;
        LOOP: do {
            do {
                System.out.println(this.spelerNaam + " is aan zet. Welk speelstuk wil je bewegen? Voer coordinaten in als volgt: x,y"); //Deze vraag hier neergezet
                selectCoords = this.selectCoords(); //Vraag om user input om te bepalen welke speelstuk hij/zij wil verzetten. {-1,-1} als het niet goed is, {x, y} als het wel goed is
                if (selectCoords[0] == -1) { // eerst kijken of de user wel goede input heeft gegeven
                    passed = false;
                } else if (selectCoords[0]==-2){
                    saveGame = true;
                    break LOOP;
                } else {
                    passed = bord.checkValidPiece(selectCoords[1], selectCoords[0], this.spelerTeam); //returns true if validpiece, but false if it is not valid
                }
            } while (!passed); // blijf vragen totdat user input goed is en het een correcte speelstuk is.


            do {
                System.out.println("U heeft een " + ((Speelstuk) bord.speelBord[selectCoords[1]][selectCoords[0]]).getNaam() +"("+((Speelstuk) bord.speelBord[selectCoords[1]][selectCoords[0]]).getValue()+")" + " gekozen. Klopt dit? Y/N");
                happy = scanner.nextLine().equalsIgnoreCase("y"); //Als je blij bent met je keuze wordt happy true en dan breakt hij de loop
                if (happy) {
                    System.out.println("welke richting wil je deze op bewegen?");
                    System.out.println("selecteer uit up(u), down(d), left(l) of right(r)");
                    passed = bord.moveChooser(selectCoords[1], selectCoords[0], this); //in bord.moveChooser, wordt al gevraagd voor user input in welke richting je wilt bewegen, en wordt dit gedaan waneer het kan.
                }
            } while (!passed);
        } while (!happy);
        if (!saveGame) {
            bord.bordPrinten(this.spelerTeam);
            System.out.println("Press enter to continue");
            scanner.nextLine();
        }
    }


    public void frondEndBeurt(int xcoordinate, int ycoordinate, String direction, Bord bord) {
        boolean validpiece = bord.checkValidPiece(ycoordinate, xcoordinate, this.spelerTeam);
        if (validpiece){
            bord.FrondEndMove(xcoordinate, ycoordinate, direction, this);
        }
    }

    // deze methode vraagt de user om omstebeurt een speelstuk op het bord neer te zetten door eerst te vragen op welk coordinaat en daarna welke speelstuk.
    public void zetTeam(Bord bord){
        List<Speelstuk> teamlijst = createteam(this.spelerTeam);
        int[] Coords;
        //Deze whileloop blijft draaien zolang de user nog speelstukken in de lijst heeft staan.
        while(!teamlijst.isEmpty()){
            bord.bordPrinten(this.spelerTeam);
            System.out.println("Waar wil je het volgende speelstuk neerzetten? voer in als x,y");
            Coords = this.selectCoordsPlacing(this.spelerTeam); // parse de gegeven string naar twee ints
            if (Coords[0] != -1 && bord.getSpeelBord()[Coords[1]][Coords[0]]==null) { //als het niet null is, dan staat er al iets anders
                // Hier is de keuze van de coordinaat wel goed
                while (true) {
                    System.out.println("Welk speelstuk wil je hier neerzetten");
                    List[] options = this.speelstukSelectOptions(teamlijst); //hier komen 2 lijstjes uit (vandaar List[]), namelijk een List<String> met alle mogelijke Speelstukken en een List<Integer> met de values van deze speelstukken.
                    List<String> printedNames = options[0]; //hier worden die lijsten gescheiden
                    List<String> speelStuknamen = options[1];
                    System.out.println("Je kan kiezen uit: \n " + printedNames); //de List<String> wordt gebruikt om te kijken of de keuze die gemaakt is door de user correct is.
                    String answer = scanner.nextLine();
                    if (speelStuknamen.contains(answer)) {
                        for (Speelstuk speelstuk : teamlijst) { //en over alle nog te plaatste speelstukken wordt geloopt...
                            if (speelstuk.getNaam().equals(answer)) { //... Totdat de er een speelstuk is gevonden met dezelfde waarde als die gekozen is (vandaar 2 lijsten)
                                bord.setPiece(Coords[1], Coords[0], speelstuk); // dit speelstuk wordt dan op het bord gezet.
                                teamlijst.remove(speelstuk); //en verwijderd uit de lijst met nog te plaatsen stukken.
                                break;
                            }
                        }
                        break;
                    } else {
                        System.out.println("je hebt een verkeerde keuze gemaakt");
                    }
                }
            } else if (Coords[0] != -1 && bord.getSpeelBord()[Coords[1]][Coords[0]]!=null) {
                System.out.println("hier staat als iets");
            }
        }
    }

    private List[] speelstukSelectOptions(List<Speelstuk> teamlijst) {
        int[] counters = new int[12]; //een lijst van 12 counters, waarbij de index in deze lijst overeenkomt met de value van een speelstuk
        teamlijst.forEach(t -> counters[t.getValue()-1]++); //loop over alle speelstukken in teamlijst en increment de correcte counter aan de hand van de value.
        List<String> printedNames = new ArrayList<>();
        List<String> speelStukNamen = new ArrayList<>();
        if (counters[0]>0)  { printedNames.add(counters[0]  + "X Spion"     ); speelStukNamen.add("spion") ;} //als de correcte index groter is dan nul, dan houdt dat in dat deze speelstuk nog in de lijst zit. Dus dan dit aan de keuzes toevoegen.
        if (counters[1]>0)  { printedNames.add(counters[1]  + "X Verkenner" ); speelStukNamen.add("verkenner") ;}
        if (counters[2]>0)  { printedNames.add(counters[2]  + "X Mineur"    ); speelStukNamen.add("mineur") ;}
        if (counters[3]>0)  { printedNames.add(counters[3]  + "X Sergeant"  ); speelStukNamen.add("sergeant") ;}
        if (counters[4]>0)  { printedNames.add(counters[4]  + "X Luitenant" ); speelStukNamen.add("luitenant") ;}
        if (counters[5]>0)  { printedNames.add(counters[5]  + "X Kapitein"  ); speelStukNamen.add("kapitein") ;}
        if (counters[6]>0)  { printedNames.add(counters[6]  + "X Majoor"    ); speelStukNamen.add("majoor") ;}
        if (counters[7]>0)  { printedNames.add(counters[7]  + "X Kolonel"   ); speelStukNamen.add("kolonel") ;}
        if (counters[8]>0)  { printedNames.add(counters[8]  + "X Generaal"  ); speelStukNamen.add("generaal") ;}
        if (counters[9]>0)  { printedNames.add(counters[9]  + "X Maarschalk"); speelStukNamen.add("maarschalk");}
        if (counters[10]>0) { printedNames.add(counters[10] + "X Bom"       ); speelStukNamen.add("bom");}
        if (counters[11]>0) { printedNames.add(counters[11] + "X Vlag"      ); speelStukNamen.add("vlag");}
        return new List[]{printedNames, speelStukNamen};
    }

    public static List<Speelstuk> createteam(int team) {
        List<Speelstuk> Teamstukken = new ArrayList<>();
        //Elk stuk krijgt een apart object en daarom worden 40 stukken gemaakt hieronder. Deze krijgen allemaal
        //het teamnummer mee zodat er onderscheid gemaakt kan worden.
        for (int i = 0; i < 6; i++) Teamstukken.add(new Bom(team));
        Teamstukken.add(new Maarschalk(team));
        Teamstukken.add(new Generaal(team));
        for (int i = 0; i < 2; i++) Teamstukken.add(new Kolonel(team));
        for (int i = 0; i < 3; i++) Teamstukken.add(new Majoor(team));
        for (int i = 0; i < 4; i++) Teamstukken.add(new Kapitein(team));
        for (int i = 0; i < 4; i++) Teamstukken.add(new Luitenant(team));
        for (int i = 0; i < 4; i++) Teamstukken.add(new Sergeant(team));
        for (int i = 0; i < 5; i++) Teamstukken.add(new Mineur(team));
        for (int i = 0; i < 8; i++) Teamstukken.add(new Verkenner(team));
        Teamstukken.add(new Spion(team));
        Teamstukken.add(new Vlag(team));
        return Teamstukken;
    }

    //getters & setters
    public String getSpelerNaam() {
        return spelerNaam;
    }

    public void setSpelerNaam(String speler) {
        this.spelerNaam = speler;
    }

   
    public int getSpelerTeam() {
        return spelerTeam;
    }

    public void setSpelerTeam(int spelerteam) {
        this.spelerTeam = spelerteam;
    }

    public boolean isGewonnen() {
        return gewonnen;
    }

    public void setGewonnen(boolean gewonnen) {
        this.gewonnen = gewonnen;
    }

    public boolean isSaveGame() {
        return saveGame;
    }

    @Override
    public String toString() {
        return "Speler{" +
                "spelerNaam='" + spelerNaam + '\'' +
                ", spelerTeam=" + spelerTeam +
                '}';
    }

}
