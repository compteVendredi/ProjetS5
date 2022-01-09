package interfaceGraphique.gestion;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import javax.swing.JFrame;

import serveur.BDD;
import serveur.Serveur;

public class ServeurFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3735600738821646909L;
	private BDD accesGestion;
	@SuppressWarnings("unused")
	private Serveur serveur;

	public ServeurFrame(Serveur serveur, BDD accesGestion) {
		super("InterUniv");
		this.serveur = serveur;
		this.accesGestion = accesGestion;
		setBackground(Color.DARK_GRAY);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 500);
		setLocationRelativeTo(null);
		
		SwitchComponent switcher = new SwitchComponent();
		switcher.addComponentToPane(getContentPane(), accesGestion);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent event) {
				serveur.arreter();
			}
		});
	}
	
	public BDD getBDD() {
		return accesGestion;
	}
}
