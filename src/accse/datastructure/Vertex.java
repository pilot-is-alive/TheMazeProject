package accse.datastructure;

public class Vertex<E> {

	E node;
	PositionList<Edge> adjacencyList;
	
	public Vertex(E node)
	{
		this.node=node;
		this.adjacencyList= new DoublyLinkedList<>();
		
	}
}
