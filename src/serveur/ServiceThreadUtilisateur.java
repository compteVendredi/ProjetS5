package serveur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import utilisateur.FilDiscussion;
import utilisateur.Message;
import utilitaire.Communication;

public class ServiceThreadUtilisateur extends Thread {

	private int clientNumber;
	private Socket socketUtilisateur;
	private BDD bdd;
	private boolean estActif = true;
	BufferedReader is;
	BufferedWriter os;

	public ServiceThreadUtilisateur(Socket socketUtilisateur, int clientNumber, BDD bdd) {
		this.clientNumber = clientNumber;
		this.socketUtilisateur = socketUtilisateur;
		this.bdd = bdd;
	}

	@Override
	public void run() {

		try {
			is = new BufferedReader(new InputStreamReader(socketUtilisateur.getInputStream()));
			os = new BufferedWriter(new OutputStreamWriter(socketUtilisateur.getOutputStream()));
			String line;

			line = is.readLine();
			String[] parts = line.split(" ");
			boolean estExistant = false, estMotDePasseCorrect = false;
			String identifiant = "";
			if(parts.length == 2) {
				identifiant = parts[0];
				String motDePasse = parts[1];
				estExistant = bdd.existeUser(identifiant);
				if (estExistant)
					estMotDePasseCorrect = bdd.getHash(identifiant).equals(motDePasse);
			}

			if (!estExistant || !estMotDePasseCorrect) {
				os.write(estExistant + " " + estMotDePasseCorrect);
				os.newLine();
				os.flush();
				Communication.log("Fin connexion n°" + clientNumber + " car existe = " + estExistant
						+ " ou mot de passe correct = " + estMotDePasseCorrect + " refusé");
			} else {
				Communication.envoyerMsg(os, "OK");
				Communication.envoyerMsg(os, Communication.gson.toJson(bdd.getUtilisateur(identifiant)));
				while (estActif) {
					// Read data to the server (sent from client).
					line = is.readLine();

					// If users send QUIT (To end conversation).
					if (line.equals("QUIT")) {
						Communication.log("QUIT reçu");
						break;
					} 
					System.out.println("Début service serveur reçu : " + line);
					traiterDemande(line);
					System.out.println("Fin service");
				}
				Communication.log("Fin connexion n°" + clientNumber);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void traiterDemande(String demande) {
		switch (demande) {
		case Communication.demandeCreationMsg:
			Message msg = Communication.gson.fromJson(Communication.lireMsg(is), Message.class);
			FilDiscussion filDiscu = Communication.gson.fromJson(Communication.lireMsg(is), FilDiscussion.class);
			bdd.ajouterMessage(msg.getId_utilisateur(), filDiscu.getId_filDiscussion(),msg.getDate(), msg.getMessage());
			break;
		case Communication.demandeTousFils:
			String id_utilisateur = Communication.lireMsg(is);
			List<FilDiscussion> listeFils = new LinkedList<FilDiscussion>();
			Map<Integer, String> mapTousFils = bdd.getListFil(id_utilisateur);
			for (Map.Entry<Integer, String> pair : mapTousFils.entrySet()) {
			    listeFils.add(new FilDiscussion(new Message(null, null, null, null, null, pair.getValue()),pair.getKey(), ""));
			}
			Communication.envoyerMsg(os, Communication.gson.toJson(listeFils));
			break;
		case Communication.demandeFil:
			String id_filDiscussion = Communication.lireMsg(is);
			String identifiant = Communication.lireMsg(is);
			Communication.envoyerMsg(os, Communication.gson.toJson(bdd.getFil(Integer.parseInt(id_filDiscussion), identifiant)));
			break;
		case Communication.demandeTousGroupes:
			List<String> listeGroupe = bdd.getListGroupe();	
			Communication.envoyerMsg(os, Communication.gson.toJson(listeGroupe));
			break;
		case Communication.demandeGroupeUtilisateur:
			String id_user = Communication.lireMsg(is);
			List<String> listeGroupeUtilisateur = bdd.getListGroupeUtilisateur(id_user);	
			Communication.envoyerMsg(os, Communication.gson.toJson(listeGroupeUtilisateur));			
			break;			
		}
	}
		

	public void arreter() {
		try {
			is.close();
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		estActif = false;
	}

}
