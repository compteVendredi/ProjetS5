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

import serveur.BDD;
import utilitaire.HashUtil;

public class CreationGroupe extends JPanel {
	private JTextField idField;
	private JTextField passwordField;
	private JTextField nameField;
	private JTextField firstNameField;
	private BDD accesGestion;
	private String targetGroupe;
	private JPanel panel_1, informationPanel;
	private Container contentPane;
	private JList<String> list;
	
	public CreationGroupe(BDD accesGestion, Container contentPane, JPanel panel_1, JPanel informationPanel, String targetGroupe, JList<String> list) {
		this.accesGestion = accesGestion;
		this.setLayout(new BorderLayout(0, 0));
		this.contentPane = contentPane;
		this.panel_1 = panel_1;
		this.informationPanel = informationPanel;
		this.targetGroupe = targetGroupe;
		this.list = list;
		
		JPanel panel_5 = new JPanel();
		this.add(panel_5, BorderLayout.NORTH);
		
		JLabel lblNewLabel_5 = new JLabel("Creation d'un groupe");
		panel_5.add(lblNewLabel_5);
		
		JPanel panel_7 = new JPanel();
		this.add(panel_7, BorderLayout.CENTER);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(20);
		panel_7.add(verticalStrut);
		
		JLabel lblNewLabel_1 = new JLabel("Nom Groupe");
		lblNewLabel_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_7.add(lblNewLabel_1);
		
		Component verticalStrut_1 = Box.createVerticalStrut(10);
		panel_7.add(verticalStrut_1);
		
		idField = new JTextField();
		panel_7.add(idField);
		idField.setColumns(10);
		
		
		Component verticalStrut_9 = Box.createVerticalStrut(20);
		panel_7.add(verticalStrut_9);
		
		JButton btnNewButton_1 = new JButton("CREER");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String groupRole;
				groupRole = idField.getText();
				CreationGroupe.this.accesGestion.ajouterGroupe(groupRole);
				CreationGroupe.this.removeAll();
				JPanel newPanel = new JPanel();
				CreationGroupe.this.add(newPanel);
				
				DefaultListModel<String> modeleList = new DefaultListModel<>();
				List<String> listeGroupes = accesGestion.getListGroupe();
				for (ListIterator<String> iterateur = listeGroupes.listIterator(); iterateur.hasNext();) {
					String idGroupe = iterateur.next();
					modeleList.addElement(idGroupe);
				}
				JList<String> list = new JList<>(modeleList);
				list.addMouseListener( new MouseAdapter()
				{
				    public void mousePressed(MouseEvent e)
				    {
				        if (e.getButton() == MouseEvent.BUTTON1 )
				        {
				        	CreationGroupe.this.targetGroupe = list.getSelectedValue();
				        	informationPanel.removeAll();
				        	CreationGroupe.this.contentPane.remove(informationPanel);
				        	CreationGroupe.this.contentPane.revalidate();
				        	CreationGroupe.this.informationPanel = new GroupPanel(accesGestion, targetGroupe, contentPane);
				        	CreationGroupe.this.add(informationPanel, BorderLayout.CENTER);
				        	CreationGroupe.this.contentPane.revalidate();
				        }
				    }
				});
				panel_1.remove(CreationGroupe.this.list);
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