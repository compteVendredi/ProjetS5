package interfaceGraphique;

import utilisateur.Utilisateur;

public class InterfaceUtilisateur {

	public static void main(String args[]) {
		System.out.println("----------Utiliasteur-----------");
		Utilisateur utilisateur = new Utilisateur("Dpt01", "aaaaaaaaaaaaaaaaaaaabb", "", "");
		if (utilisateur.seConnecter("localhost", 9999) == 0) {
			System.out.println(utilisateur.getPrenom());
			System.out.println(utilisateur.getNom());
			utilisateur.seDeconnecter();
		}
		
	}

}