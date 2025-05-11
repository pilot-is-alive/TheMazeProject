package main;

import java.util.Vector;

import accse.datastructure.Edge;
import accse.datastructure.Vertex;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/**
 * @param <T>
 */
public class Graph<T> {
	private Vector<Vertex<Color>> vertices;
	
	public Graph() {
		vertices = new Vector<Vertex<Color>>();
		//edges = new Vector<Edge<T>>();
	}
	
	public void getNodes(MyCanvas canvas) {
		WritableImage image = canvas.snapshot(null, null);
		PixelReader pixIn = image.getPixelReader();
		
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				Vertex<Color> newVertex = new Vertex<Color>(pixIn.getColor(x, y));
				vertices.add(newVertex);

			}
		}
	}
	
	
	/**
	 * Adds a connection between two vertices, implemented using an Edge that carries the weight between the vertices
	 * 
	 * @param Vertex<T> v1, Vertex<T> v2, int weight
	 * @return void
	 * 
	 */
	public void addEdge(Vertex<Color> v1, Vertex<Color> v2, int weight) {
		Edge<T> edge1 = new Edge(v1, weight); 
		Edge<T> edge2 = new Edge(v2, weight);
		v1.getEdges().addLast(edge2);
		v2.getEdges().addLast(edge1);
	}
}
