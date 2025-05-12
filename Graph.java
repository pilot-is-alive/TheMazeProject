import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
 * A generic & weighted Graph class implementation with the use of an adjacency list where,
 * each vertex is mapped to a map of its neighbouring nodes as well as the weights of their connecting edges.
 * This class contains the methods for adding & removing vertices, edges as well as retrieving neighbours, and accessing edge weights.
 */
public class Graph<E> 
{
	public Map<E, Map<E, Double>> adjList;
	
	public Graph()
	{
		adjList = new HashMap<>();
	}
	
	/*
	 * A method where a new vertex gets added/created (if no such vertex exists) to the graph.
	 * 
	 * @param node The vertex that needs to be added.
	 */
	public void insertVertex(E node)
	{
		if(!(adjList.containsKey(node)))
		{
			adjList.putIfAbsent(node, new HashMap<>());
		}
	}
	
	/*
	 * A method where a weighted & undirected edge is added between two existing vertices.
	 * The edge gets added in both directions to indicate an undirected graph.
	 * 
	 * @param from The starting vertex.
	 * @param to The ending vertex.
	 * @param weight The weight of the edge.
	 */
	public void insertEdge(E from, E to, Double weight)
	{
		
		if(adjList.containsKey(from) && adjList.containsKey(to))
		{
			adjList.get(from).put(to, weight);
			adjList.get(to).put(from, weight);
		}
	}
	
	/*
	 * A method where a vertex & all of its connected edges from the graph, are removed.
	 * 
	 * @param node The vertex to remove.
	 */
	public void removeVertex(E node)
	{
		if(adjList.containsKey(node))
		{
			 // We copy the keyset to avoid the CurrentModificationsException as we are modifying the underlying maps of the neighbours.
			Set<E> neighbours = Set.copyOf(adjList.get(node).keySet());
			for(E nodeNeighbour : neighbours)
			{
				if(adjList.containsKey(nodeNeighbour))
				{
					adjList.get(nodeNeighbour).remove(node);
				}
			}
			adjList.remove(node);
		}
	}
	
	/*
	 * A method where, if an edge between two vertices exists, is removed.
	 * 
	 * @param from The source vertex.
	 * @param to The target vertex.
	 */
	public void removeEdge(E from, E to)
	{
		if(adjList.containsKey(from) && adjList.containsKey(to))
		{
			adjList.get(from).remove(to);
		}
		if(adjList.containsKey(to) && adjList.containsKey(from))
		{
			adjList.get(to).remove(from);
		}
	}
	
	/*
	 * A method that returns a list of neighbouring vertices to the given vertex.
	 * 
	 * @param node The vertex, in which its neighbouring nodes have requested.
	 */
	public List<E> getNodeNeighbours(E node)
	{
		if(adjList.containsKey(node))
		{
			return new ArrayList<>(adjList.get(node).keySet());
		}
		return new ArrayList<>();
	}
	
	/*
	 * A method that returns a map of neighbouring nodes as well as their corresponding edge weights for a given vertex.
	 * 
	 * @param node The vertex, in which its neighbouring nodes and weights have requested.
	 * @return A map of adjacent vertices and their edge weights.
	 */
	public Map<E, Double> getNodeNeighboursWeights(E node)
	{
		return adjList.getOrDefault(node, Collections.emptyMap());
	}
	
	/*
     * Gets the weight of the edge between two vertices.
     * 
     * @param from The source vertex.
     * @param to   The destination vertex.
     * @return The weight of the edge, or null if the edge does not exist.
     */
	public Double getEdgeWeight(E from, E to)
	{
		if(adjList.containsKey(from))
		{
			return adjList.get(from).get(to);
		}
		return null;
	}
	
	/*
	 * A method that checks if an edge exists between two vertices.
	 * 
	 * @param from The source vertex.
	 * @param to The destination vertex.
	 * @return True of the edge exists or False if otherwise.
	 */
	public boolean hasEdgeWeight(E from, E to)
	{
		return adjList.containsKey(from) && adjList.get(from).containsKey(to);
	}
	
	/*
	 * A method that returns a set of all the vertices that are currently in the graph
	 * 
	 * @return A set containing all graph vertices.
	 */
	public Set<E> getVertices()
	{
		return adjList.keySet();
	}
}
