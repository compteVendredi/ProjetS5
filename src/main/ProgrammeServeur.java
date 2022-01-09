package main;

import interfaceGraphique.connexion.ConnexionBDD;


/**
 * interfaceServeur
 *
 */

public class ProgrammeServeur {

	/**
	 * Permet de lancer l'interface serveur
	 * @param args
	 */
	
	public static void main(String args[]) throws Exception {
		ConnexionBDD connexion = new ConnexionBDD();
		connexion.setVisible(true);
	}
}