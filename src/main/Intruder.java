package main;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import accse.datastructure.Graph;
import accse.datastructure.GraphDataStructure;


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
	 * A method that implements Dijikstra's algorithm which maps out a path for the intruder to find the user.
	 * 
	 * @param generatedMaze The maze created.
	 * @param intruderStarPos The intruder's starting position
	 * @param userStartPos The user's starting position.
	 * @return A map that shows the shortest path from the intruder to the user.
	 */
	public Map<E, E> intruderTracker(Graph<E> generatedMaze, E intruderStartPos, E userStartPos)
	{
		Map<E, E> IntruderBeginPoint = GraphDataStructure.dijkstraAlgorithm(generatedMaze, intruderStartPos, userStartPos);
		return IntruderBeginPoint;
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

