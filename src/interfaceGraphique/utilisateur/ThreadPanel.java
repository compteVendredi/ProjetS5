package interfaceGraphique.utilisateur;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ListIterator;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;

import commun.FilDiscussion;
import commun.Message;
import utilisateur.ServiceUtilisateur;

public class ThreadPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8327177803774656189L;
	private JTextArea messageTextArea;
	private FilDiscussion fd;
	JScrollPane scrollPane_2;
	private JPanel panel_2;
	private int idFil;
	
	public ThreadPanel(int idFil, ServiceUtilisateur currentUser) {
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(new BorderLayout(0, 0));
		this.idFil = idFil;
		//panel d'envoi
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setPreferredSize(new Dimension(100, 100));
		panel_1.setLayout(new BorderLayout(0, 0));
		this.add(panel_1, BorderLayout.SOUTH);
		
			//constitution du panel d'evoi
			JPanel panel_3 = new JPanel();
			panel_3.setBackground(Color.DARK_GRAY);
			panel_1.add(panel_3, BorderLayout.EAST);
				//bouton envoi
			JButton envoyerMessage = new JButton("ENVOYER");
			envoyerMessage.addActionListener(this::btnEnvoyerMessageListener);
			panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			panel_3.add(envoyerMessage);
		
			JScrollPane scrollPane = new JScrollPane();
			panel_1.add(scrollPane, BorderLayout.CENTER);
		
			messageTextArea = new JTextArea();
			scrollPane.setViewportView(messageTextArea);
		
		//panel affichant les sujets
		this.fd = fd;
		
		panel_2 = new JPanel();
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		panel_2.setBorder(BorderFactory.createLineBorder(Color.red));
		panel_2.setPreferredSize(new Dimension(0, 1500));
		scrollPane_2 = new JScrollPane(panel_2);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane_2, BorderLayout.CENTER);
	
		List<Message> lm = currentUser.getFilDiscussion(fd.getId_filDiscussion()).getMessages();
		for(ListIterator<Message> iterareur = lm.listIterator(); iterareur.hasNext();) {	
			Message message = iterareur.next();

			JPanel panel_5 = new JPanel();
			panel_5.setBackground(Color.DARK_GRAY);
			panel_5.setPreferredSize(new Dimension(460, 140));
			panel_2.add(panel_5);
			panel_5.setLayout(new BorderLayout(0, 0));
		
			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_5.add(scrollPane_1, BorderLayout.CENTER);
		
			JTextArea txtrLoremIpsumDolor = new JTextArea();
			txtrLoremIpsumDolor.setBackground(selectionnerCouleur(message.getStatut()));
			txtrLoremIpsumDolor.setLineWrap(true);
			txtrLoremIpsumDolor.setText(message.getMessage());
			txtrLoremIpsumDolor.setEditable(false);
			scrollPane_1.setViewportView(txtrLoremIpsumDolor);
		
			JLabel lblNewLabel = new JLabel("Par " + message.getPrenom() + " " + message.getNom() + " le " + message.getDate());
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			panel_5.add(lblNewLabel, BorderLayout.NORTH);
		}
	}
	
	public void actualiserFil() {
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_2.setBorder(BorderFactory.createLineBorder(Color.red));
		panel_2.setPreferredSize(new Dimension(0, 1500));
		this.remove(scrollPane_2);
		this.revalidate();
		scrollPane_2 = new JScrollPane(panel_2);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane_2, BorderLayout.CENTER);
		List<Message> lm = UserFrame.getCurrentUser().getFilDiscussion(this.idFil).getMessages();
		for(ListIterator<Message> iterareur = lm.listIterator(); iterareur.hasNext();) {	
			Message message = iterareur.next();

			JPanel panel_5 = new JPanel();
			panel_5.setBackground(Color.DARK_GRAY);
			panel_5.setPreferredSize(new Dimension(460, 140));
			panel_2.add(panel_5);
			panel_5.setLayout(new BorderLayout(0, 0));
		
			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_5.add(scrollPane_1, BorderLayout.CENTER);
		
			JTextArea txtrLoremIpsumDolor = new JTextArea();
			txtrLoremIpsumDolor.setBackground(selectionnerCouleur(message.getStatut()));
			txtrLoremIpsumDolor.setLineWrap(true);
			txtrLoremIpsumDolor.setText(message.getMessage());
			txtrLoremIpsumDolor.setEditable(false);
			scrollPane_1.setViewportView(txtrLoremIpsumDolor);
		
			JLabel lblNewLabel = new JLabel("Par " + message.getPrenom() + " " + message.getNom() + " le " + message.getDate());
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			panel_5.add(lblNewLabel, BorderLayout.NORTH);
		}
	}
	
	private Color selectionnerCouleur(String couleur) {
		switch(couleur) {
		case "Rouge": return Color.RED;
		case "Vert": return Color.GREEN;
		case "Orange": return Color.ORANGE;
		default: return Color.GRAY;
		}
	}
	
	private void btnEnvoyerMessageListener(ActionEvent event) {
		Message message;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		message = new Message(UserFrame.getCurrentUser().getIdentifiant(), UserFrame.getCurrentUser().getNom(), UserFrame.getCurrentUser().getPrenom(), dtf.format(LocalDateTime.now()), "Rouge", messageTextArea.getText());
		UserFrame.getCurrentUser().envoyerMessage(message, fd);
		
		//creation panelmessage en faire une classe
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.DARK_GRAY);
		panel_5.setPreferredSize(new Dimension(460, 140));
		panel_2.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
	
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_5.add(scrollPane_1, BorderLayout.CENTER);
	
		JTextArea txtrLoremIpsumDolor = new JTextArea();
		txtrLoremIpsumDolor.setBackground(selectionnerCouleur(""));
		txtrLoremIpsumDolor.setLineWrap(true);
		txtrLoremIpsumDolor.setText(message.getMessage());
		txtrLoremIpsumDolor.setEditable(false);
		scrollPane_1.setViewportView(txtrLoremIpsumDolor);
	
		JLabel lblNewLabel = new JLabel("Par " + message.getPrenom() + " " + message.getNom() + " le " + message.getDate());
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_5.add(lblNewLabel, BorderLayout.NORTH);
	}
}
