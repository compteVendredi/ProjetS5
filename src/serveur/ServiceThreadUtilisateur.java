package serveur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.google.gson.Gson;

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
			String identifiant = parts[0];
			String motDePasse = parts[1];
			boolean estExistant = bdd.existeUser(identifiant);
			boolean estMotDePasseCorrect = false;
			if(estExistant)
				estMotDePasseCorrect = bdd.getHash(identifiant).equals(motDePasse);

			if (!estExistant || !estMotDePasseCorrect) {
				os.write(estExistant + " " + estMotDePasseCorrect);
				os.newLine();
				os.flush();
				Communication.log("Fin connexion n°" + clientNumber + " car existe = " + estExistant
						+ " ou mot de passe correct = " + estMotDePasseCorrect + " refusé");
			} else {
				Gson gson = new Gson();
				Communication.envoyerMsg(os, gson.toJson(bdd.getUtilisateur(identifiant)));
				while (estActif) {
					// Read data to the server (sent from client).
					line = is.readLine();

					// If users send QUIT (To end conversation).
					if (line.equals("QUIT")) {
						Communication.log("QUIT reçu");
						break;
					}
				}
				Communication.log("Fin connexion n°" + clientNumber);
			}

		} catch (IOException e) {
			e.printStackTrace();
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
