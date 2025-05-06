import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class Intruder<E> 
{	
	Map<E, E> cameFrom = new HashMap<>();
	Queue<E> list = new LinkedList<>();
	Set<E> visit = new HashSet<>();
	
	/*
	 *  Based on BFS logic, adapted to track an intruder in the maze.
	 *  Keeps track of visited positions using a Set<E>.
	 *  Uses a Queue<E> to explore neighbouring positions in a BFS manner.
	 *  Every new node found is recorded where it came from in the cameFrom Map.
	 *  This map is used to reconstruct the shortest path from the intruder to the user.
	 */
	public Map<E, E> intruderTracker(Graph<E> generatedMaze, E intruderStartPos, E userStartPos)
	{
		cameFrom.clear();
		visit.clear();
		list.clear();
		
		list.add(intruderStartPos);
		visit.add(intruderStartPos);
		
		while(!(list.isEmpty()))
		{
			E currentPos = list.poll();
			
			if(currentPos.equals(userStartPos))
			{
				break;
			}
			else
			{
				for(E neighbourNode : generatedMaze.getNodeNeighbours(currentPos))
				{
					if(!(visit.contains(neighbourNode)))
					{
						visit.add(neighbourNode);
						cameFrom.put(neighbourNode, currentPos);
						list.add(neighbourNode);
					}
				}
			}
		}
		return cameFrom;
	}
	
	/*
	 * This method reconstructs the path from the user's position back to the intruder's start position using the data from the intruderTracker.
	 * Calls the intruderTracker to get the cameFrom Map.
	 * The path is built in reverse order so that the final list shows the correct route from the intruder to the user.
	 */
	public List<E> routeTracking(Graph<E> route, E intruderStartPos, E userStartPos)
	{
		Map<E, E> startFrom = intruderTracker(route, intruderStartPos, userStartPos);
		List<E> escapeRoute = new LinkedList<>();
		
		E currentPos = userStartPos;
		
		while(currentPos != null && !(currentPos.equals(intruderStartPos)))
		{
			escapeRoute.add(0, currentPos);
			currentPos = startFrom.get(currentPos);
		}
		
		if(currentPos != null)
		{
			escapeRoute.add(0, intruderStartPos);
		}
		return escapeRoute;
	}
}
