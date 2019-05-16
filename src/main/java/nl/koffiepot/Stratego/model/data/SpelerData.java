package nl.koffiepot.Stratego.model.data;

import javax.persistence.*;

@Entity
public class SpelerData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String spelerNaam;
    private int spelerWins;
    private int spelerLosses;

    @OneToMany
    private SpeelStukData speelStukData;

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

    @Override
    public String toString() {
        return "SpelerData{" +
                "spelerNaam='" + spelerNaam + '\'' +
                ", spelerWins=" + spelerWins +
                ", spelerLosses=" + spelerLosses +
                '}';
    }
}
