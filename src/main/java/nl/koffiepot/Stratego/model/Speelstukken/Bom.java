package nl.koffiepot.Stratego.model.Speelstukken;

import nl.koffiepot.Stratego.model.Speelstuk;


public class Bom extends Speelstuk {
    public Bom(int team){
        super(team,11, "bom");
    }


//    public void getBomImage() {
//        try{
//            BufferedImage image = ImageIO.read(new File("bin/Bom.jpg"));
//        }
//        catch (IOException e){
//            System.out.println("something went wrong: "+e.getMessage());
//        }
//    }
}
