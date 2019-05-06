package nl.koffiepot.Stratego.model;

import java.util.Scanner;

public class Speler {
    private long id;
    private String spelerNaam;
    private int spelerWins;
    private int spelerLosses;
    private int spelerTeam;
    private boolean gewonnen;
    private Scanner scanner = new Scanner(System.in);
    private int[] selectCoords = {0,0}; //deze wordt aangepast


    private int[] selectPiece(){
        /*
        -vragen om user input
        -user input checken op validity
            -probeer te parsen naar 2 coordinaten --> int[]{x,y}
            -als het goed is, return int[]{x,y}
            -als het niet goed is, return int[]{-1,-1}
         */
        System.out.println("Welke speelstuk wil je bewegen? Voer coordinaten in als volgt: x,y");
        String answer = scanner.nextLine();
        int ind = answer.indexOf(',');
        String first = answer.substring(0,ind);
        String second = answer.substring(ind+1);
        int[] coords = new int[]{0,0};
        try{
            coords[0] = Integer.parseInt(first)-1;
            coords[1] = Integer.parseInt(second)-1;
        } catch (Exception e){
            coords[0] = -1;
            coords[1] = -1;
        }
        return coords;
    }
    /*
    random comment
     */
    public void beurt(Bord bord) {

        //in een do while not correct loop zetten
        boolean passed = false;
        do{
            passed = true; // eerst maar eens even de check op true zetten.
            selectCoords = this.selectPiece(); //Vraag om user input om te bepalen welke speelstuk hij/zij wil verzetten. {-1,-1} als het niet goed is, {x, y} als het wel goed is
            if (selectCoords[0] == -1) { // eerst kijken of de user wel goede input heeft gegeven
                System.out.println("Er ging iets mis met het invoeren, probeer het nog een keer");
                passed = false;
                continue; //als het misgaat, springt java vanaf hier meteen naar de while(!passed) en slaat de volgende check dus over. Aangezien dat niet gaat :)
            }
            if(!bord.checkValidPiece(selectCoords[1],selectCoords[0],this.spelerTeam)){ //daarna kijken of het wel een correcte speelstuk is
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
            if (bord.moveChooser(selectCoords[1],selectCoords[0],this)){ //in bord.moveChooser, wordt al gevraagd voor user input in welke richting je wilt bewegen, en wordt dit gedaan waneer het kan.
                passed = true;
            }
        } while (!passed);
    }


    public Speler(String spelerNaam,int spelerTeam) {
        this.spelerNaam = spelerNaam;
        this.spelerTeam = spelerTeam;
    }

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
