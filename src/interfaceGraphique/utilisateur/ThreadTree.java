package interfaceGraphique.utilisateur;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import javax.swing.JTree;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;

import commun.FilDiscussionUtilisateur;

public class ThreadTree extends JTree {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3922186974443226272L;

	public ThreadTree(DefaultMutableTreeNode mainRoot, TreeModel modele) {
		super(modele);
	
		List<FilDiscussionUtilisateur> listeFils = UserFrame.getCurrentUser().getAllFilDiscussion();
		List<String> listeGroupes = new ArrayList<>();
	
		for (ListIterator<FilDiscussionUtilisateur> iterateur = listeFils.listIterator(); iterateur.hasNext();) {
			FilDiscussionUtilisateur fd = iterateur.next();
			if (!listeGroupes.contains(fd.getId_groupe())) {
				listeGroupes.add(fd.getId_groupe());
				System.out.println(fd.getId_groupe());
			}
		}
		
		for(ListIterator<String> iterateur1 = listeGroupes.listIterator(); iterateur1.hasNext();) {
			String groupe = iterateur1.next();
			DefaultMutableTreeNode newGroupe = new DefaultMutableTreeNode(groupe);
			for (ListIterator<FilDiscussionUtilisateur> iterateur2 = listeFils.listIterator(); iterateur2.hasNext();) {
				FilDiscussionUtilisateur fd = iterateur2.next();
				DefaultMutableTreeNode newSujet = new DefaultMutableTreeNode(fd);
				if (groupe == fd.getId_groupe()) {
					newGroupe.add(newSujet);
				}
			}
			mainRoot.add(newGroupe);
		}
		
		this.setVisibleRowCount(100);
		this.setPreferredSize(new Dimension(100, 100));
		this.setBorder(new TitledBorder(null, null, TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.setBackground(Color.LIGHT_GRAY);
	}
	/*public void actualiser() {
		List<FilDiscussionUtilisateur> listeFils = UserFrame.getCurrentUser().getAllFilDiscussion();
		for (ListIterator<FilDiscussionUtilisateur> iterateur = listeFils.listIterator(); iterateur.hasNext();) {
				FilDiscussionUtilisateur newFil = iterateur.next();
			
				DefaultMutableTreeNode newSujet = new DefaultMutableTreeNode(newFil);
				newGroupe.add(newSujet);
		}
	}*/
}
