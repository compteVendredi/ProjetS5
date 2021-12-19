package serveur;

import java.util.Date;

public class Message {
	private Utilisateur personneSource;
	private Couleur statut;
	private String message;

	protected void setPersonneSource(Utilisateur value) {
		this.personneSource = value;
	}

	protected Utilisateur getPersonneSource() {
		return this.personneSource;
	}

	private Date dateEmission;

	protected void setDateEmission(Date value) {
		this.dateEmission = value;
	}

	protected Date getDateEmission() {
		return this.dateEmission;
	}

	protected void setMessage(String value) {
		this.message = value;
	}

	protected String getMessage() {
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

	public void Message(String message) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public void setStatut() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

}