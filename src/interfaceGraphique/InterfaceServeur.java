package interfaceGraphique;

import serveur.Serveur;

public class InterfaceServeur {
	/**
	 * <pre>
	 *           1..1          1..1
	 * InterfaceServeur ------------------------> Serveur
	 *           &lt;       serveur
	 * </pre>
	 */
	private Serveur serveur;

	public void setServeur(Serveur value) {
		this.serveur = value;
	}

	public Serveur getServeur() {
		return this.serveur;
	}

	public void lancer() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

}