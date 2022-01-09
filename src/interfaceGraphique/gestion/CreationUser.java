package interfaceGraphique.gestion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import serveur.BDD;
import utilitaire.HashUtil;

public class CreationUser extends JPanel {
	private JTextField idField;
	private JTextField passwordField;
	private JTextField nameField;
	private JTextField firstNameField;
	private BDD accesGestion;
	
	public CreationUser(BDD accesGestion, Container contentPane) {
		this.accesGestion = accesGestion;
		this.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_5.getLayout();
		this.add(panel_5, BorderLayout.NORTH);
		
		JLabel lblNewLabel_5 = new JLabel("Creation Utilisateur");
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
		panel_7.add(passwordField);
		passwordField.setColumns(10);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		panel_7.add(verticalStrut_4);
		
		JLabel lblNewLabel_3 = new JLabel("Nom");
		lblNewLabel_3.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_7.add(lblNewLabel_3);
		
		Component verticalStrut_5 = Box.createVerticalStrut(10);
		panel_7.add(verticalStrut_5);
		
		nameField = new JTextField();
		panel_7.add(nameField);
		nameField.setColumns(10);
		
		Component verticalStrut_6 = Box.createVerticalStrut(20);
		panel_7.add(verticalStrut_6);
		
		JLabel lblNewLabel_4 = new JLabel("Prenom");
		lblNewLabel_4.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_7.add(lblNewLabel_4);
		
		Component verticalStrut_7 = Box.createVerticalStrut(10);
		panel_7.add(verticalStrut_7);
		
		firstNameField = new JTextField();
		panel_7.add(firstNameField);
		firstNameField.setColumns(10);
		
		Component verticalStrut_9 = Box.createVerticalStrut(20);
		panel_7.add(verticalStrut_9);
		
		JButton btnNewButton_1 = new JButton("CREER");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String userName, userFirstName, password, idUser;
				idUser = idField.getText();
				userName = nameField.getText();
				userFirstName = firstNameField.getText();
				password = HashUtil.applySha256(passwordField.getText());
				CreationUser.this.accesGestion.ajouterUtilisateur(idUser , password, userName, userFirstName);
				CreationUser.this.removeAll();
				JPanel newPanel = new JPanel();
				CreationUser.this.add(newPanel);
				contentPane.revalidate();
			}
		});
		btnNewButton_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_7.add(btnNewButton_1);
		
		Component verticalStrut_10 = Box.createVerticalStrut(20);
		panel_7.add(verticalStrut_10);
		
		Component horizontalStrut = Box.createHorizontalStrut(150);
		this.add(horizontalStrut, BorderLayout.WEST);
		
		Component horizontalStrut_1 = Box.createHorizontalStrut(150);
		this.add(horizontalStrut_1, BorderLayout.EAST);
	}
}
