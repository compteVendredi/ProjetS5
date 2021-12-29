package utilisateur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import utilitaire.Communication;

public class Utilisateur {
	private String identifiant;
	private String motDePasse;
	private String nom;
	private String prenom;
	private List<Groupe> listeGroupes = new ArrayList<>();
	transient Socket socketServeur = null;
	transient BufferedWriter os = null;
	transient BufferedReader is = null;
	
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
	 * @return 0 si succès 1 si id inexistant 2 si mauvais mot de passe 3 sinon
	 */
	public int seConnecter(String ip, int port) {

		try {

			socketServeur = new Socket(ip, port);
			os = new BufferedWriter(new OutputStreamWriter(socketServeur.getOutputStream()));
			is = new BufferedReader(new InputStreamReader(socketServeur.getInputStream()));

		} catch (UnknownHostException e) {
			e.printStackTrace();
			return 3;
		} catch (IOException e) {
			e.printStackTrace();
			return 3;
		}
		if (Communication.envoyerMsg(os, identifiant + " " + motDePasse) != 0)
			return 3;
		String res = Communication.lireMsg(is);
		if (!res.equals("OK")) {
			String[] parts = res.split(" ");
			String estExistant = parts[0];
			seDeconnecter();
			if(estExistant == "false")
				return 1;
			else
				return 2;
		}
		Utilisateur e = Communication.gson.fromJson(Communication.lireMsg(is), Utilisateur.class);
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

	/**
	 * Envoie un message
	 * @param message
	 * @param filDiscussion
	 * @return 0 si succès 1 sinon
	 */
	public int envoyerMessage(Message message, FilDiscussion filDiscussion) {
		if (Communication.envoyerMsg(os, Communication.demandeCreationMsg) != 0)
			return 1;
		if (Communication.envoyerMsg(os, Communication.gson.toJson(message)) != 0)
			return 1;
		if (Communication.envoyerMsg(os, Communication.gson.toJson(filDiscussion)) != 0)
			return 1;		
		return 0;
	}
	
	
	/**
	 * Récupère tous les fils de discussion
	 * @return tous les fils de discussion ou null en cas d'échec
	 */
	public List<FilDiscussion> getAllFilDiscussion() {
		if (Communication.envoyerMsg(os, Communication.demandeTousFils) != 0)
			return null;
		if (Communication.envoyerMsg(os, identifiant) != 0)
			return null;		
		String res;
		if((res = Communication.lireMsg(is)) == null) 
			return null;
		return Communication.gson.fromJson(res, new TypeToken<List<FilDiscussion>>(){}.getType());
	}
	
	
	/**
	 * Récupère un fil de discussion
	 * @param id_filDiscussion
	 * @return le fil de discussion ou null si erreur
	 */
	public FilDiscussion getFilDiscussion(int id_filDiscussion) {
		if (Communication.envoyerMsg(os, Communication.demandeFil) != 0)
			return null;
		if (Communication.envoyerMsg(os, " " + id_filDiscussion) != 0)
			return null;
		String res;
		if((res = Communication.lireMsg(is)) == null) 
			return null;
		return Communication.gson.fromJson(res, FilDiscussion.class);
	}
	
	public void ajouterGroupe(Groupe groupe) {
		listeGroupes.add(groupe);
		groupe.ajouterUtilisateur(this);
	}
	
	public List<String> getGroupesUtilisateur() {
		if (Communication.envoyerMsg(os, Communication.demandeGroupeUtilisateur) != 0)
			return null;
		if (Communication.envoyerMsg(os, identifiant) != 0)
			return null;		
		String res;
		if((res = Communication.lireMsg(is)) == null) 
			return null;
		System.out.println("aaa" + res);
		return Communication.gson.fromJson(res, new TypeToken<List<String>>(){}.getType());
	}
	/**
	 * 
	 * @return tous les groupes  ou null si erreur
	 */
	public List<String> receiveAllGroupe(){
		if (Communication.envoyerMsg(os, Communication.demandeTousGroupes) != 0)
			return null;
		String res;
		if((res = Communication.lireMsg(is)) == null) 
			return null;
		return Communication.gson.fromJson(res, new TypeToken<List<String>>(){}.getType());
	}
	
	public void actualiseGroupe() {
		List<String> groupes = receiveAllGroupe();
		
		for (int i = 0; i < groupes.size(); i++)
			listeGroupes.add(new Groupe(groupes.get(i)));
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
