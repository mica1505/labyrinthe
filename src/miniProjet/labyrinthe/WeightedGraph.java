// Par Sylvain Lobry, pour le cours "IF05X040 Algorithmique avanc�e"
// de l'Universit� de Paris, 11/2020

package miniProjet.labyrinthe;

import java.util.LinkedList;
import java.util.ArrayList;

// Classe d�finissant un graphe pondere.
public class WeightedGraph {
	// Sous-classe pour une arrete.
    static class Edge {
        int source;
        int destination;
        double weight;

        public Edge(int source, int destination, double weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }
    
    // Sous-classe pour un sommet.
    static class Vertex {
    	double indivTime;
    	double timeFromSource;
    	double heuristic;
    	char type;
    	Vertex prev;
    	LinkedList<Edge> adjacencylist;
    	int num;
    	
    	public Vertex(int num,char type) {
    		this.indivTime = initWeight(type);
    		this.timeFromSource = Double.POSITIVE_INFINITY;
    		this.heuristic = -1;
    		this.prev = null;
    		this.adjacencylist = new LinkedList<Edge>();
    		this.num = num;
    		this.type = type;
    	}
    	public void setWeight(double d) {
    		this.indivTime = d;
    	}
    	public Double initWeight(char c) {
    		if(c == '#') {
    			return Double.POSITIVE_INFINITY;
    		}
    		else {
    			return 1.0;
    		}
    	}
    	public void addNeighbor(Edge e) {
    		this.adjacencylist.addFirst(e);
    	}
    }

    //Sous-classe pour le graphe.
    static class Graph {
        ArrayList<Vertex> vertexlist;
        int num_v = 0;

        Graph() {
            vertexlist = new ArrayList<Vertex>();
        }

        public void addVertex(char type)
        {
        	Vertex v = new Vertex(num_v,type);
        	//v.indivTime = indivTime;
        	vertexlist.add(v);
        	num_v = num_v + 1;
        }
        
        public void addEgde(int source, int destination, double weight) {
            Edge edge = new Edge(source, destination, weight);
            vertexlist.get(source).addNeighbor(edge);
        }

    }
    
    //Test de la classe.
      public static void main(String[] args) {
            /*int vertices = 6;
            Graph graph = new Graph();
            graph.addVertex(10);
            graph.addVertex(10);
            graph.addVertex(10);
            graph.addVertex(10);
            graph.addVertex(10);
            graph.addVertex(10);
            graph.addEgde(0, 1, 4);
            graph.addEgde(0, 2, 3);
            graph.addEgde(1, 3, 2);
            graph.addEgde(1, 2, 5);
            graph.addEgde(2, 3, 7);
            graph.addEgde(3, 4, 2);
            graph.addEgde(4, 0, 4);
            graph.addEgde(4, 1, 4);
            graph.addEgde(4, 5, 6);*/
            //graph.printGraph();
        }
}
