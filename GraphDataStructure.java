import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/*
 * A generic GraphDataStructure class that contains an adaptation of Dijkstra's algorithm fitted for our project.
 * This algorithm is used to find the shortest, weighted path between two vertices in a graph.
 */
public class GraphDataStructure<E> 
{
	/* 
	 * A method that applies an adaptation of Dijkstra's algorithm.
	 * A priority queue is used to find the next possible shortest path.
	 * 
	 * @param graph The weighted graph to search.
	 * @param start The starting vertex.
	 * @param end The target vertex.
	 * @return A map showing the shortest path.
	 */
	public static <E> Map<E, E> dijkstraAlgorithm(Graph<E> graph, E start, E end)
	{
		Map<E, Double> distance = new HashMap<>();
		Map<E, E> cameFrom = new HashMap<>();
		PriorityQueue<E> priorQueue = new PriorityQueue<>(Comparator.comparingDouble(distance::get));
		Set<E> visit = new HashSet<>();
		
		// Initialise node distances from 0 to infinity (start node to end node).
		for(E vertex : graph.getVertices())
		{
			if(vertex.equals(start))
			{
				distance.put((E) vertex, (double) 0);
				priorQueue.add((E) vertex);
			}
			else
			{
				distance.put(vertex, Double.POSITIVE_INFINITY);
			}
		}
		
		// Dijkstra loop
		while(!(priorQueue.isEmpty()))
		{
			E current = (E) priorQueue.poll();
			// If the path is found, end the loop otherwise continue until found.
			if(current.equals(end))
			{
				break;
			}
			else if(visit.contains(current))
			{
				continue;
			}
			visit.add(current);
			
			// Check to see which neighbouring nodes provide the shortest path.
			for(E neighbourNode : graph.getNodeNeighbours(current))
			{
				if(!(visit.contains(neighbourNode)))
				{
					Double weightOfEdge = graph.getEdgeWeight(current, neighbourNode);
					if(weightOfEdge == null)
					{
						continue;
					}
					
					Double newDistance = distance.get(current) + weightOfEdge;
					if(newDistance < distance.get(neighbourNode))
					{
						distance.put(neighbourNode, newDistance);
						cameFrom.put(neighbourNode, current);
						priorQueue.add((E) neighbourNode);
					}
				}
			}
		}
		return cameFrom;
	}
}

