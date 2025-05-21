package main;

/*
 * Enum defining different types of regions detected in the environment.
 * These regions help classify areas of the maze or home layout for decision-making & pathfinding.
 */
public enum RegionTypes 
{
	WALLS, // Region representing walls of the user's home.
	ROOM, // A room of the user's home.
	DOORS, // The doors connecting the rooms/regions in the house.
	USER_RADIUS, // The region of the user.
	INTRUDER_RADII, // The region of the intruders
	ESCAPE_POINT, // The region representing the escape point.
	UNDEFINED, // Default state before classification.
	WALL_STRUCTURE, // Wall segments
	OPEN_SPACE;// General traversable area.
	
}
