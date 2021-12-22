package utilisateur;

import java.util.HashSet;
import java.util.Set;

import utilisateur.Message;

public class FilDiscussion {
	/**
	 * <pre>
	 *           1..1          0..*
	 * FilDiscussion ------------------------> Message
	 *           &lt;       messages
	 * </pre>
	 */
	private Set<Message> messages;

	public FilDiscussion(Message ticket) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
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

	public void setTicket(Message value) {
		this.ticket = value;
	}

	public Message getTicket() {
		return this.ticket;
	}

	public int ajotuerMessage(Message message) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public int supprimerMessage(Message message) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Message getMessage() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

}