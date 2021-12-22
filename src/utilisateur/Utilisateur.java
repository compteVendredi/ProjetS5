package utilisateur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import com.google.gson.Gson;

import utilitaire.Communication;

public class Utilisateur {
	private String identifiant;
	private String motDePasse;
	private String nom;
	private String prenom;
	Socket socketServeur = null;
	BufferedWriter os = null;
	BufferedReader is = null;

	public Utilisateur(String identifiant, String motDePasse, String nom, String prenom) {
		this.identifiant = identifiant;
		this.motDePasse = motDePasse;
		this.nom = nom;
		this.prenom = prenom;
	}

	/**
	 * Se connecte
	 * 
	 * @param ip
	 * @param port
	 * @return 0 si succès 1 si refus 2 sinon
	 */
	public int seConnecter(String ip, int port) {

		try {

			socketServeur = new Socket(ip, port);
			os = new BufferedWriter(new OutputStreamWriter(socketServeur.getOutputStream()));
			is = new BufferedReader(new InputStreamReader(socketServeur.getInputStream()));

		} catch (UnknownHostException e) {
			e.printStackTrace();
			return 2;
		} catch (IOException e) {
			e.printStackTrace();
			return 2;
		}
		if (Communication.envoyerMsg(os, identifiant + " " + motDePasse) != 0)
			return 2;

		if (Communication.lireMsg(is) != "OK") {
			seDeconnecter();
			return 1;
		}
		Gson gson = new Gson();
		Utilisateur e = gson.fromJson(Communication.lireMsg(is), Utilisateur.class);
		nom = e.nom;
		prenom = e.prenom;

		return 0;
	}

	/**
	 * Se déconnecte
	 * 
	 * @return 0 si succès 1 sinon
	 */
	public int seDeconnecter() {
		try {
			if (Communication.envoyerMsg(os, "QUIT") != 0)
				return 1;

			os.close();
			is.close();
			socketServeur.close();
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	public void envoyerMessage(Message message, FilDiscussion filDiscussion) {

	}
	
	public List<FilDiscussion> getAllFilDiscussion() {
		return null;
	}
	
	public FilDiscussion getFilDiscussion(int id_filDiscussion) {
		return null;
	}	
	
	public List<String> getAllGroupe(){
		return null;
	}

	public void setIdentifiant(String value) {
		identifiant = value;
	}

	public String getIdentifiant() {
		return identifiant;
	}

	public void setMotDePasse(String value) {
		motDePasse = value;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setNom(String value) {
		nom = value;
	}

	public String getNom() {
		return nom;
	}

	public void setPrenom(String value) {
		prenom = value;
	}

	public String getPrenom() {
		return prenom;
	}

}
