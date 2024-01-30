package miniProjet.labyrinthe;

import java.util.LinkedList;

import miniProjet.labyrinthe.WeightedGraph.Graph;

public class Labyrinthe {
	Graph graph;
	char [][] grille;
	int start,end,nlines,ncols,fire;
	
	public Labyrinthe(Graph graph,char [][] grille,int start,int end,int nlines,int ncols,int fire){
		this.graph = graph;
		this.grille = grille;
		this.start = start;
		this.end = end;
		this.nlines = nlines;
		this.ncols = ncols;
		this.fire = fire;
	}
	/**
	 * fonction qui propage le feu, pour chaque position du feu, si le feu est cote a cote avec un mur alors on l'enflamme
	 * sinon on verifie qu'il est a cote d'un joueur ou de la sortie si c'est le cas la fonction reoturne vrai
	 * @param x numero de la colonne du feu
	 * @param y numero de la ligne du feu
	 * @return true si le feu touche le prisonnier ou arrive a la sortie, faux sinon
	 */
	public boolean burn_around(int x, int y) {
		  //retourne true si le feu touche le joueur ou la sortie
		  //'A' pour éviter que les flammes soient propagées le même tour où elles sont crées
		  if (y != 0) {
			  //si la case du haut est un mur on l'eflamme
		    if (grille[y-1][x] == '.') grille[y-1][x] = 'A';
		    //sinon on verifie si c'est le joueur ou la sortie, si oui gameover
		    else if (grille[y-1][x] == 'D' || grille[y-1][x] == 'S') return true;
		  }
		  if (x != 0) {
		    if (grille[y][x-1] == '.') grille[y][x-1] = 'A';
		    else if (grille[y][x-1] == 'D' || grille[y][x-1] =='S') return true;
		  }
		  if (y != nlines-1) {
		    if (grille[y+1][x] == '.') grille[y+1][x] = 'A';
		    else if (grille[y+1][x] == 'D' || grille[y+1][x] == 'S') return true;
		  }
		  if (x != ncols-1) {
		    if (grille[y][x+1] == '.') grille[y][x+1] = 'A';
		    else if (grille[y][x+1] == 'D' || grille[y][x+1] == 'S') return true;
		  }
		  
		  return false;
		}
	/***
	 * fonction qui retourne vrai si on peut bouger le prisonnier dans une certaine direction
	 * @param x numero de la colonne du prisonnier
	 * @param y numero de la ligne du prisonnier
	 * @param dir la direction dans laquelle on veut bouger le joueur
	 * @return true si le joueur peut bouger vers la direction T, faux sinon
	 */
	public boolean can_move_dir(int x, int y, char dir) {
		//peut bouger ssi c'est un chemin ou la sortie (pas un feu ni un mur)
		  if (dir == 'T') return y != 0 && (grille[y-1][x] == '.' || grille[y-1][x] == 'S');
		  else if (dir == 'B') return y != nlines-1 && (grille[y+1][x] == '.' || grille[y+1][x] == 'S');
		  else if (dir == 'L') return x != 0 && (grille[y][x-1] == '.' || grille[y][x-1] == 'S');
		  else if (dir == 'R') return x != ncols-1 && (grille[y][x+1] == '.' || grille[y][x+1] == 'S');
		  else return false;
	}
	/**
	 * retourne le nombre de directions dans lesquelles le joueuer peut bouger
	 * @param x numero de la colonne du prisonnier
	 * @param y numero de la ligne du prisonnier
	 * @return le nombre de directions vers lesquelles le prisonnier peut bouger
	 */
	public int can_move(int x, int y) {
		  //retourne nombre de directions possibles
		  int top = (can_move_dir(x, y, 'T'))?1:0;
		  int bottom = (can_move_dir(x, y, 'B'))?1:0;
		  int left = (can_move_dir(x, y, 'L'))?1:0;
		  int right = (can_move_dir(x, y, 'R'))?1:0;
		  //System.out.println("Y : "+y+" X : "+x+"\n"+top+" "+left+" "+right+" "+bottom);
		  return top + left + right + bottom;
	}
	/***
	 * fonction qui reoturne vrai si le prisonnier est arrive a la sortie, faux sinon
	 * @param x numero de la colonne du prisonnier
	 * @param y numero de la ligne du prisonnier
	 * @return vrai si le prisonnier est arrive a la sortie, faux sinon
	 */
	public boolean win_move(int x, int y) {
		  //retourne vrai si on est a cote de la sortie
		  boolean top = (y != 0 && grille[y-1][x] == 'S');
		  boolean bottom = (y != nlines-1 && grille[y+1][x] == 'S');
		  boolean left = (x != 0 && grille[y][x-1] == 'S');
		  boolean right = (x != ncols-1 && grille[y][x+1] == 'S');
		  return top || left || right || bottom;
	}
	
	public char run_instance() {
		LinkedList<Integer> path = Algorithme.AStar(this,start,end,ncols,ncols*nlines);
		//si ya pas de solution on retourne N
		if(path.size()<=1) {
			System.out.println("Il n'y a pas de solution!!");
			//System.out.println(this);
			return 'N';
		}
		//si la grille ne contient pas de feu on reoturne Y et qu'il ya bien un chemin vers la sortie
		if(grille[fire/ncols][fire%ncols] != 'F' && path.size()>1) {
			System.out.println("Il n'y a pas de feu. RESOLU!");
			//System.out.println(this);
			return 'Y';
		}
		int coupsFeuSortie = Math.abs(fire/ncols - end/ncols) + Math.abs(fire%ncols - end%ncols);
		int coupsPrisonnierSortie = path.size() -1;
		
		//si le nombre de coups du feu vers la sortie <= au nombre de coups du prisonnier vers la sortie on retourne faux
		if(coupsFeuSortie<=coupsPrisonnierSortie) {
			System.out.println("Oups, le feu arrive a la sortie avant le prisonnier!");
			return 'N';
		}
		else {
			//on commence a l'index 1 car le premier element du path est la position du prisonnier
			//on termine a path.size()-1 car le dernier element est la sortie
			for(int i=1;i<path.size()-1;i++){
				//on transforme les A en F 
			    for (int j = 0; j < nlines; j++) {
			        for (int k = 0; k < ncols; k++) {
			          if (grille[j][k] == 'A') {
			            grille[j][k] = 'F';
			          }
			        }
			    }
			    
			    //on propage le feu
			    for(int l=0;l<nlines;l++) {
			    	for(int c=0;c<ncols;c++) {
			    		if(grille[l][c] == 'F') {
			    			if (burn_around(c,l)) {
			    				System.out.println("Le feu t'as touche!!");
			    				//System.out.println(this);
			    				return 'N';
			    			}
			    		}
			    	}	
			    }
			    //on efface l'ancienne position du joueur
			    grille[path.get(i-1)/ncols][path.get(i-1)%ncols] = '.';
			    //on fait avancer le joueur
				grille[path.get(i)/ncols][path.get(i)%ncols] = 'D';
			}
		}
		//System.out.println(this);
		return 'Y';
}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int line=0; line < nlines; line++){
			for (int col=0; col < ncols; col++){
				sb.append(grille[line][col]);
	    	}
	    	sb.append("\n");
		}
		return sb.toString();
	}
}
