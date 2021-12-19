package serveur;

import java.util.HashSet;
import java.util.Set;

import baseDeDonnee.BDD;

public class Serveur {
	/**
	 * <pre>
	 *           1..1          0..*
	 * Serveur ------------------------> FilDiscussion
	 *           &lt;       fildiscussion
	 * </pre>
	 */
	private Set<FilDiscussion> fildiscussion;

	public Set<FilDiscussion> getFildiscussion() {
		if (this.fildiscussion == null) {
			this.fildiscussion = new HashSet<FilDiscussion>();
		}
		return this.fildiscussion;
	}

	/**
	 * <pre>
	 *           1..1          0..*
	 * Serveur ------------------------- Groupe
	 *           serveur        &lt;       groupes
	 * </pre>
	 */
	private Set<Groupe> groupes;

	public Set<Groupe> getGroupes() {
		if (this.groupes == null) {
			this.groupes = new HashSet<Groupe>();
		}
		return this.groupes;
	}

	/**
	 * <pre>
	 *           1..1          0..*
	 * Serveur ------------------------- Utilisateur
	 *           serveur        &lt;       utilisateurs
	 * </pre>
	 */
	private Set<Utilisateur> utilisateurs;

	public Set<Utilisateur> getUtilisateurs() {
		if (this.utilisateurs == null) {
			this.utilisateurs = new HashSet<Utilisateur>();
		}
		return this.utilisateurs;
	}

	private String ip;

	protected void setIp(String value) {
		this.ip = value;
	}

	protected String getIp() {
		return this.ip;
	}

	private String port;

	protected void setPort(String value) {
		this.port = value;
	}

	protected String getPort() {
		return this.port;
	}

	/**
	 * <pre>
	 *           1..1          1..1
	 * Serveur ------------------------> BDD
	 *           &lt;       bdd
	 * </pre>
	 */
	private BDD bdd;

	public void setBdd(BDD value) {
		this.bdd = value;
	}

	public BDD getBdd() {
		return this.bdd;
	}

	public Integer ajouterFilDiscussion(FilDiscussion filDiscussion) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer ajouterUtilisateur(Utilisateur utilisateur) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer supprimerUtilisateur(Utilisateur utilisateur) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer ajouterGroupe(Groupe groupe) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer supprimerGroupe(Groupe groupe) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer demarrer() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer arreter() {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public void Serveur(String ip, String port, BDD bdd) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer insererDansGroupe(Utilisateur utilisateur) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer supprimerDansGroupe(Utilisateur utilisateur) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer supprimerFilDiscussion(FilDiscussion filDiscussion) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer ajotuerMessage(Message message, FilDiscussion filDiscussion) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public Integer supprimerMessage(Message message, FilDiscussion filDiscuss) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

}
