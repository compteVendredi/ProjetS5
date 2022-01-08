package interfaceGraphique;

import interfaceGraphique.gui.ConnexionBDD;


/**
 * interfaceServeur
 *
 */

public class InterfaceServeur {

	/**
	 * Permet de lancer l'interface serveur
	 * @param args
	 */
	
	public static void main(String args[]) throws Exception {
		ConnexionBDD connexion = new ConnexionBDD();
		connexion.setVisible(true);
	}
}