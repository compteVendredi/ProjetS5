package utilisateur;

import java.util.HashSet;
import java.util.Set;

public class FilDiscussion {
	
	private Set<Message> messages;
	private int id_filDiscussion;
	private int id_groupe;

	public FilDiscussion(Message ticket,int id_filDiscussion, int id_groupe) {
		messages.add(ticket);
		this.id_filDiscussion = id_filDiscussion;
		this.id_groupe = id_groupe;
	}	
	
	public Set<Message> getMessages() {
		if (this.messages == null) {
			this.messages = new HashSet<Message>();
		}
		return this.messages;
	}

	private Groupe destination;

	public void setDestination(Groupe value) {
		this.destination = value;
	}

	public Groupe getDestination() {
		return this.destination;
	}

	private Message ticket;

	public Message getTicket() {
		return this.ticket;
	}
	
	public int getId_filDiscussion() {
		return id_filDiscussion;
	}

	public int getId_groupe() {
		return id_groupe;
	}
}
