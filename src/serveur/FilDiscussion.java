package serveur;

import java.util.Set;
import java.util.HashSet;

public class FilDiscussion {
	/**
	 * <pre>
	 *           1..1          0..*
	 * FilDiscussion ------------------------> Message
	 *           &lt;       messages
	 * </pre>
	 */
	private Set<Message> messages;

	public Set<Message> getMessages() {
		if (this.messages == null) {
			this.messages = new HashSet<Message>();
		}
		return this.messages;
	}

	private Groupe destination;

	protected void setDestination(Groupe value) {
		this.destination = value;
	}

	protected Groupe getDestination() {
		return this.destination;
	}

	private Message ticket;

	protected void setTicket(Message value) {
		this.ticket = value;
	}

	protected Message getTicket() {
		return this.ticket;
	}

	public void FilDiscussion(Message ticket) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer ajotuerMessage(Message message) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer supprimerMessage(Message message) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Message getMessage() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

}