package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import utilitaire.Communication;

/** 
 * Thread qui offre les services pour un serveur
 *
 */

public class ServiceThreadServeur extends Thread {

	private volatile ServerSocket listener;
	private BDD bdd;
	private volatile boolean estActif = true;
	private List<ServiceThreadUtilisateur> utilisateurs = new LinkedList<ServiceThreadUtilisateur>();

	/**
	 * Créer un ServiceThreadServeur
	 * @param listener (socket en écoute continue)
	 * @param bdd
	 */
	
	public ServiceThreadServeur(ServerSocket listener, BDD bdd) {
		this.listener = listener;
		this.bdd = bdd;
	}

	/**
	 * Code exécuté par le thread
	 */
	
	public void run() {
		int clientNumber = 0;
		try {
			Communication.log("Le serveur peut recevoir de nouveaux clients");
			while (estActif) {
				Socket socketOfServer = listener.accept();			
				Communication.log("Nouvelle connexion client n°" + clientNumber + " acceptée");
				ServiceThreadUtilisateur nouveauUtilisateur = new ServiceThreadUtilisateur(socketOfServer,
						clientNumber++, bdd);
				utilisateurs.add(nouveauUtilisateur);
				nouveauUtilisateur.start();

			}
		} catch (IOException e) {
			Communication.log("Le serveur ne reçoit plus de nouveau client : " + e.toString());
		}
	}

	/**
	 * Permet l'arrêt du thread service serveur
	 */
	
	public void arreter() {
		Communication.log("Début de l'arrêt du service thread serveur");
		estActif = false;
		ServiceThreadUtilisateur i = null;
		for (Iterator<ServiceThreadUtilisateur> iterateur = utilisateurs.iterator(); iterateur
				.hasNext(); i = iterateur.next()) {
			if (i != null) {
				i.arreter();
				try {
					i.join();
				} catch (Exception e) {
					Communication.log("[ERREUR] Impossible d'arrêter un thread service utilisateur " + e.toString());
				}
			}
		}
		Communication.log("Fin de l'arrêt du service thread serveur (succès)");
	}

}
