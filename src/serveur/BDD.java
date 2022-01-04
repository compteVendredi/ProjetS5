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
	
	public void actualiseStatutRecu(int id_filDiscussion) {
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT COUNT(id_utilisateur) AS total FROM Recu WHERE id_filDiscussion =" + id_filDiscussion);
		try {
			resultSet.next();
			int nb_recu = resultSet.getInt("total");
			resultSet = requeteLecture("SELECT id_groupe FROM fildiscussion WHERE id_fildiscussion =" + id_filDiscussion);
			resultSet.next();
			String groupe = resultSet.getString("id_groupe");
			resultSet = requeteLecture("SELECT COUNT(id_utilisateur) AS total FROM appartenance WHERE id_groupe ='" + groupe + "'");
			resultSet.next();
			if (nb_recu >= resultSet.getInt("total")+1) {
				requeteEcriture("UPDATE message SET statut = 'Orange' WHERE id_filDiscussion = " + id_filDiscussion + " AND statut = 'Rouge'");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// faire le lu
	// changement etat orange -> vert
	public FilDiscussion getFil(int id_filDiscussion, String id_utilisateur) {
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
			List<Integer> listId_message = new ArrayList<>();
			resultSet.next();
			while(resultSet.next()) {
				String[] tab = new String[] {resultSet.getString("id_utilisateur"),resultSet.getString("date_emission"),resultSet.getString("statut"),resultSet.getString("contenu")}; 
				listId_message.add(resultSet.getInt("id_message"));
				list.add(tab);
			}
			for (Integer id_message : listId_message) {
				requeteEcriture("INSERT INTO lu VALUES ("+ id_message +",'" + id_utilisateur + "')");
				actualiseStatutlu(id_message, id_filDiscussion);
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
	
	public FilDiscussion getFil2(int id_filDiscussion, String id_utilisateur) {
		ResultSet resultSet = null;
		List<Integer> list = new ArrayList<>();
		Message message;
		FilDiscussion fil;
		resultSet = requeteLecture("SELECT id_groupe FROM FilDiscussion WHERE id_filDiscussion=" + id_filDiscussion);
		try {
			resultSet.next();
			String id_groupe = resultSet.getString("id_groupe");
			resultSet = requeteLecture("SELECT message.*, utilisateur.nom, utilisateur.prenom FROM message INNER JOIN utilisateur WHERE message.id_utilisateur = utilisateur.id_utilisateur AND message.id_filDiscussion = " + id_filDiscussion + " ORDER BY message.date_emission");
			resultSet.next();
			message = new Message(resultSet.getString("id_utilisateur"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("date_emission"), resultSet.getString("statut"), resultSet.getString("contenu"));
			list.add(resultSet.getInt("id_message"));
			fil = new FilDiscussion(message, id_filDiscussion, id_groupe);
			while(resultSet.next()) {
				message = new Message(resultSet.getString("id_utilisateur"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("date_emission"), resultSet.getString("statut"), resultSet.getString("contenu"));
				list.add(resultSet.getInt("id_message"));
				fil.ajouterMessage(message);
			}
			for (Integer id_message : list) {
				requeteEcriture("INSERT INTO lu VALUES ("+ id_message +",'" + id_utilisateur + "')");
				actualiseStatutlu(id_message, id_filDiscussion);
			}
			return fil;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
		
		// Message(String id_utilisateur, String nom, String prenom, String date, String statut, String message)
		// FilDiscussion(Message ticket,int id_filDiscussion, String id_groupe)
		
		
		/* select message.*, utilisateur.nom, utilisateur.prenom
	    from message WHERE id_filDiscussion = 1 ORDER BY date_emission
	    join utilisateur on utilisateur.id_utilisateur = message.id_utilisateur
	    
	    */


	}
	
	public void actualiseStatutlu(int id_message, int id_filDiscussion) {
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT COUNT(id_utilisateur) AS total FROM lu WHERE id_message =" + id_message);
		try {
			resultSet.next();
			int nb_lu = resultSet.getInt("total");
			resultSet = requeteLecture("SELECT id_groupe FROM fildiscussion WHERE id_fildiscussion =" + id_filDiscussion);
			resultSet.next();
			String groupe = resultSet.getString("id_groupe");
			resultSet = requeteLecture("SELECT COUNT(id_utilisateur) AS total FROM appartenance WHERE id_groupe ='" + groupe + "'");
			resultSet.next();
			if (nb_lu >= resultSet.getInt("total")+1) {
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
		resultSet = requeteLecture("SELECT id_filDiscussion FROM Fildiscussion ORDER BY id_filDiscussion DESC LIMIT 1"); 
		List<String> liste = new ArrayList<>();
		try {
			resultSet.next();
			num = resultSet.getInt("id_filDiscussion") + 1;
			requeteEcriture("INSERT INTO Fildiscussion VALUES (" + num + ", '" + id_groupe + "', '" + message + "')");
			requeteEcriture("INSERT INTO Message VALUES (NULL, '" + date + "', 'Rouge' ,'" + message + "','"+ id_utilisateur + "'," + num + ")");
			resultSet = requeteLecture("SELECT id_utilisateur FROM appartenance WHERE id_groupe = '"+ id_groupe +"'");
			while(resultSet.next()) {
				liste.add(resultSet.getString("id_utilisateur"));
			}
			for (String id_user : liste) {
				this.ajouterEstDans(id_user, num);
			}
			
			this.ajouterEstDans(id_utilisateur, num);
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
		requeteEcriture("DELETE FROM Recu WHERE id_filDiscussion =" + id_fil);
		return requeteEcriture("INSERT INTO Message VALUES (NULL,'" + date + "','Rouge','" + message + "','"
				+ id_utilisateur + "'," + id_fil + ")");
	}
	// RETURNING id
	
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
