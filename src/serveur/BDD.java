package serveur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import commun.FilDiscussion;
import commun.Message;
import commun.Utilisateur;

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
	private int requeteEcritureReturn(String requete) {
		try {
			ResultSet resultSet = null;
			stmt.executeUpdate(requete,Statement.RETURN_GENERATED_KEYS);
			resultSet = stmt.getGeneratedKeys();
			resultSet.next();
			return resultSet.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
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
	
	public List<String[]> getListFil(String id_utilisateur) {
		List<String[]> list = new ArrayList<>();
		List<Integer> listId_fil = new ArrayList<>();
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT id_filDiscussion FROM EstDans WHERE id_utilisateur='" + id_utilisateur + "'");
		try {
			while (resultSet.next()) {
				listId_fil.add(resultSet.getInt("id_filDiscussion"));
			}
			for (Integer id_fil : listId_fil) {
				requeteEcriture("INSERT INTO Recu VALUES ("+ id_fil + ",'"+ id_utilisateur +"')");
				actualiseStatutRecu(id_fil);
				resultSet = requeteLecture("SELECT premierMessage FROM FilDiscussion WHERE id_filDiscussion="+ id_fil);
				resultSet.next();
				String[] fil = {Integer.toString(id_fil), resultSet.getString("premierMessage"), Integer.toString(nbMessageNonLu(id_fil, id_utilisateur))};
				list.add(fil);	
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private int nbMessageNonLu(int id_fil, String id_utilisateur) {
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT COUNT(Message.id_message) AS total FROM Message INNER JOIN Lu WHERE Message.id_message = Lu.id_message AND Message.id_FilDiscussion = "+id_fil+" AND Lu.id_utilisateur = '" + id_utilisateur + "'");
		try {
			resultSet.next();
			int nb_lu = resultSet.getInt("total");
			resultSet = requeteLecture("SELECT nb_message FROM FilDiscussion WHERE id_filDiscussion ="+ id_fil);
			resultSet.next();
			return resultSet.getInt("nb_message") - nb_lu;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	private void actualiseStatutRecu(int id_filDiscussion) {
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT COUNT(id_utilisateur) AS total FROM Recu WHERE id_filDiscussion =" + id_filDiscussion);
		try {
			resultSet.next();
			int nb_recu = resultSet.getInt("total");
			resultSet = requeteLecture("SELECT nb_utilisateur FROM FilDiscussion WHERE id_fildiscussion =" + id_filDiscussion);
			resultSet.next();
			if (nb_recu >= resultSet.getInt("nb_utilisateur")) {
				requeteEcriture("UPDATE Message SET statut = 'Orange' WHERE id_filDiscussion = " + id_filDiscussion + " AND statut = 'Rouge'");
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
			resultSet = requeteLecture("SELECT Message.*, Utilisateur.nom, Utilisateur.prenom FROM Message INNER JOIN Utilisateur WHERE Message.id_utilisateur = Utilisateur.id_utilisateur AND Message.id_filDiscussion = " + id_filDiscussion + " ORDER BY Message.date_emission");
			resultSet.next();
			// Premier message + creation du fil
			message = new Message(resultSet.getString("id_utilisateur"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("date_emission"), resultSet.getString("statut"), resultSet.getString("contenu"));
			list.add(resultSet.getInt("id_message"));
			fil = new FilDiscussion(message, id_filDiscussion, id_groupe);
			// Ajout des message dans le fil
			while(resultSet.next()) {
				message = new Message(resultSet.getString("id_utilisateur"), resultSet.getString("nom"), resultSet.getString("prenom"), resultSet.getString("date_emission"), resultSet.getString("statut"), resultSet.getString("contenu"));
				list.add(resultSet.getInt("id_message"));
				fil.ajouterMessage(message);
			}
			// Insert lu pour l'utilisateur pour chaque message 
			for (Integer id_message : list) {
				requeteEcriture("INSERT INTO Lu VALUES ("+ id_message +",'" + id_utilisateur + "')");
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
		resultSet = requeteLecture("SELECT COUNT(id_utilisateur) AS total FROM Lu WHERE id_message =" + id_message);
		try {
			resultSet.next();
			int nb_lu = resultSet.getInt("total");
			resultSet = requeteLecture("SELECT nb_utilisateur FROM FilDiscussion WHERE id_fildiscussion =" + id_filDiscussion);
			resultSet.next();
			if (nb_lu >= resultSet.getInt("nb_utilisateur")) {
				requeteEcriture("UPDATE Message SET statut = 'Vert' WHERE id_filDiscussion = " + id_filDiscussion + " AND statut = 'Orange'");
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
		resultSet = requeteLecture("SELECT COUNT(id_groupe) AS total FROM Appartenance WHERE id_utilisateur = '" + id_utilisateur + "' AND id_groupe = '" + id_groupe + "'"); 
		List<String> liste = new ArrayList<>();
		try {
			resultSet.next();
			estDansGroupe = resultSet.getInt("total");
			resultSet = requeteLecture("SELECT nb_utilisateur FROM Groupe WHERE id_groupe = '"+ id_groupe +"'"); 
			resultSet.next();
			nb_utilisateur = resultSet.getInt("nb_utilisateur");
			if (estDansGroupe == 0) {
				nb_utilisateur++;
			}
			// Insert fil + message
			num = requeteEcritureReturn("INSERT INTO FilDiscussion VALUES (NULL, '" + id_groupe + "', '" + message + "',"+ nb_utilisateur +", 1)"); 
			requeteEcriture("INSERT INTO Message VALUES (NULL, '" + date + "', 'Rouge' ,'" + message + "','"+ id_utilisateur + "'," + num + ")");
			// Ajoute les utilisateurs dans apparenance 
			resultSet = requeteLecture("SELECT id_utilisateur FROM Appartenance WHERE id_groupe = '"+ id_groupe +"'");
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
		requeteEcriture("DELETE FROM Recu WHERE id_filDiscussion =" + id_fil);
		int num = requeteEcritureReturn("INSERT INTO Message VALUES (NULL,'" + date + "','Rouge','" + message + "','"+ id_utilisateur + "'," + id_fil + ")");
		requeteEcriture("Update FilDiscussion Set nb_message = nb_message + 1 Where id_filDiscussion =" + id_fil);
		requeteEcriture("INSERT INTO Lu VALUES ("+ num +",'" + id_utilisateur + "')");
		return 0;
	}
	
	private int ajouterEstDans(String id_utilisateur, int id_filDiscussion) {
		return requeteEcriture("INSERT INTO EstDans VALUES ('"+ id_utilisateur +"', "+ id_filDiscussion + ")");
	}

	public int ajouterUser(String id_user, String hashMDP, String Nom, String prenom) {
		return requeteEcriture("INSERT INTO utilisateur VALUES ('"+id_user+"', '"+hashMDP+"', '"+Nom+"', '"+prenom+"')");
	}
	
	public int ajouterGroupe(String role){
		return requeteEcriture("INSERT INTO Lu VALUES ('"+role+"',0)");
	}
	
	public int supprimerUser(String id_user) {
		ResultSet resultSet = null;
		List<String> listId_groupe = new ArrayList<>();
		List<Integer> listId_fil = new ArrayList<>();
		resultSet = requeteLecture("SELECT id_groupe FROM Appartenance WHERE id_utilisateur = '"+id_user+"'");
		try {
			while (resultSet.next()) {
				listId_groupe.add(resultSet.getString("id_groupe"));
			}
			for (String id_groupe : listId_groupe) {
				requeteEcriture("Update Groupe Set nb_utilisateur = nb_utilisateur - 1 Where id_groupe ='" + id_groupe +"'");
			}
			resultSet = requeteLecture("SELECT id_filDiscussion FROM EstDans WHERE id_utilisateur = '"+id_user+"'");
			while (resultSet.next()) {
				listId_fil.add(resultSet.getInt("id_filDiscussion"));
			}
			for (Integer id_fil : listId_fil) {
				resultSet = requeteLecture("SELECT COUNT(id_message) AS total FROM Message WHERE id_utilisateur = '"+id_user+"' AND id_filDiscussion = "+ id_fil);
				resultSet.next();
				int nb_message = resultSet.getInt("total");
				requeteEcriture("Update FilDiscussion Set nb_message = nb_message - "+nb_message+" Where id_filDiscussion = "+ id_fil);
				requeteEcriture("Update FilDiscussion Set nb_utilisateur = nb_utilisateur - 1 Where id_filDiscussion = " + id_fil);
			}
			return requeteEcriture("DELETE FROM Utilisateur WHERE id_utilisateur='"+id_user+"'");
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}	
	}
	
	public int supprimerMessage(int id_message) {
		ResultSet resultSet = null;
		resultSet = requeteLecture("SELECT id_filDiscussion FROM Message WHERE id_message = "+ id_message);
		try {
			resultSet.next();
			int id_fil = resultSet.getInt("id_filDiscussion");
			requeteEcriture("Update FilDiscussion Set nb_message = nb_message - 1 Where id_filDiscussion = "+ id_fil);
			return requeteEcriture("DELETE FROM Message WHERE id_message= " + id_message);
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}	
	}
	
	public int supprimerGroupe(String id_groupe) {
		return requeteEcriture("DELETE FROM Groupe WHERE id_groupe='"+ id_groupe+"' ");
	}
	
	public int supprimerFil(int id_fil) {
		requeteEcriture("DELETE FROM Message WHERE id_filDiscussion= " + id_fil);
		return requeteEcriture("DELETE FROM FilDiscussion WHERE id_filDiscussion= " + id_fil);
	}
	
	public int insertGroupe(String id_user, String id_groupe) {
		ResultSet resultSet = null;
		List<Integer> listId_fil = new ArrayList<>();
		resultSet = requeteLecture("SELECT id_fildiscussion FROM FilDiscussion WHERE id_groupe = '"+id_groupe+"'");
		try {
			while(resultSet.next()) {
				listId_fil.add(resultSet.getInt("id_fildiscussion"));
			}
			for (Integer id_fil : listId_fil) {
				requeteEcriture("INSERT INTO EstDans VALUES ('"+id_user+"', "+id_fil+")");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 1;
		}	
		requeteEcriture("INSERT INTO Appartenance VALUES ('"+id_groupe+"', '"+id_user+"')");
		return requeteEcriture("Update FilDiscussion Set nb_utilisateur = nb_utilisateur + 1 Where id_groupe = '"+id_groupe+"'");
	}	
	
	public int updateUtilisateurMDP(String id_utilisateur,String mdp) {
		return requeteEcriture("Update Utilisateur Set motDePasse = '"+mdp+"' Where id_utilisateur = '"+id_utilisateur+"'");
	}
	
	public int updateUtilisateurNom(String id_utilisateur,String nom) {
		return requeteEcriture("Update Utilisateur Set nom = '"+nom+"' Where id_utilisateur = '"+id_utilisateur+"'");
	}
	
	public int updateUtilisateurPrenom(String id_utilisateur,String prenom) {
		return requeteEcriture("Update Utilisateur Set prenom = '"+prenom+"' Where id_utilisateur = '"+id_utilisateur+"'");
	}
	
}
