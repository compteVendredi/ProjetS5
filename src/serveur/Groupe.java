package serveur;

import java.util.Set;
import java.util.HashSet;

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

	public Set<Utilisateur> getUtilisateurs() {
		if (this.utilisateurs == null) {
			this.utilisateurs = new HashSet<Utilisateur>();
		}
		return this.utilisateurs;
	}

	protected void setRole(Role value) {
		this.role = value;
	}

	protected Role getRole() {
		return this.role;
	}

	public Integer creerTicket(String message) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public void Groupe() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer ajouterUtilisateur(Utilisateur utilisateur) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer supprimerUtilisateur(Utilisateur utilisateur) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

}