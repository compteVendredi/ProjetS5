package interfaceGraphique;

import interfaceGraphique.gui.Connexion;

/**
 * interfaceUtiliateur
 *
 */

public class InterfaceUtilisateur {

	/**
	 * Permet de lancer l'interface (graphique) utilisateur
	 * @param args
	 * @throws Exception
	 */
	
	public static void main(String args[]) throws Exception {
		Connexion connexion = new Connexion();
		connexion.setVisible(true);
	}
}
