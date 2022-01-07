package commun;

/**
 * Représente un fil de discussion spécifique à un utilisateur
 *
 */

public class FilDiscussionUtilisateur extends FilDiscussion {
	private int nb_messageNonLu;
	
	/**
	 * Créer un fil de discussion
	 * @param ticket
	 * @param id_filDiscussion
	 * @param id_groupe
	 * @param nb_messageNonLu
	 */
	public FilDiscussionUtilisateur(Message ticket,int id_filDiscussion, String id_groupe, int nb_messageNonLu) {
		super(ticket, id_filDiscussion, id_groupe);
		this.nb_messageNonLu = nb_messageNonLu;
	}	
	
	/**
	 * Récupère le nombre de message non lu
	 * @return
	 */
	public int getNb_messageNonLu() {
		return nb_messageNonLu;
	}

	/**
	 * Met à jour le nombre de message non lu
	 * @param nb_messageNonLu
	 */
	public void setNb_messageNonLu(int nb_messageNonLu) {
		this.nb_messageNonLu = nb_messageNonLu;
	}
	
	
}
