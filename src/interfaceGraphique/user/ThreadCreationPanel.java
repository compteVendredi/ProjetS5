package interfaceGraphique.user;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import commun.Message;

public class ThreadCreationPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7823564978930425086L;
	private JTextField groupeTextField;
	private JTextArea messageTextArea;
	private NavigationPanel navigationPanel;
	
	public ThreadCreationPanel(NavigationPanel navigationPanel) {
		this.navigationPanel = navigationPanel;
		this.setLayout(new BorderLayout(0, 0));
		this.setBackground(Color.DARK_GRAY);
		
		JLabel titre = new JLabel("Cr�er un nouveau fil de discussion", JLabel.CENTER);
		this.add(titre, BorderLayout.NORTH);
		titre.setPreferredSize(new Dimension(0, 100));
		JPanel panelInter = new JPanel();
		panelInter.setLayout(new BorderLayout(5, 0));
		panelInter.setBackground(Color.GRAY);
		
		JPanel panelInterGroupe = new JPanel();
		panelInterGroupe.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
		panelInterGroupe.setPreferredSize(new Dimension(0, 100));
		JLabel groupeLabel = new JLabel("Groupe:", JLabel.LEFT);
		groupeTextField = new JTextField();
		groupeTextField.setColumns(10);
		panelInterGroupe.add(groupeLabel);
		panelInterGroupe.add(groupeTextField);
		panelInter.add(panelInterGroupe, BorderLayout.NORTH);
		
		JLabel messageLabel = new JLabel("Message:", JLabel.LEFT);
		JPanel panelInterMessage = new JPanel();
		panelInterMessage.setLayout(new BorderLayout(0, 0));
		panelInterMessage.add(messageLabel, BorderLayout.WEST);
		messageTextArea = new JTextArea();
		panelInterMessage.add(messageTextArea, BorderLayout.CENTER);
		panelInter.add(panelInterMessage, BorderLayout.CENTER);
	
		this.add(panelInter, BorderLayout.CENTER);
		
		JPanel panelInterSouth = new JPanel();
		panelInterSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
		this.add(panelInterSouth, BorderLayout.SOUTH);
		JButton creerFil = new JButton("CREER LE FIL");
		panelInterSouth.add(creerFil);
		creerFil.addActionListener(this::btnCreerFil);
	
	}
	
	private void btnCreerFil(ActionEvent event) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		Message message = new Message(UserFrame.getCurrentUser().getIdentifiant(), UserFrame.getCurrentUser().getNom(), UserFrame.getCurrentUser().getPrenom(), dtf.format(LocalDateTime.now()), "Rouge", messageTextArea.getText());
		UserFrame.getCurrentUser().ajouterFilDiscussion(message, groupeTextField.getText());
		navigationPanel.actualiserTree();
	}
}
