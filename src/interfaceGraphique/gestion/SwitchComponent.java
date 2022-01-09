package interfaceGraphique.gestion;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import serveur.BDD;

public class SwitchComponent implements ItemListener {
	private JPanel cards;
	public void addComponentToPane(Container pane, BDD accesGestion) {
		JPanel comboBoxPane = new JPanel();
		String comboBoxItems[] = {"GESTION UTILISATEURS", "GESTION GROUPES" };
		JComboBox<String> cb = new JComboBox<>(comboBoxItems);
		cb.setEditable(false);
		cb.addItemListener(this);
		comboBoxPane.add(cb);
    
		JPanel card1 = new UsersPanel(accesGestion, pane);
		JPanel card2 = new GroupsPanel(accesGestion, pane);
	
		cards = new JPanel(new CardLayout());
		cards.add(card1, "GESTION UTILISATEURS");
		cards.add(card2, "GESTION GROUPES" );
	
		pane.add(comboBoxPane, BorderLayout.NORTH);
		pane.add(cards, BorderLayout.CENTER);
	}
	
	public void itemStateChanged(ItemEvent evt) {
        CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards, (String)evt.getItem());
    }
}