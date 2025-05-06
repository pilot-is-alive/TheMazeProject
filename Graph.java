import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph<E> 
{
	public Map<E, List<E>> hashMap;
	
	public Graph()
	{
		hashMap = new HashMap<>();
	}
	
	public void insertVertex(E node)
	{
		if(!(hashMap.containsKey(node)))
		{
			hashMap.putIfAbsent(node, new ArrayList<>());
		}
	}
	
	public void insertEdge(E from, E to)
	{
		
		if(hashMap.containsKey(from) && hashMap.containsKey(to))
		{
			hashMap.get(from).add(to);
			hashMap.get(to).add(from);
		}
	}
	
	public void removeVertex(E node)
	{
		if(hashMap.containsKey(node))
		{
			for(E nodeNeighbour : hashMap.get(node))
			{
				hashMap.get(nodeNeighbour).remove(node);
			}
			hashMap.remove(node);
		}
	}
	
	public void removeEdge(E from, E to)
	{
		if(hashMap.containsKey(from) && hashMap.containsKey(to))
		{
			hashMap.get(from).remove(to);
			hashMap.get(to).remove(from);
		}
	}
	
	public List<E> getNodeNeighbours(E node)
	{
		return hashMap.getOrDefault(node, new ArrayList<>());
	}
	
	public Set<E> getVertices()
	{
		return hashMap.keySet();
	}
}
