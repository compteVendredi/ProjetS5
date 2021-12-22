package serveur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;

import utilisateur.FilDiscussion;
import utilisateur.Groupe;
import utilisateur.Message;
import utilisateur.Utilisateur;

import com.google.gson.Gson;

public class Serveur {

	private static BDD bdd;
	private int port;
	private static boolean estActif = true;
	ServerSocket listener = null;
	
	public static void log(String msg) {
		System.out.println(msg);
	}

	/**
	 * Constructeur d'un serveur qui s'initialise
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
		int clientNumber = 0;
		
		if(bdd.seConnecter() != 0)
			return 1;
		
		try {
			listener = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}

		estActif = true;
		try {
			while (estActif) {
				// Accept client connection request
				// Get new Socket at Server.
				Socket socketOfServer = listener.accept();
				log("Nouvelle connexion n°" + clientNumber);
				new ServiceThread(socketOfServer, clientNumber++).start();
			}
		} catch (IOException e) {
			return arreter();
		}
		return arreter();
	}

	/**
	 * Arrete le serveur
	 * 
	 * @return 0 si succès 1 sinon
	 */
	public int arreter() {
		bdd.seDeconnecter();
		
		estActif = false;
		try {
			listener.close();
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	public Set<FilDiscussion> getFildiscussion() {
		return null;
	}

	public Set<Groupe> getGroupes() {
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

	public int ajouterGroupe(Groupe groupe) {
		return 1;
	}

	public int supprimerGroupe(Groupe groupe) {
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

	private static class ServiceThread extends Thread {

		private int clientNumber;
		private Socket socketOfServer;

		public ServiceThread(Socket socketOfServer, int clientNumber) {
			this.clientNumber = clientNumber;
			this.socketOfServer = socketOfServer;
		}

		@Override
		public void run() {

			try {

				// Open input and output streams
				BufferedReader is = new BufferedReader(new InputStreamReader(socketOfServer.getInputStream()));
				BufferedWriter os = new BufferedWriter(new OutputStreamWriter(socketOfServer.getOutputStream()));
				String line;
				
				line = is.readLine();
				String[] parts = line.split(" ");
				String identifiant = parts[0];
				String motDePasse = parts[1];
				boolean estExistant = bdd.existeUser(identifiant);
				boolean estMotDePasseCorrect = bdd.getHash(identifiant) == motDePasse;

				if(!estExistant || !estMotDePasseCorrect) {
					os.write(estExistant + " " + estMotDePasseCorrect);
					os.newLine();
					os.flush();
					log("Fin connexion n°" + clientNumber + " car existe = " + estExistant +  " ou mot de passe correct = " + estMotDePasseCorrect + " refusé");
				}
				else {
					Gson gson = new Gson();
					os.write(gson.toJson(bdd.getUser(identifiant)));
					while (estActif) {
						// Read data to the server (sent from client).
						line = is.readLine();
						
						// If users send QUIT (To end conversation).
						if (line.equals("QUIT")) {
							log("QUIT reçu");							
							break;
						}
					}			
					log("Fin connexion n°" + clientNumber);
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
