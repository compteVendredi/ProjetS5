package utilisateur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import com.google.gson.reflect.TypeToken;

import commun.FilDiscussion;
import commun.Groupe;
import commun.Message;
import commun.Utilisateur;
import utilitaire.Communication;

/**
 * Représente un utilisateur et offre les services 
 * pour l'interface utilisateur
 */

public class ServiceUtilisateur extends Utilisateur {
	private Socket socketServeur = null;
	private BufferedWriter os = null;
	private BufferedReader is = null;
	
	/**
	 * Créer un service utilisateur
	 * @param identifiant
	 * @param motDePasse
	 * @param nom
	 * @param prenom
	 */
	
	public ServiceUtilisateur(String identifiant, String motDePasse, String nom, String prenom) {
		super(identifiant, motDePasse, nom, prenom);
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
			Communication.log("[ERREUR] hôte inconnu socket : " + e.toString());
			return 3;
		} catch (IOException e) {
			Communication.log("[ERREUR] IO des flux : " + e.toString());
			return 3;
		}
		if (Communication.envoyerMsg(os, identifiant + " " + motDePasse) != 0) {
			Communication.log("[ERREUR] Impossible d'écrire sur le flux de sortie");
			return 3;
		}
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
		ServiceUtilisateur e = Communication.gson.fromJson(Communication.lireMsg(is), ServiceUtilisateur.class);
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

			if(os != null)
				os.close();
			if(is != null)
				is.close();
			if(socketServeur != null)
				socketServeur.close();
		} catch (IOException e) {
			Communication.log("[ERREUR] IO flux utilisateur " + e.toString());
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
	 * Ajoute un fil de discussion
	 * @param message
	 * @param id_groupe
	 * @return 0 si succès 1 sinon
	 */
	
	public int ajouterFilDiscussion(Message message, String id_groupe) {
		if (Communication.envoyerMsg(os, Communication.demandeCreationFil) != 0)
			return 1;
		if (Communication.envoyerMsg(os, identifiant) != 0)
			return 1;			
		if (Communication.envoyerMsg(os, Communication.gson.toJson(message)) != 0)
			return 1;	
		if (Communication.envoyerMsg(os, id_groupe) != 0)
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
		if (Communication.envoyerMsg(os, "" + id_filDiscussion) != 0)
			return null;
		if (Communication.envoyerMsg(os, "" + identifiant) != 0)
			return null;				
		String res;
		if((res = Communication.lireMsg(is)) == null) 
			return null;
		return Communication.gson.fromJson(res, FilDiscussion.class);
	}
	
	/**
	 * Ajoute un groupe
	 * @param groupe
	 */
	public void ajouterGroupe(Groupe groupe) {
		listeGroupes.add(groupe);
		groupe.ajouterUtilisateur(this);
	}
	
	/**
	 * Récupère la liste des groupes de l'utilisateur
	 * @return les groupes de l'utilisateur ou null si erreur
	 */
	
	public List<String> getGroupesUtilisateur() {
		if (Communication.envoyerMsg(os, Communication.demandeGroupeUtilisateur) != 0)
			return null;
		if (Communication.envoyerMsg(os, identifiant) != 0)
			return null;		
		String res;
		if((res = Communication.lireMsg(is)) == null) 
			return null;
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

	/**
	 * Actualise les groupes
	 */
	
	public void actualiseGroupe() {
		List<String> groupes = receiveAllGroupe();
		
		for (int i = 0; i < groupes.size(); i++)
			listeGroupes.add(new Groupe(groupes.get(i)));
	}

}
