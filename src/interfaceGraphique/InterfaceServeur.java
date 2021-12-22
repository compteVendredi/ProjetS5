package interfaceGraphique;

import java.io.IOException;

import serveur.BDD;
import serveur.Serveur;

public class InterfaceServeur {

	public static void lancer() {
		System.out.println("--------Serveur--------");
		BDD bdd = new BDD("root", "", "jdbc:mysql://localhost:3306/s5");
		Serveur serveur = new Serveur(9999, bdd);
		serveur.demarrer();
	}

}