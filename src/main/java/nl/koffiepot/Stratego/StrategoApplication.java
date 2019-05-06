package nl.koffiepot.Stratego;

import nl.koffiepot.Stratego.model.Bord;
import nl.koffiepot.Stratego.model.Speler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class StrategoApplication {

	public static void main(String[] args) {
		SpringApplication.run(StrategoApplication.class, args);



		Scanner scanner = new Scanner(System.in);
		System.out.print("Voer naam speler 1 in:");
		Speler speler1 = new Speler(scanner.nextLine(),0);
		System.out.print("Voer naam speler 2 in:");
		Speler speler2 = new Speler(scanner.nextLine(),1);

		System.out.println(speler1);
		System.out.println(speler2);

		Bord spelerBord = new Bord();

		spelerBord.bordPrinten();
		spelerBord.bordPrinten(0);

		speler1.beurt(spelerBord);
		spelerBord.bordPrinten(0);


	}

}
