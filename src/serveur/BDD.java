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
import java.util.Set;

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
	public ResultSet requeteLecture(String requete) {
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
	public int requeteEcriture(String requete) {
		try {
			stmt.executeUpdate(requete);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}
		return 0;
	}

	/**
	 * Récupère tous les utilisateurs de la BDD
	 * 
	 * @return utilisateurs
	 */
	public Set<Utilisateur> getAllUtilisateurs() {
		return null;
	}

	public void setLogin(String value) {
		login = value;
	}

	public String getLogin() {
		return login;
	}

	public void setMotDePasse(String value) {
		motDePasse = value;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setUrl(String value) {
		url = value;
	}

	public String getUrl() {
		return url;
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
		ResultSet resultSet = null;
		ResultSet resultSet2 = null;
		resultSet = requeteLecture("SELECT id_filDiscussion FROM Estdans WHERE id_utilisateur='" + id_utilisateur + "'");
		try {
			while (resultSet.next()) {
				resultSet2 = requeteLecture("SELECT id_filDiscussion FROM Fildiscussion WHERE id_filDiscussion='"+ resultSet.getInt("id_filDiscussion") + "'");
				resultSet2.next();
				map.put(resultSet.getInt("id_filDiscussion"), resultSet2.getString("premierMessage"));	
			}
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public FilDiscussion getFil(int id_filDiscussion) {
		ResultSet resultSetFirst = null;
		ResultSet resultSet = null;
		resultSetFirst = requeteLecture("SELECT * FROM Message WHERE id_filDiscussion = " + id_filDiscussion+ " ORDER BY date_emission LIMIT 1");
		Message message = null;
		FilDiscussion fil = null;
		try {
			resultSetFirst.next();
			resultSet = requeteLecture("SELECT * FROM Utilisateur WHERE id_utilisateur='" + resultSetFirst.getString("id_utilisateur") + "'");
			resultSet.next();
			message = new Message(resultSetFirst.getString("id_utilisateur"), resultSet.getString("nom"),
					resultSet.getString("prenom"), resultSetFirst.getString("date_emission"),
					resultSetFirst.getString("statut"), resultSetFirst.getString("contenue"));
			fil = new FilDiscussion(message, id_filDiscussion, resultSetFirst.getInt("id_groupe"));
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

	public Map<Integer, String> getListGroupe() {
		Map<Integer, String> map = new HashMap<Integer, String>();
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT * FROM Groupe");
		try {
			while (resultSet.next()) {
				map.put(resultSet.getInt("id_groupe"), resultSet.getString("role"));	
			}
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String getGroupe(int id_groupe) {
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT role FROM Groupe WHERE id_groupe = '" + id_groupe + "'");
		try {
			resultSet.next();
			return resultSet.getString("role");
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public FilDiscussion ajouterFil(String id_utilisateur, String date, String message, int id_groupe) {
		ResultSet resultSet = null;
		int num;
		resultSet = requeteLecture("SELECT id_filDiscussion FROM Fildiscussion ORDER BY id_filDiscussion DESC LIMIT 1"); 
		
		try {
			resultSet.next();
			num = resultSet.getInt("id_filDiscussion") + 1;
			requeteEcriture("INSERT INTO fildiscussion VALUES (" + num + ", " + id_groupe + ", '" + message + "')");
			requeteEcriture("INSERT INTO message VALUES (NULL, '" + date + "', 'Rouge' ,'" + message + "','"+ id_utilisateur + "'," + num + ")");
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

//	INSERT INTO fildiscussion VALUES (3, 1, 'Mon message');
//	INSERT INTO message VALUES (NULL, '2021-12-31 12:35:59', 'Rouge','Mon message','Dpt01',3)

	/*-- existeUser(String Id_user) : boolean
	 *
	 *-- getAllUser() : List user
	 *-- getHash(String id_user) : String 32 
	 *-- getListFil(String id_user) : Map<int><String> 
	 *-- getFil(ID_fil) : Fil fil
	 *-- getUtilisateur(String Id_user) : User user
	 *-- getListGroupe() : Map<int><String> 
	 *-- getGroupe(int id_groupe) String groupe
	 * 
	 *-- ajouterFil(String Id_user, Date date, String message, int Id_groupe) : Fil fil
	 *-- ajouterMessage(String id_user,Int id_fil, Date date, String message) : int (0 true, 1 error)
	 * ajouterUser(String id_user, String hashMDP, String Nom, String prenom) : int (0 true, 1 error, 2 existe deja)
	 * ajouterGroupe(String role) : int (0 true, 1 error)
	 * 
	 * supUser(String ID_user) : (0 true, 1 error)
	 * supGroupe(Int ID_groupe) : (0 true, 1 error)
	 * supMessage(Int ID_message) : List id_user
	 * supFil(Int ID_fil) :(0 true, 1 error)
	 * 
	 * inserGroupe(String Id_user, Int ID_groupe) : (0 true, 1 error)
	 * 
	 * SELECT * FROM `message` WHERE id_filDiscussion = 1 ORDER BY date_emission LIMIT 1
	 */

}
