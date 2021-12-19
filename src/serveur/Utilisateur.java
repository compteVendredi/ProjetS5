package serveur;

public class Utilisateur {
	private String identifiant;
	private String motDePasse;
	private String nom;
	private String prenom;

	protected void setIdentifiant(String value) {
		this.identifiant = value;
	}

	protected String getIdentifiant() {
		return this.identifiant;
	}

	protected void setMotDePasse(String value) {
		this.motDePasse = value;
	}

	protected String getMotDePasse() {
		return this.motDePasse;
	}

	protected void setNom(String value) {
		this.nom = value;
	}

	protected String getNom() {
		return this.nom;
	}

	protected void setPrenom(String value) {
		this.prenom = value;
	}

	protected String getPrenom() {
		return this.prenom;
	}

	public void Utilisateur(String identifiant, String motDePasse, String nom, String prenom) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer seConnecter(Serveur serveur) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer seDeconnecter() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Groupe getGroupe() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public void envoyerMessage(Message message, FilDiscussion filDiscussion) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

}