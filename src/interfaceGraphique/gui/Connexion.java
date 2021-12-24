package interfaceGraphique.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
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

import utilisateur.Utilisateur;

public class Connexion extends JFrame {
	private static final long serialVersionUID = -983602126963088259L;
	private JTextField textField;
	private JPasswordField passwordField;
	JButton btnConnexion = new JButton("CONNEXION");
	
	public Connexion() throws Exception {
		super("Connexion à InterUniv");
		Container contentPane;
		JPanel panel, panel_3, panel_4, panel_5;
		JLabel lblNewLabel_1, lblNewLabel, lblMotDePasse;
		GridBagConstraints gbc_passwordField;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(475, 350);
		setLocationRelativeTo(null);
		contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout(0, 0));
			
		panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
			
		panel_3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_3.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_3.setBackground(Color.DARK_GRAY);
		panel_3.setPreferredSize(new Dimension(0, 80));
		panel.add(panel_3, BorderLayout.NORTH);
		
		lblNewLabel_1 = new JLabel("InterUniv");
		lblNewLabel_1.setPreferredSize(new Dimension(140, 70));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 32));
		panel_3.add(lblNewLabel_1);
			
		panel_4 = new JPanel();
		panel_4.setBackground(Color.DARK_GRAY);
		panel_4.setPreferredSize(new Dimension(0, 80));
		panel.add(panel_4, BorderLayout.SOUTH);
			
		btnConnexion.setFont(new Font("Dialog", Font.BOLD, 12));
		btnConnexion.setForeground(Color.WHITE);
		btnConnexion.setBackground(Color.GRAY);
		btnConnexion.addActionListener(this::btnConnexionListener);
		panel_4.add(btnConnexion);
			
		panel_5 = new JPanel();
		panel_5.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_5.setBackground(Color.GRAY);
		panel_5.setForeground(Color.BLACK);
		panel.add(panel_5, BorderLayout.CENTER);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{32, 142, 278, 0};
		gbl_panel_5.rowHeights = new int[]{29, 35, 29, 0, 0};
		gbl_panel_5.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
			
		lblNewLabel = new JLabel("Identifiant:");
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 1;
		panel_5.add(lblNewLabel, gbc_lblNewLabel);
			
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.insets = new Insets(0, 0, 5, 0);
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 1;
		panel_5.add(textField, gbc_textField);
		textField.setColumns(10);
			
		lblMotDePasse = new JLabel("Mot de passe:");
		lblMotDePasse.setForeground(Color.WHITE);
		lblMotDePasse.setFont(new Font("Dialog", Font.BOLD, 22));
		GridBagConstraints gbc_lblMotDePasse = new GridBagConstraints();
		gbc_lblMotDePasse.anchor = GridBagConstraints.WEST;
		gbc_lblMotDePasse.insets = new Insets(0, 0, 0, 5);
		gbc_lblMotDePasse.gridx = 1;
		gbc_lblMotDePasse.gridy = 3;
		panel_5.add(lblMotDePasse, gbc_lblMotDePasse);
			
		passwordField = new JPasswordField();
		gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 3;
		panel_5.add(passwordField, gbc_passwordField);
	}
	
	private void btnConnexionListener(ActionEvent event) {
		String userName, password;
		char tempPassword[];
		Utilisateur utilisateur;
		
		userName = textField.getText();
		tempPassword = passwordField.getPassword();
		password = new String(tempPassword);
		utilisateur = new Utilisateur(userName, password, "", "");
		if (utilisateur.seConnecter("localhost", 9999) == 0) {
			User user = new User();
			user.setVisible(true);
			Connexion.this.dispose();
			utilisateur.seDeconnecter();
		}
	}
}
