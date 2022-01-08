package interfaceGraphique.gui;

import java.awt.event.ActionEvent;

import interfaceGraphique.gui.gestion.ServeurFrame;

import serveur.BDD;
import serveur.Serveur;


public class ConnexionBDD extends Connexion {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1453012910886660784L;

	public ConnexionBDD() throws Exception {
		super();
	}

	protected void btnConnexionListener(ActionEvent event) {
		String userName, password;
		char tempPassword[];
		ServeurFrame serveurFrame;
		
		userName = idField.getText();
		tempPassword = passwordField.getPassword();
		password = new String(tempPassword);
		BDD accesGestion = new BDD(userName, password, "jdbc:mysql://localhost:3306/s5");
		if (accesGestion.seConnecter() == 0) {
			BDD accesServeur = new BDD(userName, password, "jdbc:mysql://localhost:3306/s5");
			Serveur serveur = new Serveur(9999, accesServeur);
			if(serveur.demarrer() == 0) {
				System.out.println("Serveur demarré");
				serveurFrame = new ServeurFrame(serveur, accesGestion);
				serveurFrame.setVisible(true);
			}
		}
		ConnexionBDD.this.dispose();
	}
}
