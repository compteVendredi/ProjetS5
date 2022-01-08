package interfaceGraphique.gui.gestion;


import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelAddSuprr extends JPanel {
	private String id;
	
	public PanelAddSuprr(String id) {
		this.id = id;
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_1 = new JLabel(id);
		this.add(lblNewLabel_1);
		Component horizontalStrut = Box.createHorizontalStrut(20);
		this.add(horizontalStrut);
		
		JButton btnNewButton_2 = new JButton("DELETE");
		btnNewButton_2.addActionListener(this::btnDelete);
		this.add(btnNewButton_2);
		
		Component horizontalStrut2 = Box.createHorizontalStrut(20);
		this.add(horizontalStrut2);
	}
	
	private void btnDelete(ActionEvent event) {
		
	}
}
