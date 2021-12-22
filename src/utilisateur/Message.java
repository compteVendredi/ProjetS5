package utilisateur;

import java.util.Date;

public class Message {
	private Utilisateur personneSource;
	private Couleur statut;
	private String message;

	public Message(String message) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}
	
	public void setPersonneSource(Utilisateur value) {
		this.personneSource = value;
	}

	public Utilisateur getPersonneSource() {
		return this.personneSource;
	}

	private Date dateEmission;

	public void setDateEmission(Date value) {
		this.dateEmission = value;
	}

	public Date getDateEmission() {
		return this.dateEmission;
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