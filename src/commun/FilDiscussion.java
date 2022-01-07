package commun;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un fil de discussion
 *
 */

public class FilDiscussion {
	
	private List<Message> messages = new ArrayList<>();
	private int id_filDiscussion;
	private String id_groupe;

	/**
	 * Créer un fil de discussion
	 * @param ticket
	 * @param id_filDiscussion
	 * @param id_groupe
	 */
	public FilDiscussion(Message ticket,int id_filDiscussion, String id_groupe) {
		this.messages.add(ticket);
		this.id_filDiscussion = id_filDiscussion;
		this.id_groupe = id_groupe;
	}	
	
	/**
	 * Récupère les messages du fil de discussion
	 * @return les messages
	 */
	public List<Message> getMessages() {
		return this.messages;
	}
	
	/**
	 * Ajoute un message au fil
	 * @param msg
	 */
	public void ajouterMessage(Message msg) {
		messages.add(msg);
	}	

	/**
	 * Récupère l'id du fil 
	 * @return id_filDiscussion
	 */
	public int getId_filDiscussion() {
		return id_filDiscussion;
	}

	/**
	 * Récupère l'id du groupe auquel le fil appartient
	 * @return id_groupe
	 */
	public String getId_groupe() {
		return id_groupe;
	}
	
	/**
	 * Récupère le premier message / titre / ticket du fil
	 * @return titre
	 */
	public String toString() {
		return messages.get(0).getMessage();
	}
}
