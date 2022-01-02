package serveur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utilisateur.FilDiscussion;
import utilisateur.Message;
import utilisateur.Utilisateur;

public class BDD {
	private String login;
	private String url;
	private String motDePasse;
	private Connection con;
	private Statement stmt;

	public BDD(String login, String motDePasse, String url) {
		this.login = login;
		this.motDePasse = motDePasse;
		this.url = url;
	}

	/**
	 * Essaye de se connecter
	 * 
	 * @return 0 si succès 1 si SQLTimeoutException 2 si SQLException
	 */
	public int seConnecter() {
		try {
			con = DriverManager.getConnection(url, login, motDePasse);
			stmt = con.createStatement();
		} catch (SQLTimeoutException e) {
			e.printStackTrace();
			return 1;
		} catch (SQLException e) {
			e.printStackTrace();
			return 2;
		}
		return 0;
	}

	/**
	 * Se déconnecte
	 * 
	 * @see seConnecter
	 */
	public int seDeconnecter() {
		con = null;
		return 0;

	}

	/**
	 * Renvoie le résultat d'une requête (lecture) sql
	 * 
	 * @param requete
	 * @return resultatRequete ou null si erreur
	 */
	private ResultSet requeteLecture(String requete) {
		ResultSet resultSet = null;
		try {
			resultSet = stmt.executeQuery(requete);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return resultSet;

	}

	/**
	 * Exécute une requête (écriture) sql
	 * 
	 * @param requete
	 * @return 0 si succès 1 sinon
	 */
	private int requeteEcriture(String requete) {
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}
		
	public boolean existeUser(String id_utilisateur) {
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT COUNT(id_utilisateur) AS total FROM Utilisateur WHERE id_utilisateur='" + id_utilisateur + "'");
		try {
			resultSet.next();
			if (resultSet.getInt("total") == 0) {
				return false;
			} else
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<Utilisateur> getAllUser() {
		List<Utilisateur> liste = new ArrayList<>();
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT * FROM Utilisateur");
		try {
			while (resultSet.next()) {
				Utilisateur user = new Utilisateur(resultSet.getString("id_utilisateur"),resultSet.getString("motDePasse"), resultSet.getString("nom"), resultSet.getString("prenom"));
				liste.add(user);	
			}
			return liste;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public String getHash(String id_utilisateur) {
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT motDePasse FROM Utilisateur WHERE id_utilisateur='" + id_utilisateur + "'");
		try {
			resultSet.next();
			return resultSet.getString("motDePasse");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// faire la transie de rouge -> orange
	// creer une table recu 
	public Map<Integer, String> getListFil(String id_utilisateur) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		List<Integer> liste = new ArrayList<>();
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT id_filDiscussion FROM Estdans WHERE id_utilisateur='" + id_utilisateur + "'");
		try {
			while (resultSet.next()) {
				liste.add(resultSet.getInt("id_filDiscussion"));
			}
			for (Integer id_fil : liste) {
				resultSet = requeteLecture("SELECT premierMessage FROM Fildiscussion WHERE id_filDiscussion="+ id_fil);
				resultSet.next();
				map.put(id_fil, resultSet.getString("premierMessage"));	
			}
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// add tout les message dans le fil	
	// faire le lu
	// changement etat orange -> vert
	public FilDiscussion getFil(int id_filDiscussion, String identifiant) {
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT * FROM Message WHERE id_filDiscussion = " + id_filDiscussion+ " ORDER BY date_emission LIMIT 1");
		Message message;
		FilDiscussion fil;
		try {
			resultSet.next();
			String id = resultSet.getString("id_utilisateur");
			String date = resultSet.getString("date_emission");
			String statut = resultSet.getString("statut");
			String contenu = resultSet.getString("contenu");
			resultSet = requeteLecture("SELECT * FROM Utilisateur WHERE id_utilisateur='" + id + "'");
			resultSet.next();
			message = new Message(id, resultSet.getString("nom"), resultSet.getString("prenom"), date, statut, contenu);
			resultSet = requeteLecture("SELECT id_groupe FROM FilDiscussion WHERE id_filDiscussion=" + id_filDiscussion);
			resultSet.next();
			fil = new FilDiscussion(message, id_filDiscussion, resultSet.getString("id_groupe"));
			resultSet = requeteLecture("SELECT * FROM `message` WHERE id_filDiscussion = " + id_filDiscussion + " ORDER BY date_emission");
			List<String[]> list = new ArrayList<>();
			while(resultSet.next()) {
				String[] tab = new String[] {resultSet.getString("id_utilisateur"),resultSet.getString("date_emission"),resultSet.getString("statut"),resultSet.getString("contenu")}; 
				list.add(tab);
			}
			for (String[] tab : list) {
				resultSet = requeteLecture("SELECT * FROM Utilisateur WHERE id_utilisateur='" + tab[0] + "'");
				resultSet.next();
				message = new Message(tab[0], resultSet.getString("nom"), resultSet.getString("prenom"), tab[1], tab[2], tab[3]);
				fil.ajouterMessage(message);
			}
			return fil;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}	

	public Utilisateur getUtilisateur(String id_utilisateur) {
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT * FROM Utilisateur WHERE id_utilisateur = '" + id_utilisateur + "'");
		Utilisateur user = null;
		try {
			resultSet.next();
			user = new Utilisateur(id_utilisateur, resultSet.getString("motDePasse"),resultSet.getString("nom"), resultSet.getString("prenom"));
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<String> getListGroupe() {
		List<String> liste = new ArrayList<>();
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT * FROM Groupe");
		try {
			while (resultSet.next()) {
				liste.add(resultSet.getString("id_groupe"));	
			}
			return liste;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getListGroupeUtilisateur(String id_utilisateur) {
		List<String> liste = new ArrayList<>();
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT * FROM Appartenance where id_utilisateur='" + id_utilisateur + "'");
		try {
			while (resultSet.next()) {
				liste.add(resultSet.getString("id_groupe"));	
			}
			return liste;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}	

	public FilDiscussion ajouterFil(String id_utilisateur, String date, String message, String id_groupe) {
		ResultSet resultSet = null;
		int num;
		resultSet = requeteLecture("SELECT id_filDiscussion FROM Fildiscussion ORDER BY id_filDiscussion DESC LIMIT 1"); 
		
		try {
			resultSet.next();
			num = resultSet.getInt("id_filDiscussion") + 1;
			requeteEcriture("INSERT INTO Fildiscussion VALUES (" + num + ", '" + id_groupe + "', '" + message + "')");
			requeteEcriture("INSERT INTO Message VALUES (NULL, '" + date + "', 'Rouge' ,'" + message + "','"+ id_utilisateur + "'," + num + ")");
			return this.getFil(num);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * @return 0 si succès 1 sinon
	 */
	public int ajouterMessage(String id_utilisateur, int id_fil, String date, String message) {
		return requeteEcriture("INSERT INTO Message VALUES (NULL,'" + date + "','Rouge','" + message + "','"
				+ id_utilisateur + "'," + id_fil + ")");
	}
	
	public int ajouterEstDans(String id_utilisateur, int id_filDiscussion) {
		return requeteEcriture("INSERT INTO EstDans VALUES ('"+ id_utilisateur +"', "+ id_filDiscussion + ")");
	}


	/* getNbMessageNonLu(String id_utilisateur, int id_filDiscussion) : int
	 * ajouterUser(String id_user, String hashMDP, String Nom, String prenom) : int (0 true, 1 error, 2 existe deja)
	 * ajouterGroupe(String role) : int (0 true, 1 error)
	 * 
	 * supUser(String ID_user) : (0 true, 1 error)
	 * supGroupe(Int ID_groupe) : (0 true, 1 error)
	 * supMessage(Int ID_message) : List id_user
	 * supFil(Int ID_fil) :(0 true, 1 error)
	 * 
	 * inserGroupe(String Id_user, Int ID_groupe) : (0 true, 1 error)
	 */

}
