package accse.datastructure;

public class Edge<E>{

	Vertex<E> target;
	int weight;
	public Edge(Vertex<E> target, int weight)
	{
		this.target=target;
		this.weight=weight;
	}
}
