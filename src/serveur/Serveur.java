package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Set;

import utilisateur.FilDiscussion;
import utilisateur.Message;
import utilisateur.Utilisateur;

public class Serveur {

	private BDD bdd;
	private int port;
	private ServiceThreadServeur serviceServeur;
	private ServerSocket listener = null;

	/**
	 * Constructeur d'un serveur qui s'initialise
	 * 
	 * @param port (<= 1023 si non root)
	 * @param bdd
	 */
	public Serveur(int port, BDD bdd) {
		this.port = port;
		this.bdd = bdd;
	}

	/**
	 * Démarre le serveur (fonction bloquante)
	 * 
	 * @return 0 si succès 1 sinon
	 */
	public int demarrer() {

		if (bdd.seConnecter() != 0)
			return 1;

		try {
			listener = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}

		serviceServeur = new ServiceThreadServeur(listener, bdd);
		serviceServeur.start();

		return 0;
	}

	/**
	 * Arrete le serveur
	 * 
	 * @return 0 si succès 1 (ou 2 si IO erreur) sinon
	 */
	public int arreter() {
		bdd.seDeconnecter();
		serviceServeur.arreter();

		try {
			listener.close();
			serviceServeur.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return 1;
		} catch (IOException e) {
			e.printStackTrace();
			return 2;
		}
		return 0;
	}

	public Set<FilDiscussion> getFildiscussion() {
		return null;
	}

	public Set<Utilisateur> getUtilisateurs() {
		return null;
	}

	public int ajouterFilDiscussion(FilDiscussion filDiscussion) {
		return 1;
	}

	public int ajouterUtilisateur(Utilisateur utilisateur) {
		return 1;
	}

	public int supprimerUtilisateur(Utilisateur utilisateur) {
		return 1;
	}

	public int insererDansGroupe(Utilisateur utilisateur) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public int supprimerDansGroupe(Utilisateur utilisateur) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public int supprimerFilDiscussion(FilDiscussion filDiscussion) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public int ajotuerMessage(Message message, FilDiscussion filDiscussion) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

	public int supprimerMessage(Message message, FilDiscussion filDiscuss) {
		// TODO implement this operation
		throw new UnsupportedOperationException("not implemented");
	}

}
