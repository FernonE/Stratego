package nl.koffiepot.Stratego.model.data;

import nl.koffiepot.Stratego.model.Blokkade;
import nl.koffiepot.Stratego.model.Bord;
import nl.koffiepot.Stratego.model.Speelstuk;
import nl.koffiepot.Stratego.model.Speelstukken.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
public class BordData {

    //variables
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String naam = "default";
    private SpeelVlak[][] speelBord = new SpeelVlak[10][10];
    //private Object[][] speelBord = new Object[10][10];


    public BordData() {
        Blokkade blokkade = new Blokkade();
        List<Speelstuk> team1 = createteam(0); //De tijdelijk functie om een team aan te maken aante roepen
        List<Speelstuk> team2 = createteam(1); //
        Random rand = new Random();
        for (int y = 0; y < 4; y++) { //het bord vullen
            for (int x = 0; x < 10; x++) {
                int ind = rand.nextInt(team1.size());
                speelBord[y][x] = team1.get(ind);
                team1.remove(ind);

                ind = rand.nextInt(team2.size()); //dit kan gelijk voor team 2, de x coordinaat wordt alleen met 6 verhoogd.
                speelBord[y + 6][x] = team2.get(ind);
                team2.remove(ind);
            }
        }
        //hardcoded blokkades
        speelBord[4][2] = blokkade; //coordinaten 5,3
        speelBord[4][3] = blokkade; //coordinaten 5,4
        speelBord[5][2] = blokkade;
        speelBord[5][3] = blokkade;
        speelBord[4][6] = blokkade;
        speelBord[4][7] = blokkade;
        speelBord[5][6] = blokkade;
        speelBord[5][7] = blokkade;
    }

    //Methode voor het maken van 40 speelstukken
    public List<Speelstuk> createteam(int team) {
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



    public long getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Object[][] getSpeelBord() {
        return speelBord;
    }

    public void setSpeelBord(SpeelVlak[][] speelBord) {
        this.speelBord = speelBord;
    }
}
