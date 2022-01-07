package utilisateur;

import java.util.ArrayList;
import java.util.List;

public class FilDiscussion {
	
	private List<Message> messages = new ArrayList<>();
	private int id_filDiscussion;
	private String id_groupe;
	private int nb_messageNonLu;
	
	public FilDiscussion(Message ticket,int id_filDiscussion, String id_groupe) {
		this.messages.add(ticket);
		this.id_filDiscussion = id_filDiscussion;
		this.id_groupe = id_groupe;
	}	
	
	public FilDiscussion(Message ticket,int id_filDiscussion, String id_groupe, int nb_messageNonLu) {
		this.messages.add(ticket);
		this.id_filDiscussion = id_filDiscussion;
		this.id_groupe = id_groupe;
		this.nb_messageNonLu = nb_messageNonLu;
	}	
	
	public List<Message> getMessages() {
		return this.messages;
	}
	
	public void ajouterMessage(Message msg) {
		messages.add(msg);
	}	

	public int getId_filDiscussion() {
		return id_filDiscussion;
	}

	public String getId_groupe() {
		return id_groupe;
	}
	
	public String toString() {
		return messages.get(0).getMessage();
	}

	public int getNb_messageNonLu() {
		return nb_messageNonLu;
	}

	public void setNb_messageNonLu(int nb_messageNonLu) {
		this.nb_messageNonLu = nb_messageNonLu;
	}
	
	
}
