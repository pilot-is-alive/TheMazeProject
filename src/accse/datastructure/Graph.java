package accse.datastructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph<E> 
{
	public Map<E, Map<E, Double>> adjList;
	
	public Graph()
	{
		adjList = new HashMap<>();
	}
	
	public void insertVertex(E node)
	{
		if(!(adjList.containsKey(node)))
		{
			adjList.putIfAbsent(node, new HashMap<>());
		}
	}
	
	public void insertEdge(E from, E to, double weight)
	{
		
		if(adjList.containsKey(from) && adjList.containsKey(to))
		{
			adjList.get(from).put(to, weight);
			adjList.get(to).put(from, weight);
		}
	}
	
	public void removeVertex(E node)
	{
		if(adjList.containsKey(node))
		{
			// copying the keyset to avoid ConcurrentModificationException 
			// since we are modifying the underlying maps of the neighbours
			Set<E> neighbours = Set.copyOf(adjList.get(node).keySet());
			
			for(E nodeNeighbour : neighbours)
			{
				if (adjList.containsKey(nodeNeighbour)) {
					adjList.get(nodeNeighbour).remove(node);
				}
			}
			adjList.remove(node);
		}
	}
	
	public void removeEdge(E from, E to)
	{
		if(adjList.containsKey(from) && adjList.get(from).containsKey(to))
		{
			adjList.get(from).remove(to);
		}
		if (adjList.containsKey(to) && adjList.get(to).containsKey(from)) {
			adjList.get(to).remove(from);
		}
	}
	
	public List<E> getNodeNeighbours(E node)
	{
		if (adjList.containsKey(node)) {
			return new ArrayList<>(adjList.get(node).keySet());
		}
		return new ArrayList<>();
	}
	
	public Map<E, Double> getNodeNeighboursWeights(E node)
	{
		return adjList.getOrDefault(node, Collections.emptyMap());
	}
	
	/**
     * Gets the weight of the edge between two vertices.
     *
     * @param from The source vertex.
     * @param to   The destination vertex.
     * @return The weight of the edge, or null if the edge does not exist.
     */
	public Double getEdgeWeight(E from, E to) {
		if (adjList.containsKey(from)) {
			return adjList.get(from).get(to);
		}
		
		return null;
	}
	
	public boolean hasEdge(E from, E to) {
		return adjList.containsKey(from) &&  adjList.get(from).containsKey(to);
	}
	
	public Set<E> getVertices()
	{
		return adjList.keySet();
	}
}
