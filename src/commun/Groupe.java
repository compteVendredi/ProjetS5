package commun;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un groupe
 *
 */

public class Groupe {
	private String idGroupe;//Note idGroupe = role du groupe
	private List<Utilisateur> userList = new ArrayList<>();
	
	/**
	 * Créer un groupe
	 * @param idGroupe
	 */
	public Groupe(String idGroupe) {
		this.idGroupe = idGroupe;
	}
	
	/**
	 * Récupère l'id/le rôle du groupe
	 * @return idGroupe
	 */
	public String getIdGroupe() {
		return idGroupe;
	}
	
	/**
	 * Ajoute un utilisateur au groupe
	 * @param user
	 * @return 1 
	 */
	public int ajouterUtilisateur(Utilisateur user) {
		userList.add(user);
		return 1;
	}
}
