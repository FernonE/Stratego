package nl.koffiepot.Stratego.model.data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class SpelData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String spelNaam;
    private int currentTurn;

    @OneToMany
    private Set<SpelerData> spelerDataSet = new HashSet<>();

    public SpelData(String spelnaam) {
        this.spelNaam = spelnaam;
    }

    public SpelData() {
    }

    public void addSpelerData(SpelerData spelerData) {
        this.spelerDataSet.add(spelerData);
    }
    public void saveData(String name) {
        this.spelNaam = name;
    }

    public String loadData() {
        return this.spelNaam;
    }

    public long getId() {
        return id;
    }

    public String getSpelNaam() {
        return spelNaam;
    }

    public void setSpelNaam(String spelNaam) {
        this.spelNaam = spelNaam;
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public void setCurrentTurn(int currentTurn) {
        this.currentTurn = currentTurn;
    }
}
