package accse.datastructure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph<E> 
{
	public Map<E, List<E>> adjList;
	
	public Graph()
	{
		adjList = new HashMap<>();
	}
	
	public void insertVertex(E node)
	{
		if(!(adjList.containsKey(node)))
		{
			adjList.putIfAbsent(node, new ArrayList<>());
		}
	}
	
	public void insertEdge(E from, E to)
	{
		
		if(adjList.containsKey(from) && adjList.containsKey(to))
		{
			adjList.get(from).add(to);
			adjList.get(to).add(from);
		}
	}
	
	public void removeVertex(E node)
	{
		if(adjList.containsKey(node))
		{
			for(E nodeNeighbour : adjList.get(node))
			{
				adjList.get(nodeNeighbour).remove(node);
			}
			adjList.remove(node);
		}
	}
	
	public void removeEdge(E from, E to)
	{
		if(adjList.containsKey(from) && adjList.containsKey(to))
		{
			adjList.get(from).remove(to);
			adjList.get(to).remove(from);
		}
	}
	
	public List<E> getNodeNeighbours(E node)
	{
		return adjList.getOrDefault(node, new ArrayList<>());
	}
	
	public Set<E> getVertices()
	{
		return adjList.keySet();
	}
}
