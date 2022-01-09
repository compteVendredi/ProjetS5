package interfaceGraphique.connexion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import interfaceGraphique.utilisateur.UserFrame;

import javax.swing.Box;
import javax.swing.BoxLayout;

import utilisateur.ServiceUtilisateur;
import utilitaire.HashUtil;

public class Connexion extends JFrame {
	private static final long serialVersionUID = -983602126963088259L;
	protected JButton btnConnexion = new JButton("CONNEXION");
	protected JTextField idTextField;
	protected JPasswordField passwordField;
	
	public Connexion() throws Exception {
		super("Connexion à InterUniv");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(475, 350);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		getContentPane().add(initHeadPanel(), BorderLayout.NORTH);
		getContentPane().add(initTailPanel(), BorderLayout.SOUTH);
		getContentPane().add(initMidPanel(), BorderLayout.CENTER);
	}
	
	private JPanel initHeadPanel() {
		JPanel headPanel;
		JLabel logo;
		
		headPanel = new JPanel();
		headPanel.setBackground(Color.DARK_GRAY);
		headPanel.setPreferredSize(new Dimension(0, 80));
		headPanel.setLayout(new BoxLayout(headPanel, BoxLayout.X_AXIS));
		logo = new JLabel("InterUniv");
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
	
	protected JPanel initLabelPanel() {
		JPanel labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		labelPanel.add(verticalStrut);
		
		JLabel idLabel = new JLabel("Identifiant :");
		idLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		labelPanel.add(idLabel);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		labelPanel.add(verticalStrut_1);
		
		JLabel passwordLabel = new JLabel("Mot de passe :");
		passwordLabel.setFont(new Font("Dialog", Font.BOLD, 22));
		labelPanel.add(passwordLabel);
		
		Component verticalStrut_5 = Box.createVerticalStrut(20);
		labelPanel.add(verticalStrut_5);
		return labelPanel;
	}
	
	protected JPanel initTextAreaPanel() {
		JPanel textAreaPanel = new JPanel();
		textAreaPanel.setLayout(new BoxLayout(textAreaPanel, BoxLayout.Y_AXIS));
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		textAreaPanel.add(verticalStrut_2);
		
		idTextField = new JTextField();
		idTextField.setColumns(10);
		textAreaPanel.add(idTextField);
		
		Component verticalStrut_3 = Box.createVerticalStrut(50);
		textAreaPanel.add(verticalStrut_3);
		
		passwordField = new JPasswordField();
		textAreaPanel.add(passwordField);
		
		Component verticalStrut_4 = Box.createVerticalStrut(20);
		textAreaPanel.add(verticalStrut_4);
		return textAreaPanel;
	}
	
	private JPanel initMidPanel() {
		JPanel midPanel;
		
		midPanel = new JPanel();
		midPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		midPanel.setBackground(Color.GRAY);
		midPanel.setForeground(Color.BLACK);
		midPanel.setLayout(new BorderLayout(0, 0));
		
		midPanel.add(initLabelPanel(), BorderLayout.WEST);
		midPanel.add(initTextAreaPanel());
		return midPanel;
	}

	protected void btnConnexionListener(ActionEvent event) {
		String userName, password;
		char tempPassword[];
		ServiceUtilisateur utilisateur;
		
		userName = idTextField.getText();
		tempPassword = passwordField.getPassword();
		password = new String(tempPassword);
		utilisateur = new ServiceUtilisateur(userName, HashUtil.applySha256(password), "", "");
		if (utilisateur.seConnecter("localhost", 9999) == 0) {
			UserFrame.setCurrentUser(utilisateur);
			try {
			UserFrame userFrame = new UserFrame();
			
				userFrame.setVisible(true);
			}
			catch (Exception e) {
			}
			
			Connexion.this.dispose();
		}
	}
}