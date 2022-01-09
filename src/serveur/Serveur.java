package serveur;

import java.io.IOException;
import java.net.ServerSocket;

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
	private volatile ServerSocket listener = null;

	/**
	 * Créer un Serveur
	 * 
	 * @param port (inférieur ou égal 1023 si non root)
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
			if(listener != null)
				listener.close();
			if(serviceServeur != null)
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

}
