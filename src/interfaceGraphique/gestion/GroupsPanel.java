package interfaceGraphique.gestion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.ListIterator;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;



import serveur.BDD;
import serveur.Serveur;

public class GroupsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6468203785279714743L;
	private String targetGroupe;
	private JPanel informationPanel;
	private Container contentPane;
	private BDD accesGestion;
	
	public GroupsPanel(BDD accesGestion, Container contentPane) {
		this.accesGestion = accesGestion;
		this.contentPane = contentPane;
		this.setLayout(new BorderLayout(0, 0));
		informationPanel = new JPanel();
		informationPanel.setBackground(Color.DARK_GRAY);
		informationPanel.setLayout(new BorderLayout(0, 0));
		this.add(informationPanel, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setPreferredSize(new Dimension(200, 0));
		this.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new BorderLayout(0, 0));
		
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
		        	targetGroupe = list.getSelectedValue();
		        	informationPanel.removeAll();
		        	GroupsPanel.this.contentPane.remove(informationPanel);
					GroupsPanel.this.contentPane.revalidate();
		        	informationPanel = new GroupPanel(accesGestion, targetGroupe, contentPane);
		        	GroupsPanel.this.add(informationPanel, BorderLayout.CENTER);
		        	GroupsPanel.this.contentPane.revalidate();
		        }
		    }
		});
		panel_1.add(list, BorderLayout.CENTER);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("Liste Groupes:");
		panel_3.add(lblNewLabel);
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Ajouter Groupe");
		btnNewButton.addActionListener(this::btnAjouterGroupe);
		panel_4.add(btnNewButton);
		
	}
	
	private void btnAjouterGroupe(ActionEvent event) {
		informationPanel.removeAll();
		contentPane.remove(informationPanel);
		informationPanel = new CreationGroupe(accesGestion, contentPane);
		this.add(informationPanel, BorderLayout.CENTER);
		contentPane.revalidate();
	}
}