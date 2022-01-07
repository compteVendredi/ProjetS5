package interfaceGraphique.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import interfaceGraphique.gui.user.User;
import utilisateur.ServiceUtilisateur;
import utilitaire.HashUtil;

public class Connexion extends JFrame {
	private static final long serialVersionUID = -983602126963088259L;
	private JTextField idField;
	private JPasswordField passwordField;
	JButton btnConnexion = new JButton("CONNEXION");
	
	public Connexion() throws Exception {
		super("Connexion à InterUniv");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(475, 350);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(initBackGroundPanel());
	}
	
	private JPanel initBackGroundPanel() {
		JPanel backGroundPanel, headPanel, tailPanel, midPanel;
		
		backGroundPanel = new JPanel();
		backGroundPanel.setLayout(new BorderLayout(0, 0));
		headPanel = initHeadPanel();
		backGroundPanel.add(headPanel, BorderLayout.NORTH);
		tailPanel = initTailPanel();
		backGroundPanel.add(tailPanel, BorderLayout.SOUTH);
		midPanel = initMidPanel();
		backGroundPanel.add(midPanel, BorderLayout.CENTER);
		return backGroundPanel;
	}
	
	private JPanel initHeadPanel() {
		JPanel headPanel;
		JLabel logo;
		
		headPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) headPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		headPanel.setBackground(Color.DARK_GRAY);
		headPanel.setPreferredSize(new Dimension(0, 80));
		logo = new JLabel("InterUniv");
		logo.setPreferredSize(new Dimension(140, 70));
		logo.setHorizontalAlignment(SwingConstants.LEFT);
		logo.setForeground(Color.WHITE);
		logo.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 32));
		headPanel.add(logo);
		return headPanel;
	}
	
	private JPanel initTailPanel() {
		JPanel tailPanel;
		
		tailPanel = new JPanel();
		tailPanel.setBackground(Color.DARK_GRAY);
		tailPanel.setPreferredSize(new Dimension(0, 80));
		btnConnexion.setFont(new Font("Dialog", Font.BOLD, 12));
		btnConnexion.setForeground(Color.WHITE);
		btnConnexion.setBackground(Color.GRAY);
		btnConnexion.addActionListener(this::btnConnexionListener);
		tailPanel.add(btnConnexion);
		return tailPanel;
	}
	
	private JPanel initMidPanel() {
		JPanel midPanel;
		GridBagLayout gblMidPanel;
		JLabel idLabel, passwordLabel;
		
		midPanel = new JPanel();
		midPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		midPanel.setBackground(Color.GRAY);
		midPanel.setForeground(Color.BLACK);
		gblMidPanel = new GridBagLayout();
		gblMidPanel.columnWidths = new int[]{32, 142, 278, 0};
		gblMidPanel.rowHeights = new int[]{29, 35, 29, 0, 0};
		gblMidPanel.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gblMidPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		midPanel.setLayout(gblMidPanel);
		idLabel = new JLabel("Identifiant:");
		idLabel.setHorizontalAlignment(SwingConstants.LEFT);
		idLabel.setForeground(Color.WHITE);
		idLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		midPanel.add(idLabel, positionIdLabel());
		idField = new JTextField();
		idField.setColumns(10);
		midPanel.add(idField, positionIdField());
		passwordLabel = new JLabel("Mot de passe:");
		passwordLabel.setForeground(Color.WHITE);
		passwordLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		midPanel.add(passwordLabel, positionPasswordLabel());
		passwordField = new JPasswordField();
		midPanel.add(passwordField, positionPasswordField());
		return midPanel;
	}
	
	private GridBagConstraints positionIdLabel () {
		GridBagConstraints gbcIdLabel;
		
		gbcIdLabel = new GridBagConstraints();
		gbcIdLabel.anchor = GridBagConstraints.WEST;
		gbcIdLabel.insets = new Insets(0, 0, 5, 5);
		gbcIdLabel.gridx = 1;
		gbcIdLabel.gridy = 1;
		return gbcIdLabel;
	}
	
	private GridBagConstraints positionIdField() {
		GridBagConstraints gbcIdField;
		
		gbcIdField = new GridBagConstraints();
		gbcIdField.fill = GridBagConstraints.HORIZONTAL;
		gbcIdField.insets = new Insets(0, 0, 5, 0);
		gbcIdField.gridx = 2;
		gbcIdField.gridy = 1;
		return gbcIdField;
	}
	
	private GridBagConstraints positionPasswordLabel() {
		GridBagConstraints gbcPasswordLabel;
		
		gbcPasswordLabel = new GridBagConstraints();
		gbcPasswordLabel.anchor = GridBagConstraints.WEST;
		gbcPasswordLabel.insets = new Insets(0, 0, 0, 5);
		gbcPasswordLabel.gridx = 1;
		gbcPasswordLabel.gridy = 3;
		return gbcPasswordLabel;
	}
	
	private GridBagConstraints positionPasswordField() {
		GridBagConstraints gbcPasswordField;
		
		gbcPasswordField = new GridBagConstraints();
		gbcPasswordField.fill = GridBagConstraints.HORIZONTAL;
		gbcPasswordField.gridx = 2;
		gbcPasswordField.gridy = 3;
		return gbcPasswordField;
	}
	
	private void btnConnexionListener(ActionEvent event) {
		String userName, password;
		char tempPassword[];
		ServiceUtilisateur utilisateur;
		
		userName = idField.getText();
		tempPassword = passwordField.getPassword();
		password = new String(tempPassword);
		utilisateur = new ServiceUtilisateur(userName, HashUtil.applySha256(password), "", "");
		if (utilisateur.seConnecter("localhost", 9999) == 0) {
			User.setCurrentUser(utilisateur);
			User user = new User();
			user.setVisible(true);
			Connexion.this.dispose();
		}
	}
}