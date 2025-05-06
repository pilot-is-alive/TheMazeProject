import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class GraphDataStructure<E> 
{
	public static <E> Map<E, E> djikstraAlgorithm(Graph<E> graph, E start, E end)
	{
		Map<E, Double> distance = new HashMap<>();
		Map<E, E> cameFrom = new HashMap<>();
		PriorityQueue<E> priorQueue = new PriorityQueue<>(Comparator.comparingDouble(distance::get));
		Set<E> visit = new HashSet<>();
		
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
		
		while(!(priorQueue.isEmpty()))
		{
			E current = (E) priorQueue.poll();
			if(current.equals(end))
			{
				break;
			}
			else if(visit.contains(current))
			{
				continue;
			}
			visit.add(current);
			
			for(E neighbourNode : graph.getNodeNeighbours(current))
			{
				if(!(visit.contains(neighbourNode)))
				{
					Double newDistance = distance.get(current) + 1;
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
