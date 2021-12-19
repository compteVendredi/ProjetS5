package interfaceGraphique;

import serveur.Utilisateur;

public class InterfaceUtilisateur {
	/**
	 * <pre>
	 *           1..1          1..1
	 * InterfaceUtilisateur ------------------------> Utilisateur
	 *           &lt;       utilisateur
	 * </pre>
	 */
	private Utilisateur utilisateur;

	public void setUtilisateur(Utilisateur value) {
		this.utilisateur = value;
	}

	public Utilisateur getUtilisateur() {
		return this.utilisateur;
	}

	public void lancer() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

}