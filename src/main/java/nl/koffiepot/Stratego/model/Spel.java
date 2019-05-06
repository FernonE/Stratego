
package nl.koffiepot.Stratego.model;

public class Spel {
    private long id;

    private String name;

    public static void main(String[] args) {
        //RICK: dit heb ik even gedaan om de bord constructor en de toString te testen in Bord.
        Bord bord = new Bord();
        bord.bordPrinten();
        bord.bordPrinten(1);
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
