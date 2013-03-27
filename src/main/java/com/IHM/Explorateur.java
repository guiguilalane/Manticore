/**
 * 
 */
package com.IHM;

import java.io.File;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author guillaume
 *
 */
public class Explorateur extends JFrame {

	//arborescence des répertoires(panneau de gauche)
	private JTree directoryTree;
	private DefaultMutableTreeNode root;
	
	//affiche les fichiers et dossiers(panneau de droite)
	private JTable displayDirectory;
	
	public Explorateur() {
		super("Manticore");
		setSize(300, 300);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Génération de l'arbre
		listRoot();
		
	}
	
//	private void listRoot() {
//		root = new DefaultMutableTreeNode();
//		int count = 0;
//		DefaultMutableTreeNode reader;
//		DefaultMutableTreeNode node;
//		for(File file : File.listRoots()) {
//			reader = new DefaultMutableTreeNode(file.getAbsolutePath());
//			for(File name : file.listFiles()) {
//				node = new DefaultMutableTreeNode(name.getName() + "\\");
//				reader.add(listFile(name, node));
//			}
//			
//			root.add(reader);
//		}
//		
//		directoryTree = new JTree(root);
//		
//		getContentPane().add(new JScrollPane(directoryTree));
//	}
//	
//	private DefaultMutableTreeNode listFile(File file, DefaultMutableTreeNode node) {
//		
//		DefaultMutableTreeNode tempNode = null;
//		if(!file.isFile()) {
//			File[] list = file.listFiles();
//			if(list != null) {
//				DefaultMutableTreeNode subNode = null;
//				for(File name : list) {
//					if(name.isDirectory()) {
//						subNode = new DefaultMutableTreeNode(name.getName() + "\\");
//						node.add(listFile(name, subNode));
//					}
//				}
//			}
//		}
//		return node;
//	}
	
	private void listRoot(){     
	    this.root = new DefaultMutableTreeNode();      
	    int count = 0;
	    for(File file : File.listRoots()){
	      DefaultMutableTreeNode lecteur =
	      new DefaultMutableTreeNode(file.getAbsolutePath());
	      try {
	        for(File nom : file.listFiles()){
	          DefaultMutableTreeNode node = new DefaultMutableTreeNode(nom.getName()+"\\");              
	          lecteur.add(this.listFile(nom, node));              
	        }
	      } catch (NullPointerException e) {}
	 
	      this.root.add(lecteur);                
	    }
	    //Nous créons, avec notre hiérarchie, un arbre
	    directoryTree = new JTree(this.root);     
	    //Que nous plaçons sur le ContentPane de notre JFrame à l'aide d'un scroll
	    this.getContentPane().add(new JScrollPane(directoryTree));
	  }
	 
	  private DefaultMutableTreeNode listFile(File file, DefaultMutableTreeNode node){
	    int count = 0;
	       
	    if(file.isFile())
	      return new DefaultMutableTreeNode(file.getName());
	    else{
	      File[] list = file.listFiles();
	      if(list == null)
	        return new DefaultMutableTreeNode(file.getName());
	 
	      for(File nom : list){
	        count++;
	        //Pas plus de 5 enfants par noeud
	        if(count < 5){
	          DefaultMutableTreeNode subNode;
	          if(nom.isDirectory()){
	            subNode = new DefaultMutableTreeNode(nom.getName()+"\\");
	            node.add(this.listFile(nom, subNode));
	          }else{
	            subNode = new DefaultMutableTreeNode(nom.getName());
	          }
	          node.add(subNode);
	        }
	      }
	      return node;
	    }
	  }

	  public static void main(String[] args){
		    Explorateur fen = new Explorateur();
		    fen.setVisible(true);
	  }
	  
}
