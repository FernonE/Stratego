package nl.koffiepot.Stratego.model;

import nl.koffiepot.Stratego.model.Speelstukken.Majoor;

public class Speelstuk {
    long id;
    private int value;
    private String speelstukNaam;
    private int team;
    private int xCoordinaat;
    private int yCoordinaat;



    public Speelstuk(int team,int value, String naam){
        this.team = team;
        this.value = value;
        this.speelstukNaam = naam;
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

    public String getSpeelstukNaam() {
        return speelstukNaam;
    }

    public void setSpeelstukNaam(String speelstukNaam) {
        this.speelstukNaam = speelstukNaam;
    }

    public int getTeam() {
        return team;
    }

    public int getxCoordinaat() {
        return xCoordinaat;
    }

    public void setxCoordinaat(int xCoordinaat) {
        this.xCoordinaat = xCoordinaat;
    }

    public int getyCoordinaat() {
        return yCoordinaat;
    }

    public void setyCoordinaat(int yCoordinaat) {
        this.yCoordinaat = yCoordinaat;
    }
}
