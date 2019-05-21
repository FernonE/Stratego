package nl.koffiepot.Stratego.model;

public class Speelstuk {
    long id;
    private int value;
    private String naam;
    private int team;


    public Speelstuk(int team,int value, String naam){
        this.team = team;
        this.value = value;
        this.naam = naam;
    }


    //Methodes voor het aanmaken
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public int getTeam() {
        return team;
    }
}
