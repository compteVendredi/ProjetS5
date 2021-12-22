package interfaceGraphique;

import utilisateur.Utilisateur;

public class InterfaceUtilisateur {

	public static void lancer() {
		System.out.println("----------Utiliasteur-----------");
		Utilisateur utilisateur = new Utilisateur("Dpt01", "aaaaaaaaaaaaaaaaaaaabb", "", "");
		if(utilisateur.seConnecter("localhost", 9999) == 0) {
			utilisateur.seDeconnecter();
		}
	}

}