package nl.koffiepot.Stratego.model.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SpeelStukData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int team;
    private int value;
    private int Xcoordinate;
    private int Ycoordinate;
    private String spelNaam;

    public SpeelStukData() {
    }

    public long getId() {
        return id;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getXcoordinate() {
        return Xcoordinate;
    }

    public void setXcoordinate(int xcoordinate) {
        Xcoordinate = xcoordinate;
    }

    public int getYcoordinate() {
        return Ycoordinate;
    }

    public void setYcoordinate(int ycoordinate) {
        Ycoordinate = ycoordinate;
    }

    public String getSpelNaam() {
        return spelNaam;
    }

    public void setSpelNaam(String spelNaam) {
        this.spelNaam = spelNaam;
    }
}
