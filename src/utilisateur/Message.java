package utilisateur;

import java.util.Date;

public class Message {
	private String id_utilisateur;
	private String nom;
	private String prenom;
	private Date date;
	private Couleur statut;
	private String message;

	

	public Message(String id_utilisateur, String nom, String prenom, Date date, Couleur statut, String message) {
		this.id_utilisateur = id_utilisateur;
		this.nom = nom;
		this.prenom = prenom;
		this.date = date;
		this.statut = statut;
		this.message = message;
	}
	
	public void setMessage(String value) {
		this.message = value;
	}

	public String getMessage() {
		return this.message;
	}

	public String toString() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Couleur getStatut() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public void setStatut() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

}
