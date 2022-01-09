package interfaceGraphique.gestion;

import java.awt.event.ActionEvent;

import serveur.BDD;

public class AddSupprUser extends PanelAddSuprr {
	public AddSupprUser(String idUser, String idGroup, BDD accesGestion) {
		super(idGroup, idUser, accesGestion);
	}
	
	protected void btnDelete(ActionEvent event) {
		accesGestion.supprimerUserGroupe(idGroup, idUser);
	}
}
