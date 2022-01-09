package interfaceGraphique.gestion;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ListIterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;

import commun.Utilisateur;
import serveur.BDD;
import utilitaire.HashUtil;

public class CreationUser extends JPanel {
	private JTextField idField;
	private JTextField passwordField;
	private JTextField nameField;
	private JTextField firstNameField;
	private BDD accesGestion;
	private JPanel panel_1, informationPanel;
	private Container contentPane;
	private Utilisateur targetUser;
	private JList list;
	
	public CreationUser(BDD accesGestion, Container contentPane, JPanel panel_1, JPanel informationPanel, Utilisateur targetUser, JList<Utilisateur> list) {
		this.accesGestion = accesGestion;
		this.setLayout(new BorderLayout(0, 0));
		this.panel_1 = panel_1;
		this.informationPanel = informationPanel;
		this.targetUser = targetUser;
		this.contentPane = contentPane;
		this.list = list;
		
		JPanel panel_5 = new JPanel();
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
				
				DefaultListModel<Utilisateur> modeleList = new DefaultListModel<>();
				List<Utilisateur> listeUtilisateurs = CreationUser.this.accesGestion.getAllUser();
				for (ListIterator<Utilisateur> iterateur = listeUtilisateurs.listIterator(); iterateur.hasNext();) {
					Utilisateur user = iterateur.next();
					modeleList.addElement(user);
				}
				JList<Utilisateur> list = new JList<>(modeleList);
				list.addMouseListener( new MouseAdapter()
				{
				    public void mousePressed(MouseEvent e)
				    {
				        if (e.getButton() == MouseEvent.BUTTON1 )
				        {
				        	CreationUser.this.targetUser = (Utilisateur) list.getSelectedValue();
				        	informationPanel.removeAll();
				        	CreationUser.this.contentPane.remove(informationPanel);
							CreationUser.this.contentPane.revalidate();
				        	CreationUser.this.informationPanel = new UserPanel(accesGestion, targetUser.getIdentifiant(), accesGestion.getHash(targetUser.getIdentifiant()), targetUser.getPrenom(), targetUser.getNom(), contentPane);
				        	CreationUser.this.add(informationPanel, BorderLayout.CENTER);
				        	CreationUser.this.contentPane.revalidate();
				        }
				    }
				});
				panel_1.remove(CreationUser.this.list);
				panel_1.add(list, BorderLayout.CENTER);
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
