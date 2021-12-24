package interfaceGraphique.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class User extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public User() {
		super("InterUniv");
		getContentPane().setBackground(Color.DARK_GRAY);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.DARK_GRAY);
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewJgoodiesTitle = new JLabel("Titre du sujet");
		lblNewJgoodiesTitle.setForeground(Color.WHITE);
		lblNewJgoodiesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewJgoodiesTitle.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 22));
		panel.add(lblNewJgoodiesTitle, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.DARK_GRAY);
		panel_1.setPreferredSize(new Dimension(100, 100));
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.DARK_GRAY);
		panel_1.add(panel_3, BorderLayout.EAST);
		
		JButton btnNewButton = new JButton("ENVOYER");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_3.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_3.add(btnNewButton);
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		JTextArea textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.GRAY);
		panel_5.setPreferredSize(new Dimension(490, 150));
		panel_2.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_5.add(scrollPane_1, BorderLayout.CENTER);
		
		JTextArea txtrLoremIpsumDolor = new JTextArea();
		txtrLoremIpsumDolor.setBackground(new Color(173, 216, 230));
		txtrLoremIpsumDolor.setLineWrap(true);
		txtrLoremIpsumDolor.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce quis justo vel erat dapibus sollicitudin. Praesent venenatis aliquet tellus. Nunc dictum vestibulum massa ut dictum. Aliquam erat volutpat. Mauris molestie vehicula justo quis ullamcorper. Etiam convallis nunc lectus, id feugiat ex tempus elementum. Nullam vehicula eget tellus et tincidunt. Donec consectetur, risus quis dignissim malesuada, odio sem ultricies urna, id blandit lacus nulla id est. Morbi volutpat quis ipsum a vehicula. Aliquam ut metus vestibulum, lobortis urna a, luctus ipsum. Quisque mattis faucibus lacus a sollicitudin. Fusce tristique, dolor a fringilla lobortis, massa ex vestibulum elit, auctor ultrices massa diam ut nibh.");
		txtrLoremIpsumDolor.setEditable(false);
		scrollPane_1.setViewportView(txtrLoremIpsumDolor);
		
		JLabel lblNewLabel = new JLabel("Par [Prenom] [Nom] le [Date] \u00E0 [Heure]");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_5.add(lblNewLabel, BorderLayout.NORTH);
		
		JPanel panel_4 = new JPanel();
		panel_4.setPreferredSize(new Dimension(200, 0));
		getContentPane().add(panel_4, BorderLayout.WEST);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_4.add(scrollPane_2);
		
		JTree tree = new JTree();
		tree.setVisibleRowCount(100);
		tree.setToolTipText("");
		tree.setRootVisible(false);
		tree.setPreferredSize(new Dimension(100, 100));
		tree.setBorder(new TitledBorder(null, "Fils de discussion", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		tree.setBackground(Color.LIGHT_GRAY);
		scrollPane_2.setViewportView(tree);
		setBackground(Color.WHITE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(700, 500);
		setLocationRelativeTo(null);
	}
}
