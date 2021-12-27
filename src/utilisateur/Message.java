package utilisateur;

public class Message {
	private String id_utilisateur;
	private String nom;
	private String prenom;
	private String date;
	private String statut;
	private String message;

	

	public Message(String id_utilisateur, String nom, String prenom, String date, String statut, String message) {
		this.id_utilisateur = id_utilisateur;
		this.nom = nom;
		this.prenom = prenom;
		this.date = date;
		this.statut = statut;
		this.message = message;
	}



	public String getStatut() {
		return statut;
	}



	public void setStatut(String statut) {
		this.statut = statut;
	}



	public String getId_utilisateur() {
		return id_utilisateur;
	}



	public String getNom() {
		return nom;
	}



	public String getPrenom() {
		return prenom;
	}



	public String getDate() {
		return date;
	}



	public String getMessage() {
		return message;
	}
	
	

}
