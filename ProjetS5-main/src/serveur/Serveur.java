package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Set;

import utilisateur.FilDiscussion;
import utilisateur.Message;
import utilisateur.Utilisateur;
import utilitaire.Communication;

/**
 * Serveur permettant d'exécuter des opérations sur une 
 * bdd (base de donnée) et de répondre aux demandes des clients
 *
 */

public class Serveur {

	private BDD bdd;
	private int port;
	private ServiceThreadServeur serviceServeur = null;
	private ServerSocket listener = null;

	/**
	 * Créer un Serveur
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
		Communication.log("Début du démarrage du serveur sur le port " + port);
		if (bdd.seConnecter() != 0) {
			Communication.log("[ERREUR] Impossible de démarrer la BDD");
			return 1;
		}
		try {
			listener = new ServerSocket(port);
		} catch (IOException e) {
			Communication.log("[ERREUR] IO socket écoute :" + e.toString());
			return 1;
		}

		serviceServeur = new ServiceThreadServeur(listener, bdd);
		serviceServeur.start();

		Communication.log("Fin du démarrage du serveur sur le port (succès)");
		
		return 0;
	}

	/**
	 * Arrete le serveur
	 * 
	 * @return 0 si succès 1 (ou 2 si IO erreur) sinon
	 */
	public int arreter() {
		Communication.log("Début de l'arrêt du serveur ");
		if(bdd != null)
			bdd.seDeconnecter();
		if(serviceServeur != null)
			serviceServeur.arreter();

		try {
			listener.close();
			serviceServeur.join();
		} catch (InterruptedException e) {
			Communication.log("[ERREUR] interruption " + e.toString());
			return 1;
		} catch (IOException e) {
			Communication.log("[ERREUR] entrée/sortie " + e.toString());
			return 2;
		}
		Communication.log("Fin de l'arrêt du serveur (succès)");
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
