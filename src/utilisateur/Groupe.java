package utilisateur;

import java.util.ArrayList;
import java.util.List;

public class Groupe {
	//rajouter le role
	private String idGroupe;
	private List<Utilisateur> userList = new ArrayList<>();
	
	public Groupe(String idGroupe) {
		this.idGroupe = idGroupe;
	}
	public String getIdGroupe() {
		return idGroupe;
	}
	public int ajouterUtilisateur(Utilisateur user) {
		userList.add(user);
		return 1;
	}
}
