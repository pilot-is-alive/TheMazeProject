import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/*
 * A generic Intruder class that simulates an intruder making its way through a maze.
 * An adaptation of the Breadth-First Search (BFS) is used to determine what is the shortest path 
 * in finding the user. 
 */
public class Intruder<E> 
{	
	// Each child node is tracked back to their parent node.
	Map<E, E> cameFrom = new HashMap<>();
	// A queue is needed for BFS traversal.
	Queue<E> list = new LinkedList<>();
	// A set stores if a node has been visited. 
	Set<E> visit = new HashSet<>();
	
	/*
	 *  A method that applies the BFS traversal, starting from the intruder's 
	 *  starting point to the user's current position.
	 *  
	 *  @param generatedMaze The maze created.
	 *  @param intruderStartPos The intruder's starting position.
	 *  @param userStartPos The user's starting position.
	 *  @return A map of nodes that have been visited.
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
			// If the intruder has found the user, break.
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
	 * A method that tracks the shortest path from the intruder to the user.
	 */
	public List<E> routeTracking(Graph<E> route, E intruderStartPos, E userStartPos)
	{
		Map<E, E> startFrom = intruderTracker(route, intruderStartPos, userStartPos);
		List<E> trackUser = new LinkedList<>();
		
		E currentPosOfUser = userStartPos;
		
		while(currentPosOfUser != null && !(currentPosOfUser.equals(intruderStartPos)))
		{
			trackUser.add(0, currentPosOfUser);
			currentPosOfUser = startFrom.get(currentPosOfUser);
		}
		
		if(currentPosOfUser != null)
		{
			trackUser.add(0, intruderStartPos);
		}
		return trackUser;
	}
}
