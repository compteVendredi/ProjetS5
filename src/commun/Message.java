package commun;

/**
 * Représente un message
 */

public class Message {
	private String id_utilisateur;
	private String nom;
	private String prenom;
	private String date;
	private String statut;
	private String message;

	
	/**
	 * Créer un message
	 * @param id_utilisateur
	 * @param nom
	 * @param prenom
	 * @param date
	 * @param statut (rappel : gris, rouge, orange, vert)
	 * @param message
	 */
	public Message(String id_utilisateur, String nom, String prenom, String date, String statut, String message) {
		this.id_utilisateur = id_utilisateur;
		this.nom = nom;
		this.prenom = prenom;
		this.date = date;
		this.statut = statut;
		this.message = message;
	}

	/**
	 * Récupère le statut du message
	 * @return statut
	 */
	public String getStatut() {
		return statut;
	}


	/**
	 * Met à jour le statut du message
	 * @param statut
	 */
	public void setStatut(String statut) {
		this.statut = statut;
	}

	/**
	 * Récupère l'id du créateur du message
	 * @return l'id du créateur
	 */
	public String getId_utilisateur() {
		return id_utilisateur;
	}


	/**
	 * Récupère le nom du créateur
	 * @return nom du créateur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Récupère le prénom du créateur
	 * @return prénom du créateur
	 */
	public String getPrenom() {
		return prenom;
	}


	/**
	 * Récupère la date de création
	 * @return date création
	 */
	public String getDate() {
		return date;
	}

	/**
	 * Récupère le contenu du message
	 * @return message
	 */
	public String getMessage() {
		return message;
	}
	
	

}
