package interfaceGraphique.gui;

import utilisateur.Message;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import utilisateur.FilDiscussion;
import utilisateur.Utilisateur;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.event.WindowAdapter;
import java.util.List;
import java.util.ListIterator;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.FlowLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class User extends JFrame {
	private static final long serialVersionUID = 1L;
	private Utilisateur utilisateurSession;
	private FilDiscussion fd = null;
	private JPanel panel;
	private JTextArea textMessage;
	
	public User(Utilisateur utilisateurSession) {
		super("InterUniv");
		this.utilisateurSession = utilisateurSession;
		
		getContentPane().setBackground(Color.DARK_GRAY);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		JPanel panel_4 = new JPanel();
		panel_4.setPreferredSize(new Dimension(200, 0));
		getContentPane().add(panel_4, BorderLayout.WEST);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_4.add(scrollPane_2);
		
	
		DefaultMutableTreeNode mainRoot = new DefaultMutableTreeNode("Fils de Discussion");
		DefaultTreeModel treeModele = new DefaultTreeModel(mainRoot);
		JTree tree = new JTree(treeModele);
		tree.setShowsRootHandles(false);
		utilisateurSession.actualiseGroupe();
		List<String> listeGroupes = utilisateurSession.getGroupesUtilisateur();
		for (int i = 0; i < listeGroupes.size(); i++) {
			DefaultMutableTreeNode newGroupe = new DefaultMutableTreeNode(listeGroupes.get(i));
			List<FilDiscussion> listeFils = utilisateurSession.getAllFilDiscussion();
			for (ListIterator<FilDiscussion> iterateur = listeFils.listIterator(); iterateur.hasNext();) {
					FilDiscussion newFil = iterateur.next();
					DefaultMutableTreeNode newSujet = new DefaultMutableTreeNode(newFil);
					newGroupe.add(newSujet);
			}
			mainRoot.add(newGroupe);
		}
		
		tree.setVisibleRowCount(100);
		tree.setPreferredSize(new Dimension(100, 100));
		tree.setBorder(new TitledBorder(null, null, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tree.setBackground(Color.LIGHT_GRAY);
		scrollPane_2.setViewportView(tree);
		
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 500);
		setLocationRelativeTo(null);
		this.addTreeLeftClicListener(tree);
		
		JButton btnNewButton = new JButton("Ajouter Fil de discussion");
		btnNewButton.addActionListener(this::btnCreerFil);
		panel_4.add(btnNewButton, BorderLayout.SOUTH);
		
		panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent event) {
				utilisateurSession.seDeconnecter();
			}
		});
	}
	
	private JPanel initSujetPanel(DefaultMutableTreeNode node) {
		JPanel sujetPanel = new JPanel();
		sujetPanel.setBackground(Color.DARK_GRAY);
		sujetPanel.setLayout(new BorderLayout(0, 0));
		//panel d'envoy
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setPreferredSize(new Dimension(100, 100));
		sujetPanel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.DARK_GRAY);
		panel_1.add(panel_3, BorderLayout.EAST);
		
		JButton envoyerMessage = new JButton("ENVOYER");
		envoyerMessage.addActionListener(this::btnEnvoyerMessageListener);
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_3.add(envoyerMessage);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		textMessage = new JTextArea();
		scrollPane.setViewportView(textMessage);
		fd = (FilDiscussion)node.getUserObject();
		JPanel panel_2 = new JPanel();
		sujetPanel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		List<Message> lm = utilisateurSession.getFilDiscussion(fd.getId_filDiscussion()).getMessages();
		for(ListIterator<Message> iterareur = lm.listIterator(); iterareur.hasNext();) {	
			Message message = iterareur.next();

			JPanel panel_5 = new JPanel();
			panel_5.setBackground(Color.GRAY);
			panel_5.setPreferredSize(new Dimension(480, 150));
			panel_2.add(panel_5);
			panel_5.setLayout(new BorderLayout(0, 0));
		
			JScrollPane scrollPane_1 = new JScrollPane();
			scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel_5.add(scrollPane_1, BorderLayout.CENTER);
		
			JTextArea txtrLoremIpsumDolor = new JTextArea();
			txtrLoremIpsumDolor.setBackground(new Color(173, 216, 230));
			txtrLoremIpsumDolor.setLineWrap(true);
			txtrLoremIpsumDolor.setText(message.getMessage());
			txtrLoremIpsumDolor.setEditable(false);
			scrollPane_1.setViewportView(txtrLoremIpsumDolor);
		
			JLabel lblNewLabel = new JLabel("Par " + message.getPrenom() + " " + message.getNom() + " le " + message.getDate());
			lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
			panel_5.add(lblNewLabel, BorderLayout.NORTH);
		}
		return sujetPanel;
	}
	
	private void addTreeLeftClicListener(final JTree tree) {
		MouseListener ml = new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					int selRow = tree.getRowForLocation(e.getX(), e.getY());
					TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
					if (selRow != -1) {
						tree.clearSelection();
						tree.setSelectionPath(selPath);
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
						//Object obj = node.getUserObject();
						Class<FilDiscussion> c = FilDiscussion.class;
						boolean b = c.isInstance(node.getUserObject());
						if (b) {
							JPanel sujetPanel = initSujetPanel(node);
							panel.removeAll();
							panel.add(sujetPanel, BorderLayout.CENTER);
							panel.revalidate();
						}
					}
				}
			}};
		tree.addMouseListener(ml);
	}
	private JPanel createPanel() {
		JPanel creationPanel = new JPanel();
		creationPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		return creationPanel;
	}
	
	private void btnCreerFil(ActionEvent event) {
		panel.removeAll();
		panel.add(createPanel(), BorderLayout.CENTER);
		panel.revalidate();
	}
	
	private void btnEnvoyerMessageListener(ActionEvent event) {
		Message message;
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		message = new Message(utilisateurSession.getIdentifiant(), utilisateurSession.getNom(), utilisateurSession.getPrenom(), dtf.format(LocalDateTime.now()), "new", textMessage.getText());
		utilisateurSession.envoyerMessage(message, fd);
	}
}
