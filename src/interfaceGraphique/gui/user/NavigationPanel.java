package interfaceGraphique.gui.user;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.ScrollPaneConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import commun.FilDiscussion;

public class NavigationPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5512156323485795968L;
	private Container contentPane;
	private JPanel baseThread;
	private ThreadTree tree;
	JScrollPane scrollPane_2;
	public NavigationPanel(Container contentPane, JPanel baseThread) {
		this.contentPane = contentPane;
		this.baseThread = baseThread;
		this.setPreferredSize(new Dimension(200, 0));
		this.setLayout(new BorderLayout(0, 0));
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane_2);
		
		actualiserTree();
		
		
		JButton btnNewButton = new JButton("Ajouter Fil de discussion");
		btnNewButton.addActionListener(this::btnAjouterFil);
		this.add(btnNewButton, BorderLayout.SOUTH);
	}
	
	public void actualiserTree() {
		DefaultMutableTreeNode mainRoot = new DefaultMutableTreeNode();
		DefaultTreeModel treeModele = new DefaultTreeModel(mainRoot);
		tree = new ThreadTree(mainRoot, treeModele);
		scrollPane_2.setViewportView(tree);
		this.addTreeLeftClicListener(tree);
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
							JPanel sujetPanel = new ThreadPanel(node, UserFrame.getCurrentUser());
							contentPane.remove(baseThread);
							contentPane.revalidate();
							contentPane.add(sujetPanel, BorderLayout.CENTER);
							baseThread = sujetPanel;
						}
					}
				}
			}};
		tree.addMouseListener(ml);
	}

	private void btnAjouterFil(ActionEvent event) {
		JPanel creationPanel = new ThreadCreationPanel(this);
		contentPane.remove(baseThread);
		contentPane.revalidate();
		contentPane.add(creationPanel, BorderLayout.CENTER);
		baseThread = creationPanel;
	}
}
