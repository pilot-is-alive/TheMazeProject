package accse.datastructure;

public class Vertex {

	String label;
	PositionList<Edge> adjacencyList;
	
	public Vertex(String label)
	{
		this.label=label;
		this.adjacencyList= new DoublyLinkedList<>();
		
	}
}
