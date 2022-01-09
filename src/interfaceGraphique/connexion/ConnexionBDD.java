package interfaceGraphique.connexion;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import interfaceGraphique.gestion.ServeurFrame;
import serveur.BDD;
import serveur.Serveur;


public class ConnexionBDD extends Connexion {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1453012910886660784L;
	private JTextField urlBDD;
	
	public ConnexionBDD() throws Exception {
		super();
	}

	protected JPanel initLabelPanel() {
		JPanel labelPanel = new JPanel();
		labelPanel = super.initLabelPanel();
		JLabel urlLabel = new JLabel("URL Base de donnée:");
		urlLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		labelPanel.add(urlLabel);
		Component verticalStrut_5 = Box.createVerticalStrut(50);
		labelPanel.add(verticalStrut_5);
		return labelPanel;
	}
	
	protected JPanel initTextAreaPanel() {
		JPanel textAreaPanel = new JPanel();
		textAreaPanel = super.initTextAreaPanel();
		urlBDD = new JTextField("jdbc:mysql://localhost:3306/s5");
		textAreaPanel.add(urlBDD);
		Component verticalStrut_6 = Box.createVerticalStrut(20);
		textAreaPanel.add(verticalStrut_6);
		return textAreaPanel;
	}
	
	protected void btnConnexionListener(ActionEvent event) {
		String userName, password;
		char tempPassword[];
		ServeurFrame serveurFrame;
		
		userName = idTextField.getText();
		tempPassword = passwordField.getPassword();
		password = new String(tempPassword);
		BDD accesGestion = new BDD(userName, password, urlBDD.getText());
		if (accesGestion.seConnecter() == 0) {
			BDD accesServeur = new BDD(userName, password, urlBDD.getText());
			Serveur serveur = new Serveur(9999, accesServeur);
			if(serveur.demarrer() == 0) {
				System.out.println("Serveur demarr�");
				serveurFrame = new ServeurFrame(serveur, accesGestion);
				serveurFrame.setVisible(true);
			}
		}
		ConnexionBDD.this.dispose();
	}
}
