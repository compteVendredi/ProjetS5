package interfaceGraphique.gui.gestion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ListIterator;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import commun.Utilisateur;
import serveur.BDD;

public class GroupPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8214421332364358664L;
	private Container contentPane;
	private String idGroupe;
	private BDD accesGestion;
	
	public GroupPanel(BDD accesGestion, String idGroupe, Container contentPane) {
		this.setLayout(new BorderLayout(0, 0));
		this.idGroupe = idGroupe;
		this.contentPane = contentPane;
		this.accesGestion = accesGestion;
		JPanel panel_7 = new JPanel();
		this.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.Y_AXIS));
	
		Component verticalStrut = Box.createVerticalStrut(20);
		panel_7.add(verticalStrut);
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		JLabel lblNewLabel_1 = new JLabel(idGroupe);
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton deleteBtn = new JButton("DELETE");
		deleteBtn.addActionListener(this::btnDeleteGroupe);
		newPanel.add(deleteBtn);
		this.add(newPanel, BorderLayout.NORTH);
		panel_7.add(lblNewLabel_1);
	
		Component verticalStrut_1 = Box.createVerticalStrut(50);
		panel_7.add(verticalStrut_1);
	
		JPanel panel_6 = new JPanel();
		panel_7.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		JPanel titreListe = new JPanel();
		titreListe.setLayout(new BoxLayout(titreListe, BoxLayout.X_AXIS));
		JLabel lblNewLabel_6 = new JLabel("Utilisateurs");
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
		List<String> listeGroupes = accesGestion.getAllIdUsersGroup(idGroupe);
		for (ListIterator<String> iterateur = listeGroupes.listIterator(); iterateur.hasNext();) {
			String idUser = iterateur.next();
			JPanel newPanel2 = new PanelAddSuprr(idUser, idGroupe, accesGestion);
			listePane.add(newPanel2);
		}
		panel_7.add(scrollPane, BorderLayout.CENTER);
		
		Component verticalStrut_3 = Box.createVerticalStrut(20);
		panel_7.add(verticalStrut_3);
	
		Component horizontalStrut = Box.createHorizontalStrut(150);
		this.add(horizontalStrut, BorderLayout.WEST);
	
		Component horizontalStrut_1 = Box.createHorizontalStrut(150);
		this.add(horizontalStrut_1, BorderLayout.EAST);
	}
	
	private void btnAddListener(ActionEvent event) {
		List<Utilisateur> listeUser = accesGestion.getAllUser();
		Object[] possibilities = new Object[listeUser.size()];
		int nb = 0;
		for(ListIterator<Utilisateur> iterateur = listeUser.listIterator(); iterateur.hasNext() && nb < listeUser.size();) {
			Utilisateur user = iterateur.next();
			possibilities[nb] = user.getIdentifiant(); 
			nb++;
		}
		
		String s = (String)JOptionPane.showInputDialog(
							this,
		                    "Choissisez un utilisateur à ajouter\n",
		                    "Ajout d'un un utilisateur",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null, possibilities,
		                    possibilities[0]);

		//If a string was returned, say so.
		if ((s != null) && (s.length() > 0)) {
			accesGestion.insertGroupe(s, idGroupe);
		}
		
	}
	
	private void btnDeleteGroupe(ActionEvent event) {
		this.removeAll();
		accesGestion.supprimerGroupe(idGroupe);
		this.add(new JPanel(), BorderLayout.CENTER);
		contentPane.revalidate();
	}
}
