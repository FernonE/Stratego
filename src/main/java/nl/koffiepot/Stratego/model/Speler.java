package nl.koffiepot.Stratego.model;

import nl.koffiepot.Stratego.model.Speelstukken.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Speler {
    private long id;
    private String spelerNaam;
    private int spelerWins;
    private int spelerLosses;
    private int spelerTeam;
    private boolean gewonnen;
    private Scanner scanner = new Scanner(System.in);

    //Constructor
    public Speler(String spelerNaam,int spelerTeam) {
        this.spelerNaam = spelerNaam;
        this.spelerTeam = spelerTeam;
    }

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
            int ind = answer.indexOf(','); // ik heb deze in de try block gezet. Als iemand ipv coordinaten een string invoert dan wordt deze exception ook gevangen.
            String first = answer.substring(0,ind);
            String second = answer.substring(ind+1);
            coords[0] = Integer.parseInt(first)-1;
            coords[1] = Integer.parseInt(second)-1;
        } catch (Exception e){
            coords[0] = -1;
            coords[1] = -1;
        }
        return coords;
    }

    public void beurt(Bord bord) {

        //in een do while not correct loop zetten
        boolean passed = false;
        //deze wordt aangepast
        int[] selectCoords;
        do{
            passed = true; // eerst maar eens even de check op true zetten.
            System.out.println("Welke speelstuk wil je bewegen? Voer coordinaten in als volgt: x,y"); //Deze vraag hier neergezet
            selectCoords = this.selectCoords(); //Vraag om user input om te bepalen welke speelstuk hij/zij wil verzetten. {-1,-1} als het niet goed is, {x, y} als het wel goed is
            if (selectCoords[0] == -1) { // eerst kijken of de user wel goede input heeft gegeven
                System.out.println("Er ging iets mis met het invoeren, probeer het nog een keer");
                passed = false;
                continue; //als het misgaat, springt java vanaf hier meteen naar de while(!passed) en slaat de volgende check dus over. Aangezien dat niet gaat :)
            }
            if(!bord.checkValidPiece(selectCoords[1], selectCoords[0],this.spelerTeam)){ //daarna kijken of het wel een correcte speelstuk is
                //prints wanneer iets verkeerd gekozen is gebeurt al in bord.
                passed = false;
            }
        } while(!passed); // blijf vragen totdat user input goed is en het een correcte speelstuk is.


        passed = false; //passed weer op false zetten.
        do{
            System.out.println("welke richting wil je deze op bewegen?");
            System.out.println("selecteer uit up(u), down(d), left(l) of right(r)");
            //bord.moveChooser vraag al om user input welke richting je op wilt, als dit mogelijk is gebeurt dit ook meteen
            //is de move ook uitgevoerd, dan komt true eruit, en anders false
            if (bord.moveChooser(selectCoords[1], selectCoords[0],this)){ //in bord.moveChooser, wordt al gevraagd voor user input in welke richting je wilt bewegen, en wordt dit gedaan waneer het kan.
                passed = true;
            }
        } while (!passed);
    }

    // deze methode vraagt de user om omstebeurt een speelstuk op het bord neer te zetten door eerst te vragen op welk coordinaat en daarna welke speelstuk.
    public void zetTeam(Bord bord){
        List<Speelstuk> teamlijst = createteam(this.spelerTeam);
        int[] Coords;
        //Deze whileloop blijft draaien zolang de user nog speelstukken in de lijst heeft staan.
        while(!teamlijst.isEmpty()){
            bord.bordPrinten(this.spelerTeam);
            System.out.println("Waar wil je het volgende speelstuk neerzetten? voer in als x,y");
            Coords = this.selectCoords(); // parse de gegeven string naar twee ints
            if (Coords[0] == -1){
                System.out.println("Er ging iets mis met het invoeren, probeer het nog een keer");
            } else if (bord.getSpeelBord()[Coords[1]][Coords[0]]!=null){ //als het niet null is, dan staat er al iets anders
                System.out.println("hier staat als iets");
            } else { // Hier is de keuze van de coordinaat wel goed
                while(true) {
                    System.out.println("Welk speelstuk wil je hier neerzetten");
                    List[] options = this.speelstukSelectOptions(teamlijst); //hier komen 2 lijstjes uit (vandaar List[]), namelijk een List<String> met alle mogelijke Speelstukken en een List<Integer> met de values van deze speelstukken.
                    List<String> printedNames = options[0]; //hier worden die lijsten gescheiden
                    List<String> speelStuknamen = options[1];
                    System.out.println("Je kan kiezen uit: \n " + printedNames); //de List<String> wordt gebruikt om te kijken of de keuze die gemaakt is door de user correct is.
                    String answer = scanner.nextLine();
                    if (speelStuknamen.contains(answer)){
                        for(Speelstuk speelstuk : teamlijst){ //en over alle nog te plaatste speelstukken wordt geloopt...
                            if (speelstuk.getNaam().equals(answer)){ //... Totdat de er een speelstuk is gevonden met dezelfde waarde als die gekozen is (vandaar 2 lijsten)
                                bord.setPiece(Coords[1],Coords[0],speelstuk); // dit speelstuk wordt dan op het bord gezet.
                                teamlijst.remove(speelstuk); //en verwijderd uit de lijst met nog te plaatsen stukken.
                                break;
                            }
                        }
                        break;
                    } else {
                        System.out.println("je hebt een verkeerde keuze gemaakt");
                    }
                }
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

    private List<Speelstuk> createteam(int team) {
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

    //Getters and Setters (zijn ze allemaal nodig)?
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSpeler() {
        return spelerNaam;
    }

    public void setSpeler(String speler) {
        this.spelerNaam = speler;
    }

    public int getSpelerWins() {
        return spelerWins;
    }


    public void setSpelerWins(int spelerWins) {
        this.spelerWins = spelerWins;
    }

    public int getSpelerLosses() {
        return spelerLosses;
    }

    public void setSpelerLosses(int spelerLosses) {
        this.spelerLosses = spelerLosses;
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

    @Override
    public String toString() {
        return "Speler{" +
                "Naam='" + spelerNaam + '\'' +
                ", Team=" + (spelerTeam==0 ? "Red":"Blue") +
                '}';
    }
}
