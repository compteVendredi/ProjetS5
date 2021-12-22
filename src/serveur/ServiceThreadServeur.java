package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import utilitaire.Communication;

public class ServiceThreadServeur extends Thread {

	private ServerSocket listener;
	private BDD bdd;
	private volatile boolean estActif = true;
	private List<ServiceThreadUtilisateur> utilisateurs = new LinkedList<ServiceThreadUtilisateur>();

	public ServiceThreadServeur(ServerSocket listener, BDD bdd) {
		this.listener = listener;
		this.bdd = bdd;
		this.utilisateurs = utilisateurs;
	}

	public void run() {
		int clientNumber = 0;
		try {
			while (estActif) {
				Socket socketOfServer = listener.accept();
				Communication.log("Nouvelle connexion n°" + clientNumber);
				ServiceThreadUtilisateur nouveauUtilisateur = new ServiceThreadUtilisateur(socketOfServer,
						clientNumber++, bdd);
				utilisateurs.add(nouveauUtilisateur);
				nouveauUtilisateur.start();

			}
		} catch (IOException e) {
			Communication.log("Le serveur ne reçoit plus de nouveau client");
			//e.printStackTrace();
		}
	}

	public void arreter() {
		estActif = false;
		ServiceThreadUtilisateur i = null;
		for (Iterator<ServiceThreadUtilisateur> iterateur = utilisateurs.iterator(); iterateur
				.hasNext(); i = iterateur.next()) {
			if (i != null) {
				i.arreter();
				try {
					i.join();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
