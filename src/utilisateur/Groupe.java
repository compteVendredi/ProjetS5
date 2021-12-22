package utilisateur;

import java.util.HashSet;
import java.util.Set;

import utilisateur.Role;
import utilisateur.Utilisateur;

public class Groupe {
	/**
	 * <pre>
	 *           0..*          0..*
	 * Groupe ------------------------- Utilisateur
	 *           groupes        &lt;       utilisateurs
	 * </pre>
	 */
	private Set<Utilisateur> utilisateurs;
	private Role role;

	public Groupe() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}
	
	public Set<Utilisateur> getUtilisateurs() {
		if (this.utilisateurs == null) {
			this.utilisateurs = new HashSet<Utilisateur>();
		}
		return this.utilisateurs;
	}

	public void setRole(Role value) {
		this.role = value;
	}

	public Role getRole() {
		return this.role;
	}

	public int creerTicket(String message) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public int ajouterUtilisateur(Utilisateur utilisateur) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public int supprimerUtilisateur(Utilisateur utilisateur) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

}