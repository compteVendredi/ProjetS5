package serveur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import commun.FilDiscussion;
import commun.FilDiscussionUtilisateur;
import commun.Message;
import utilitaire.Communication;

/**
 * Thread qui offre les services pour un utilsateur 
 * (se connectant au serveur)
 *
 */

public class ServiceThreadUtilisateur extends Thread {

	private int numeroClient;
	private BDD bdd;
	private volatile boolean estActif = true;
	private volatile BufferedReader is;
	private volatile BufferedWriter os;
	private volatile Socket socketUtilisateur;
	
	/**
	 * Redéfinition locale d'une méthode de log
	 */
	
	private void log(String msg) {
		Communication.log("Service thread n°" + numeroClient + " : " + msg);
	}	

	/**
	 * Créer un service thread utilisateur
	 * @param socketUtilisateur
	 * @param numeroClient
	 * @param bdd
	 */
	
	public ServiceThreadUtilisateur(Socket socketUtilisateur, int numeroClient, BDD bdd) {
		this.numeroClient = numeroClient;
		this.bdd = bdd;
		this.socketUtilisateur = socketUtilisateur;
		try {
			is = new BufferedReader(new InputStreamReader(this.socketUtilisateur.getInputStream()));
			os = new BufferedWriter(new OutputStreamWriter(this.socketUtilisateur.getOutputStream()));
		}
		catch(Exception e) {
			log("Impossible d'établir un flux sur le socket utilisateur : " + e.toString());
		}
	}
	
	
	/**
	 * Etabli la connexion avec l'utilisateur (sur le socket)
	 * @return 0 si réussi 1 si mauvais identifiant 2 si mauvais mot de passe 3 sinon
	 */
	
	private int etablirConnexion() {
		String line;
		try {
			line = is.readLine();
		} catch (IOException e) {
			log("[ERREUR] Impossible de lire le flux sur le socket utilisateur : " + e.toString());
			return 3;
		}
		
		String[] parts = line.split(" ");
		if(parts.length != 2) {
			log("[ERREUR] Mauvaises données reçues");
			return 3;
		}
		
		String identifiant = parts[0];
		String motDePasse = parts[1];
		
		boolean estExistant = bdd.existeUser(identifiant);
		boolean estMotDePasseCorrect = false;
		if (estExistant)
			estMotDePasseCorrect = bdd.getHash(identifiant).equals(motDePasse);

		if (!(estExistant && estMotDePasseCorrect)) {
			Communication.envoyerMsg(os, "Non");
			if(estExistant)
				return 2;
			else
				return 1;
		} 
		
		Communication.envoyerMsg(os, "OK");
		Communication.envoyerMsg(os, Communication.gson.toJson(bdd.getUtilisateur(identifiant)));
		return 0;
	}
	
	/**
	 * Code exécuté par le thread
	 */
	
	public void run() {
		String line = null;

		int retour = etablirConnexion();
		if(retour == 1) 
			log("[ERREUR] Impossible d'établir la connexion (mauvais identifiant)");
		else if(retour == 2)
			log("[ERREUR] Impossible d'établir la connexion (mauvais mot de passe)");
		else
			while (estActif) {
				try {
					line = is.readLine();
				} catch (IOException e) {
					log("[ERREUR] IO sur la lecture du flux : " + e.toString());
				}
				if (line.equals("QUIT")) {
					Communication.log("QUIT reçu");
					estActif = false;
				}
				else {
					if(traiterDemande(line) != 0)
						log("[ERREUR] Impossible de traiter la demande du client");						
				}

			}
		log("Fin connexion du client n°" + numeroClient);
	}

	/**
	 * Permet de traiter les demandes du client
	 * @param demande
	 * @return 0 succès 1 sinon
	 */
	
	public int traiterDemande(String demande) {
		log("Début du traitement de la demande : " + demande);
		switch (demande) {
			case Communication.demandeCreationMsg:{
				Message msg = Communication.gson.fromJson(Communication.lireMsg(is), Message.class);
				FilDiscussion filDiscu = Communication.gson.fromJson(Communication.lireMsg(is), FilDiscussion.class);
				if(bdd.ajouterMessage(msg.getId_utilisateur(), filDiscu.getId_filDiscussion(),msg.getDate(), msg.getMessage()) != 0)
					return 1;
				break;}
			case Communication.demandeCreationFil:{
				String id_utilisateur = Communication.lireMsg(is);
				Message msg = Communication.gson.fromJson(Communication.lireMsg(is), Message.class);
				String id_groupe = Communication.lireMsg(is);
				if(bdd.ajouterFil(id_utilisateur, msg.getDate(), msg.getMessage(), id_groupe) != 0)
					return 1;
				break;}
			case Communication.demandeTousFils:{
				String id_utilisateur = Communication.lireMsg(is);
				List<FilDiscussionUtilisateur> listeFils = new LinkedList<FilDiscussionUtilisateur>();
				List<String[]> tousFils = bdd.getListFil(id_utilisateur);
				for (String[] i : tousFils) {
				    listeFils.add(new FilDiscussionUtilisateur(new Message(null, null, null, null, null, i[1]),Integer.parseInt(i[0]), "", Integer.parseInt(i[2])));
				}
				Communication.envoyerMsg(os, Communication.gson.toJson(listeFils));
				break;}
			case Communication.demandeFil:{
				String id_filDiscussion = Communication.lireMsg(is);
				String identifiant = Communication.lireMsg(is);
				FilDiscussion filDiscu = bdd.getFil(Integer.parseInt(id_filDiscussion), identifiant);
				Communication.envoyerMsg(os, Communication.gson.toJson(filDiscu));
				break;}
			case Communication.demandeTousGroupes:{
				List<String> listeGroupe = bdd.getListGroupe();	
				Communication.envoyerMsg(os, Communication.gson.toJson(listeGroupe));
				break;}
			case Communication.demandeGroupeUtilisateur:{
				String id_user = Communication.lireMsg(is);
				List<String> listeGroupeUtilisateur = bdd.getListGroupeUtilisateur(id_user);	
				Communication.envoyerMsg(os, Communication.gson.toJson(listeGroupeUtilisateur));			
				break;}
			default:
				return 1;
		}
		log("Fin du traitement (succès)");
		return 0;
	}

	/**
	 * Permet l'arrêt du thread service utilisateur
	 */
	
	public void arreter() {
		try {
			socketUtilisateur.close();
			if(is != null)
				is.close();
			if(os != null)
				os.close();
		} catch (Exception e) {
			log("[ERREUR] Impossible de fermer les flux du service utilisateur : " + e.toString());
		}
		estActif = false;
	}

}
