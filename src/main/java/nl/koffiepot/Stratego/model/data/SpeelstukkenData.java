package nl.koffiepot.Stratego.model.data;

import javax.persistence.*;

@Entity
@Table (name="speelstukNaam")
public class SpeelstukkenData {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private int value;
    private String speelstukNaam;
    private int team;
    private int xCoordinaat;
    private int yCoordinaat;

    @ManyToOne
    @JoinColumn(name="spelerNaam")
    private SpelerData speler;


    public SpeelstukkenData() {
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

    public void setTeam(int team) {
        this.team = team;
    }
}
