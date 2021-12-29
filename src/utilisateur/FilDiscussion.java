package utilisateur;

import java.util.ArrayList;
import java.util.List;

public class FilDiscussion {
	
	private List<Message> messages = new ArrayList<>();
	private int id_filDiscussion;
	private String id_groupe;

	public FilDiscussion(Message ticket,int id_filDiscussion, String id_groupe) {
		this.messages.add(ticket);
		this.id_filDiscussion = id_filDiscussion;
		this.id_groupe = id_groupe;
	}	
	
	public List<Message> getMessages() {
		return this.messages;
	}

	public int getId_filDiscussion() {
		return id_filDiscussion;
	}

	public String getId_groupe() {
		return id_groupe;
	}
}
