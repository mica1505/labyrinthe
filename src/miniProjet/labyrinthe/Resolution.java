package miniProjet.labyrinthe;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import miniProjet.labyrinthe.WeightedGraph.Edge;
import miniProjet.labyrinthe.WeightedGraph.Graph;
import miniProjet.labyrinthe.WeightedGraph.Vertex;

public class Resolution {
	public static void main(String[] args) {
		
		//Lecture de la carte et creation du graphe
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Veuillez saisir le chemin absolu du fichier qui contient le labyrinthe : ");
		//on recupere le chemin du fichier dpeuis lequel on va lire les labyrinthes
		String f = sc.next();
		sc.close();
		File myObj = new File(f);
		Scanner myReader;
		//cette liste va contenir les labyrinthes qu'on va lire depuis le fichier saisi
		ArrayList<Labyrinthe> labyrinthes = new ArrayList<Labyrinthe>();
		//on verifie que le fichier existe
		try {
			
			myReader = new Scanner(myObj);
			//on lit le nombre d'instances que contient le fichier
			String data = myReader.nextLine();
		    int nbInstances = Integer.valueOf(data);
		    //on lit les labyrinthes qui se trouvent dans le fichier et on l'ajoute a la liste des labyrinthes
		    for(int nl =0;nl<nbInstances;nl++) {
		    	labyrinthes.add(initLab(myReader));
		    }
		    myReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//on resoud chaque labyrinthe qui se trouve dans la liste
		for( Labyrinthe lab: labyrinthes) {
			System.out.println("Labyrinthe n_"+(labyrinthes.indexOf(lab)+1));
			System.out.println(lab.run_instance());
		}
	}
	//fonction qui lit un labyrinthe depuis un fichier donne
	public static Labyrinthe initLab(Scanner myReader) {
		Labyrinthe lab=null;

		//TODO: obtenir le fichier qui decrit la carte

	      String data = "";
	      
	      //Lecture du nombre de lignes
	      data = myReader.nextLine();
	      int nlines = Integer.parseInt(data);
	      
	      //Et du nombre de colonnes
	      data = myReader.nextLine();
	      
	      int ncols = Integer.parseInt(data);
	      
	      //Initialisation du graphe
	      Graph graph = new Graph();
	      char [][] grille = new char[nlines][ncols];
	      int start=0,end=0,fire=0;
	      
	      //On ajoute les sommets dans le graphe (avec le bon type)
	      for (int line=0; line < nlines; line++)
	      {
	    	  data = myReader.nextLine();
	    	  //System.out.println(data);
	    	  for (int col=0; col < ncols; col++)
	    	  {
	    		  //System.out.println(data.charAt(col));
	    		  /*ON AJOUTE LE SOMMET AU GRAPHE*/
	    		  if(Character.valueOf(data.charAt(col)) == 'D') {
	    			  start = line*ncols+col;
	    			  //System.out.println("START : "+start);
	    		  }
	    		  if(Character.valueOf(data.charAt(col))=='S') {
	    			  end = line*ncols+col;
	    			  //System.out.println("END : "+end);
	    		  }
	    		  if(Character.valueOf(data.charAt(col))=='F') {
	    			  fire = line*ncols+col;
	    			  //System.out.println("Fire : "+fire);
	    		  }
	    		  grille[line][col] = Character.valueOf(data.charAt(col));
	    		  graph.addVertex(Character.valueOf(data.charAt(col)));
	    	  }
	      }
	      lab = new Labyrinthe(graph,grille,start,end,nlines,ncols,fire);
	      
	      System.out.println(lab);
	      
	      //TODO: ajouter les arretes
	      for (int line=0; line < nlines; line++)
	      {
	    	  for (int col=0; col < ncols; col++)
	    	  {
	    		  int sommetSource = line*ncols +col;
	    		  double poidSource = graph.vertexlist.get(sommetSource).indivTime;
	    		  if(line<nlines-1) {//bas
	    			  lab.graph.addEgde(sommetSource, (line+1)*ncols+col, (poidSource+graph.vertexlist.get((line+1)*ncols+col).indivTime)/2);//en bas
	    		  }
	    		  if(line >0){//HAUT
	    			  lab.graph.addEgde(sommetSource, (line-1)*ncols+col, (poidSource+graph.vertexlist.get((line-1)*ncols+col).indivTime)/2);//en haut
	    		  }
	    		  if(col<ncols-1) { //droite 
	    			  lab.graph.addEgde(sommetSource, line*ncols+col+1,(poidSource+graph.vertexlist.get(line*ncols+col+1).indivTime)/2);//a droite
	    		  }
	    		  if(col>0) { //la gauche 
	    			  lab.graph.addEgde(sommetSource, line*ncols+col-1, (poidSource+graph.vertexlist.get(line*ncols+col-1).indivTime)/2);//a gauche
	    		  }
	    	  }
	      }
	      return lab;

	}
}
