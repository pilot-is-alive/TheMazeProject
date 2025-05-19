package main;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import accse.datastructure.Graph;
import accse.datastructure.GraphDataStructure;

/*
 * A generic User class that helps the user find a safe point in the maze by implementing Dijikstra's algorithm 
 * to find the shortest & most safe path to exit the maze.
 */
public class User<E> 
{
	/*
	 * A method that implements Dijikstra's algorithm to help the user find the shortest & most safe path
	 * to exit the maze.
	 * 
	 * @param generatedMaze The maze created.
	 * @param userStartPos The user's starting position.
	 * @param safePoint The predefined safety point for the user to exit the maze.
	 * @return A map that shows the safest & shortest path for the user from their starting position.
	 */
	public Map<E, E> userTracker(Graph<E> generatedMaze, E userStartPos, E safePoint)
	{
		Map<E, E> beginningPoint = GraphDataStructure.dijkstraAlgorithm(generatedMaze, userStartPos, safePoint);
		return beginningPoint;
	}
	
	/*
	 * A method that routes the safest & shortest path for the user to exit the maze safely.
	 * 
	 * @param visitedNodes The map of all the nodes that have been visited.
	 * @param userStartPos The user's starting position.
	 * @param safePoint The predefined safety point for the user to exit the maze.
	 * @return A map that shows the safest & shortest path for the user to exit the maze.
	 */
	public List<E> routeTracking(Map<E, E> visitedNodes, E userStartPos, E safePoint)
	{
		E currentPosOfUser = safePoint;
		List<E> safeRoute = new LinkedList<>();
		
		while(currentPosOfUser != null && !(currentPosOfUser.equals(userStartPos)))
		{
			safeRoute.add(0, currentPosOfUser);
			currentPosOfUser = visitedNodes.get(currentPosOfUser);
		}
		
		if(currentPosOfUser != null)
		{
			safeRoute.add(0, userStartPos);
		}
		return safeRoute;
	}
}
