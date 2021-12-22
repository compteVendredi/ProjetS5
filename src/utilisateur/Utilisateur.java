package utilisateur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Set;

import com.google.gson.Gson;

public class Utilisateur {
	private String identifiant;
	private String motDePasse;
	private String nom;
	private String prenom;
	Socket socketOfClient = null;
	BufferedWriter os = null;
	BufferedReader is = null;

	public Utilisateur(String identifiant, String motDePasse, String nom, String prenom) {
		this.identifiant = identifiant;
		this.motDePasse = motDePasse;
		this.nom = nom;
		this.prenom = prenom;
	}
	
	private int envoyerMsgAuServeur(String msg){
		try {
			os.write(msg);
			os.newLine();
			os.flush();
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
	
	private String lireMsgServeur() {
		String responseLine;
        try{
        	responseLine = is.readLine();
        } catch(IOException e) {
        	e.printStackTrace();
        	return null;
        }
        return responseLine;

	}

	/**
	 * Se connecte
	 * 
	 * @param ip
	 * @param port
	 * @return 0 si succès 1 si inconnu 2 si mot de passe incorrect 3 sinon
	 */
	public int seConnecter(String ip, int port) {

		try {

			socketOfClient = new Socket(ip, port);
			// Create output stream at the client (to send data to the server)
			os = new BufferedWriter(new OutputStreamWriter(socketOfClient.getOutputStream()));
			// Input stream at Client (Receive data from the server).
			is = new BufferedReader(new InputStreamReader(socketOfClient.getInputStream()));

		} catch (UnknownHostException e) {
			e.printStackTrace();
			return 3;
		} catch (IOException e) {
			e.printStackTrace();
			return 3;
		}
		if(envoyerMsgAuServeur(identifiant + " " + motDePasse) != 0) 
			return 3;
		
		String res = lireMsgServeur();
		if(res != "OK") {
			seDeconnecter();
			String[] parts = res.split(" ");
			String identifiant = parts[0];
			if(parts[0] == "false")
				return 1;
			else
				return 2;
		}
		Gson gson = new Gson();	
		Utilisateur target = gson.fromJson(lireMsgServeur(), Utilisateur.class);
		nom = target.nom;
		prenom = target.prenom;
		
		return 0;
	}

	/**
	 * Se déconnecte
	 * 
	 * @return 0 si succès 1 sinon
	 */
	public int seDeconnecter() {
		try {
			if(envoyerMsgAuServeur("QUIT") != 0)
				return 1;

			os.close();
			is.close();
			socketOfClient.close();
		} catch (IOException e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	public void envoyerMessage(Message message, FilDiscussion filDiscussion) {

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

	/**
	 * @return the filDiscussion
	 */
	public Set<FilDiscussion> getFilDiscussion() {
		return null;
	}

	/**
	 * @param filDiscussion the filDiscussion to set
	 */
	public void setFilDiscussion(Set<FilDiscussion> filDiscussion) {
		
	}

	/**
	 * @return the groupes
	 */
	public Set<Groupe> getGroupes() {
		return null;
	}

	/**
	 * @param groupes the groupes to set
	 */
	public void setGroupes(Set<Groupe> groupes) {
		
	}

}