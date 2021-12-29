package interfaceGraphique;

import java.util.concurrent.TimeUnit;

import serveur.BDD;
import serveur.Serveur;

public class InterfaceServeur {

	public static void main(String args[]) {
		System.out.println("--------Serveur--------");
		BDD bdd = new BDD("root", "", "jdbc:mysql://localhost:3306/s5");
		Serveur serveur = new Serveur(9999, bdd);
		serveur.demarrer();

		try {
			TimeUnit.SECONDS.sleep(100);
		} catch (Exception e) {
			e.printStackTrace();
		}

		serveur.arreter();
	}

}