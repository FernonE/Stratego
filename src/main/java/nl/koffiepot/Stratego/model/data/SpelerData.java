package nl.koffiepot.Stratego.model.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SpelerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String spelerNaam;
    private int spelerWins;
    private int spelerLosses;


    public SpelerData(){}


    public long getId() {
        return id;
    }

    public String getSpelerNaam() {
        return spelerNaam;
    }

    public void setSpelerNaam(String speler) {
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
}
