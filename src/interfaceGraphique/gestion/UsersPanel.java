package interfaceGraphique.gestion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ListIterator;

import javax.swing.AbstractListModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import commun.Utilisateur;
import interfaceGraphique.connexion.Connexion;
import interfaceGraphique.user.UserFrame;
import serveur.BDD;
import utilisateur.ServiceUtilisateur;
import utilitaire.HashUtil;

public class UsersPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6468203785279714743L;
	JList<Utilisateur> list;
	private JPanel informationPanel;
	private Utilisateur targetUser;
	private BDD accesGestion = null;
	private Container contentPane;
	
	public UsersPanel(BDD accesGestion, Container contentPane) {
		this.contentPane = contentPane;
		informationPanel = new JPanel();
		informationPanel.setBackground(Color.DARK_GRAY);
		informationPanel.setLayout(new BorderLayout(0, 0));
		this.add(informationPanel, BorderLayout.CENTER);
		this.setLayout(new BorderLayout(0, 0));
		this.accesGestion = accesGestion;
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setPreferredSize(new Dimension(200, 0));
		this.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		DefaultListModel<Utilisateur> modeleList = new DefaultListModel<>();
		List<Utilisateur> listeUtilisateurs = this.accesGestion.getAllUser();
		for (ListIterator<Utilisateur> iterateur = listeUtilisateurs.listIterator(); iterateur.hasNext();) {
			Utilisateur user = iterateur.next();
			modeleList.addElement(user);
		}
		list = new JList<>(modeleList);
		list.addMouseListener( new MouseAdapter()
		{
		    public void mousePressed(MouseEvent e)
		    {
		        if (e.getButton() == MouseEvent.BUTTON1 )
		        {
		        	targetUser = list.getSelectedValue();
		        	informationPanel.removeAll();
		        	UsersPanel.this.contentPane.remove(informationPanel);
					UsersPanel.this.contentPane.revalidate();
		        	informationPanel = new UserPanel(accesGestion, targetUser.getIdentifiant(), accesGestion.getHash(targetUser.getIdentifiant()), targetUser.getPrenom(), targetUser.getNom(), contentPane);
		        	UsersPanel.this.add(informationPanel, BorderLayout.CENTER);
		        	UsersPanel.this.contentPane.revalidate();
		        }
		    }
		});
		panel_1.add(list, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Liste Utilisateurs:");
		panel_3.add(lblNewLabel);
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Ajouter Utilisateur");
		btnNewButton.addActionListener(this::btnAjouterUtilisateur);
		panel_4.add(btnNewButton);
		
	}
	
	private void btnAjouterUtilisateur(ActionEvent event) {
		informationPanel.removeAll();
		contentPane.remove(informationPanel);
		informationPanel = new CreationUser(accesGestion, contentPane);
		this.add(informationPanel, BorderLayout.CENTER);
		contentPane.revalidate();
	}
}
