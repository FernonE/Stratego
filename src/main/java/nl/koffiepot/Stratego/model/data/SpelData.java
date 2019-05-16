package nl.koffiepot.Stratego.model.data;

import javax.persistence.*;

@Entity
public class SpelData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String spelNaam;

    @OneToMany
    private SpelerData spelerData;

    public SpelData() {
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
}
