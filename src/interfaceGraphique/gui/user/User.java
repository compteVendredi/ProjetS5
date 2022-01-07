package interfaceGraphique.gui.user;

import java.awt.Color;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import utilisateur.ServiceUtilisateur;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;

public class User extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2299593615803888403L;
	private static ServiceUtilisateur CurrentUser = null;
	
	public User() {
		super("InterUniv");
		setBackground(Color.DARK_GRAY);
		setLayout(new BorderLayout(0, 0));
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 500);
		setLocationRelativeTo(null);
		JPanel sujetPanel = new JPanel();
		sujetPanel.setBackground(Color.DARK_GRAY);
		sujetPanel.setLayout(new BorderLayout(0, 0));
		JPanel navigationPanel = new NavigationPanel(getContentPane(), sujetPanel);
		getContentPane().add(navigationPanel, BorderLayout.WEST);
		getContentPane().add(sujetPanel, BorderLayout.EAST);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent event) {
				CurrentUser.seDeconnecter();
			}
		});
	}
	
	public static ServiceUtilisateur getCurrentUser() {
		return CurrentUser;
	}
	
	public static void setCurrentUser(ServiceUtilisateur currentUser) {
		CurrentUser = currentUser;
	}
	
}
