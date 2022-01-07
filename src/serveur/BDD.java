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
	
	/**
	 * Exécute une requête (écriture) sql avec un retour
	 * 
	 * @param requete
	 * @return ResultSet
	 */
	private ResultSet requeteEcritureReturn(String requete) {
		try {
			ResultSet resultSet = null;
			stmt.executeUpdate(requete,Statement.RETURN_GENERATED_KEYS);
			resultSet = stmt.getGeneratedKeys();
			return resultSet;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
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
				requeteEcriture("INSERT INTO Recu VALUES ("+ id_fil + ",'"+ id_utilisateur +"')");
				actualiseStatutRecu(id_fil);
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
	
	private void actualiseStatutRecu(int id_filDiscussion) {
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT COUNT(id_utilisateur) AS total FROM Recu WHERE id_filDiscussion =" + id_filDiscussion);
		try {
			resultSet.next();
			int nb_recu = resultSet.getInt("total");
			resultSet = requeteLecture("SELECT nb_utilisateur FROM fildiscussion WHERE id_fildiscussion =" + id_filDiscussion);
			resultSet.next();
			if (nb_recu >= resultSet.getInt("nb_utilisateur")) {
				requeteEcriture("UPDATE message SET statut = 'Orange' WHERE id_filDiscussion = " + id_filDiscussion + " AND statut = 'Rouge'");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public FilDiscussion getFil(int id_filDiscussion, String id_utilisateur) {
		ResultSet resultSet = null;
		List<Integer> list = new ArrayList<>();
		Message message;
		FilDiscussion fil;
		// Select le groupe de l'utilisateur 
		resultSet = requeteLecture("SELECT id_groupe FROM FilDiscussion WHERE id_filDiscussion=" + id_filDiscussion);
		try {
			resultSet.next();
			String id_groupe = resultSet.getString("id_groupe");
			// Select tout les messages + nom, prenom de l'utilisateur du message
			resultSet = requeteLecture("SELECT message.*, utilisateur.nom, utilisateur.prenom FROM message INNER JOIN utilisateur WHERE message.id_utilisateur = utilisateur.id_utilisateur AND message.id_filDiscussion = " + id_filDiscussion + " ORDER BY message.date_emission");
			resultSet.next();
			// Premier message + creation du fil
			message = new Message(resultSet.getString("id_utilisateur"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("date_emission"), resultSet.getString("statut"), resultSet.getString("contenu"));
			list.add(resultSet.getInt("id_message"));
			fil = new FilDiscussion(message, id_filDiscussion, id_groupe,1);
			// Ajout des message dans le fil
			while(resultSet.next()) {
				message = new Message(resultSet.getString("id_utilisateur"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("date_emission"), resultSet.getString("statut"), resultSet.getString("contenu"));
				list.add(resultSet.getInt("id_message"));
				fil.ajouterMessage(message);
			}
			// Insert lu pour l'utilisateur pour chaque message 
			for (Integer id_message : list) {
				requeteEcriture("INSERT INTO lu VALUES ("+ id_message +",'" + id_utilisateur + "')");
				actualiseStatutlu(id_message, id_filDiscussion);
			}
			return fil;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void actualiseStatutlu(int id_message, int id_filDiscussion) {
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT COUNT(id_utilisateur) AS total FROM lu WHERE id_message =" + id_message);
		try {
			resultSet.next();
			int nb_lu = resultSet.getInt("total");
			resultSet = requeteLecture("SELECT nb_utilisateur FROM fildiscussion WHERE id_fildiscussion =" + id_filDiscussion);
			resultSet.next();
			if (nb_lu >= resultSet.getInt("nb_utilisateur")) {
				requeteEcriture("UPDATE message SET statut = 'Vert' WHERE id_filDiscussion = " + id_filDiscussion + " AND statut = 'Orange'");
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		int nb_utilisateur;
		int estDansGroupe;
		// Check si l'utilisateur est dans le groupe
		resultSet = requeteLecture("SELECT COUNT(id_groupe) AS total FROM appartenance WHERE id_utilisateur = '" + id_utilisateur + "' AND id_groupe = '" + id_groupe + "'"); 
		List<String> liste = new ArrayList<>();
		try {
			resultSet.next();
			estDansGroupe = resultSet.getInt("total");
			resultSet = requeteLecture("SELECT nb_utilisateur FROM groupe WHERE id_groupe = '"+ id_groupe +"'"); 
			resultSet.next();
			nb_utilisateur = resultSet.getInt("nb_utilisateur");
			if (estDansGroupe == 0) {
				nb_utilisateur++;
			}
			// Insert fil + message
			resultSet = requeteEcritureReturn("INSERT INTO Fildiscussion VALUES (NULL, '" + id_groupe + "', '" + message + "',"+ nb_utilisateur +"); SELECT LAST_INSERT_ID() AS return"); 
			resultSet.next();
			num = resultSet.getInt("return");
			requeteEcriture("INSERT INTO Message VALUES (NULL, '" + date + "', 'Rouge' ,'" + message + "','"+ id_utilisateur + "'," + num + ")");
			// Ajoute les utilisateurs dans apparenance 
			resultSet = requeteLecture("SELECT id_utilisateur FROM appartenance WHERE id_groupe = '"+ id_groupe +"'");
			while(resultSet.next()) {
				liste.add(resultSet.getString("id_utilisateur"));
			}
			for (String id_user : liste) {
				this.ajouterEstDans(id_user, num);
			}
			if (estDansGroupe == 0) {
				this.ajouterEstDans(id_utilisateur, num);
			}
			// Insert lu pour l'utilisateur 
			requeteEcriture("INSERT INTO Recu VALUES ("+ num + ",'"+ id_utilisateur +"')");
			return this.getFil(num, id_utilisateur);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * @return 0 si succès 1 sinon
	 */
	public int ajouterMessage(String id_utilisateur, int id_fil, String date, String message) {
		ResultSet resultSet = null;
		try {
			requeteEcriture("DELETE FROM Recu WHERE id_filDiscussion =" + id_fil);
			resultSet = requeteLecture("INSERT INTO Message VALUES (NULL,'" + date + "','Rouge','" + message + "','"
					+ id_utilisateur + "'," + id_fil + "); SELECT LAST_INSERT_ID() AS return");
			resultSet.next();
			requeteEcriture("INSERT INTO lu VALUES ("+ resultSet.getInt("return") +",'" + id_utilisateur + "')");
			return 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
		
	}
	
	private int ajouterEstDans(String id_utilisateur, int id_filDiscussion) {
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
