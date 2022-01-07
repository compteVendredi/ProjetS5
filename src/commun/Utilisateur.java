package commun;

import java.util.ArrayList;
import java.util.List;

/**
 * Représente un utilisateur
 */

public class Utilisateur {
	protected String identifiant;
	protected String motDePasse;
	protected String nom;
	protected String prenom;
	protected List<Groupe> listeGroupes = new ArrayList<>();
	
	/**
	 * Créer un utilisateur
	 * @param identifiant
	 * @param motDePasse
	 * @param nom
	 * @param prenom
	 */
	
	public Utilisateur(String identifiant, String motDePasse, String nom, String prenom) {
		this.identifiant = identifiant;
		this.motDePasse = motDePasse;
		this.nom = nom;
		this.prenom = prenom;
	}

	/**
	 * Ajoute un groupe à l'utiliateur (et s'ajoute dans ce groupe)
	 * @param groupe
	 */
	public void ajouterGroupe(Groupe groupe) {
		listeGroupes.add(groupe);
		groupe.ajouterUtilisateur(this);
	}
	
	/**
	 * Met à jour l'identifiant
	 * @param value
	 */
	
	public void setIdentifiant(String value) {
		identifiant = value;
	}

	/**
	 * Récupère l'identifiant
	 * @return l'identifiant
	 */
	
	public String getIdentifiant() {
		return identifiant;
	}

	/**
	 * Met à jour le mot de passe
	 * @param value
	 */
	
	public void setMotDePasse(String value) {
		motDePasse = value;
	}

	/**
	 * Récupère le mot de passe (hashé)
	 * @return motDePasse
	 */
	
	public String getMotDePasse() {
		return motDePasse;
	}
	
	/**
	 * Met à jour le nom
	 * @param value
	 */

	public void setNom(String value) {
		nom = value;
	}

	/**
	 * Récupère le nom
	 * @return nom
	 */
	
	public String getNom() {
		return nom;
	}

	/**
	 * Met à jour le prénom
	 * @param value
	 */
	
	public void setPrenom(String value) {
		prenom = value;
	}

	/**
	 * Récupère le prénom
	 * @return
	 */
	
	public String getPrenom() {
		return prenom;
	}

}
