package interfaceGraphique.gestion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ListIterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import interfaceGraphique.connexion.Connexion;
import interfaceGraphique.user.UserFrame;
import serveur.BDD;
import utilisateur.ServiceUtilisateur;
import utilitaire.HashUtil;

public class UserPanel extends JPanel {
	private JTextField idField;
	private JTextField passwordField;
	private JTextField firstNameField;
	private JTextField nameField;
	private JButton btnNewButton_1;
	private BDD accesGestion;
	private String id;
	private Container contentPane;
	private JOptionPane optionPane;
	
	public UserPanel(BDD accesGestion, String id, String password, String firstName, String name, Container contentPane) {
		this.contentPane = contentPane;
		this.id = id;
		this.accesGestion = accesGestion;
		this.setLayout(new BorderLayout(0, 0));
		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_5.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		this.add(panel_5, BorderLayout.NORTH);
		
		JLabel lblNewLabel_5 = new JLabel("Information Utilisateur");
		JButton deleteBtn = new JButton("DELETE");
		deleteBtn.addActionListener(this::btnDeleteUserListener);
		panel_5.add(deleteBtn);
		panel_5.add(lblNewLabel_5);
		
		JPanel panel_7 = new JPanel();
		this.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel_7.add(verticalStrut);
		
		JLabel lblNewLabel_1 = new JLabel("Identifiant");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_7.add(lblNewLabel_1);
		
		Component verticalStrut_1 = Box.createVerticalStrut(10);
		panel_7.add(verticalStrut_1);
		
		idField = new JTextField();
		idField.setText(id);
		panel_7.add(idField);
		idField.setColumns(10);
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		panel_7.add(verticalStrut_2);
		
		JLabel lblNewLabel_2 = new JLabel("Mot de passe");
		lblNewLabel_2.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_7.add(lblNewLabel_2);
		
		Component verticalStrut_3 = Box.createVerticalStrut(10);
		panel_7.add(verticalStrut_3);
		
		passwordField = new JTextField();
		passwordField.setText(password);
		panel_7.add(passwordField);
		passwordField.setColumns(10);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		panel_7.add(verticalStrut_4);
		
		JLabel lblNewLabel_3 = new JLabel("Pr�nom");
		lblNewLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_7.add(lblNewLabel_3);
		
		Component verticalStrut_5 = Box.createVerticalStrut(10);
		panel_7.add(verticalStrut_5);
		
		firstNameField = new JTextField();
		firstNameField.setText(firstName);
		panel_7.add(firstNameField);
		firstNameField.setColumns(10);
		
		Component verticalStrut_6 = Box.createVerticalStrut(20);
		panel_7.add(verticalStrut_6);
		
		JLabel lblNewLabel_4 = new JLabel("Nom");
		lblNewLabel_4.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_7.add(lblNewLabel_4);
		
		Component verticalStrut_7 = Box.createVerticalStrut(10);
		panel_7.add(verticalStrut_7);
		
		nameField = new JTextField();
		nameField.setText(name);
		panel_7.add(nameField);
		nameField.setColumns(10);
		
		Component verticalStrut_8 = Box.createVerticalStrut(20);
		panel_7.add(verticalStrut_8);
		
		JPanel panel_6 = new JPanel();
		panel_7.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		JPanel titreListe = new JPanel();
		titreListe.setLayout(new BoxLayout(titreListe, BoxLayout.X_AXIS));
		JLabel lblNewLabel_6 = new JLabel("Groupes");
		titreListe.add(lblNewLabel_6);
		Component horizontalStrut10 = Box.createHorizontalStrut(20);
		titreListe.add(horizontalStrut10);
		JButton btnNewButton_2 = new JButton("ADD");
		btnNewButton_2.addActionListener(this::btnAddListener);
		titreListe.add(btnNewButton_2);
		
		panel_7.add(titreListe);
		
		JPanel listePane = new JPanel();
		JScrollPane scrollPane = new JScrollPane(listePane, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listePane.setLayout(new BoxLayout(listePane, BoxLayout.Y_AXIS));
		List<String> listeGroupes = accesGestion.getListGroupeUtilisateur(id);
		for (ListIterator<String> iterateur = listeGroupes.listIterator(); iterateur.hasNext();) {
			String idGroupe = iterateur.next();
			JPanel newPanel = new AddSupprUser(id, idGroupe, accesGestion);
			listePane.add(newPanel);
		}
		panel_7.add(scrollPane, BorderLayout.CENTER);
		
		Component verticalStrut_9 = Box.createVerticalStrut(20);
		panel_7.add(verticalStrut_9);
		
		btnNewButton_1 = new JButton("MODIFIER");
		btnNewButton_1.addActionListener(this::btnModifListener);
		btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_7.add(btnNewButton_1);
		
		Component verticalStrut_10 = Box.createVerticalStrut(20);
		panel_7.add(verticalStrut_10);
		
		Component horizontalStrut = Box.createHorizontalStrut(150);
		this.add(horizontalStrut, BorderLayout.WEST);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(150);
		this.add(horizontalStrut_1, BorderLayout.EAST);
		
	}
	
	private void btnAddListener(ActionEvent event) {
		List<String> listeGroupe = accesGestion.getListGroupe();
		Object[] possibilities = new Object[listeGroupe.size()];
		int nb = 0;
		for(ListIterator<String> iterateur = listeGroupe.listIterator(); iterateur.hasNext() && nb < listeGroupe.size();) {
			String nomGroupe = iterateur.next();
			possibilities[nb] = (String) nomGroupe; 
			nb++;
		}
		
		String s = (String)JOptionPane.showInputDialog(
							this,
		                    "Choissisez un groupe à ajouter\n",
		                    "Ajout d'un groupe",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null, possibilities,
		                    possibilities[0]);

		//If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			accesGestion.insertGroupe(id, s);
		}
		
	}
	
	private void btnModifListener(ActionEvent event) {
		accesGestion.updateUtilisateurNom(id,nameField.getText());
		accesGestion.updateUtilisateurPrenom(id, firstNameField.getText());
		accesGestion.updateUtilisateurMDP(id, passwordField.getText());
	}
	
	private void btnDeleteUserListener(ActionEvent event) {
		this.removeAll();
		accesGestion.supprimerUtilisateur(id);
		this.add(new JPanel(), BorderLayout.CENTER);
		contentPane.revalidate();
	}
}
