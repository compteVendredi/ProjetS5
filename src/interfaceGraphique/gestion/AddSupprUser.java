package interfaceGraphique.gestion;

import java.awt.event.ActionEvent;

import serveur.BDD;

public class AddSupprUser extends PanelAddSuprr {
	public AddSupprUser(String idAffiche, String idParent, BDD accesGestion) {
		super(idAffiche, idParent, accesGestion);
	}
	
	protected void btnDelete(ActionEvent event) {
		accesGestion.supprimerUtilisateurGroupe(idAffiche, idParent);
	}
}
