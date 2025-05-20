package main;

import java.util.List;

import accse.datastructure.Graph;

public class RegionRules 
{
	// Threshold constants for classifying regions by area and shape
	// Minimum pixels for a room
    private static final double ROOM_MIN_AREA = 500; 
    // Min width/height ratio for a room
    private static final double ROOM_ASPECT_RATIO_MIN = 0.5;
    // Max width/height ratio for a room
    private static final double ROOM_ASPECT_RATIO_MAX = 2.0;
    // Max pixels for a dead-end passage
    private static final double DEAD_END_MAX_AREA = 200;            

    /**
     * Classify each region using heuristic rules based on area, aspect ratio, and graph connectivity.
     * 
     * @param regions List of regions to classify.
     * @param rag Region Adjacency Graph (RAG) describing region neighbors.
     * @param escaperLoc Location of the user/escaper.
     * @param intruderLocs Locations of intruders.
     * @param escapePointLoc Location of the escape point.
     */
    public void classifyRegions(List<Region> regions, Graph<Region> regionGraph, PixelCoordinate userPos, List<PixelCoordinate> intruderPos, PixelCoordinate safeZonePos) 
    {
        for (Region currentRegion : regions) 
        {
            // Skip wall regions as they are pre-classified
            if (currentRegion.getType() == RegionTypes.WALL_STRUCTURE) 
            {
                continue;
            }

            // Count how many open-space neighbors this region has in the RAG
            int openNeighborCount = 0;
            if (regionGraph.adjList.containsKey(currentRegion)) 
            {
                for (Region neighbor : regionGraph.getNodeNeighbours(currentRegion)) 
                {
                    if (neighbor.getType() != RegionTypes.WALL_STRUCTURE) 
                    {
                        openNeighborCount++;
                    }
                }
            }

            // Rule-based classification by area and aspect ratio thresholds
            if (currentRegion.getArea() >= ROOM_MIN_AREA && currentRegion.getAspectRatio() >= ROOM_ASPECT_RATIO_MIN && currentRegion.getAspectRatio() <= ROOM_ASPECT_RATIO_MAX) 
            {
            	currentRegion.setType(RegionTypes.ROOM);
            } 
            else if (openNeighborCount == 1 && currentRegion.getArea() < DEAD_END_MAX_AREA) 
            {
                // Potential dead-end passage, verify if it leads to escape point
                boolean leadsToEscape = false;
                
                if (safeZonePos != null && regionGraph.adjList.containsKey(currentRegion) && !regionGraph.getNodeNeighbours(currentRegion).isEmpty()) 
                {    
                    Region Neighbour = null;
                    // Find the single open neighbor region
                    for (Region neighbour : regionGraph.getNodeNeighbours(currentRegion)) 
                    {
                        if (neighbour.getType() != RegionTypes.WALL_STRUCTURE)
                        {
                            Neighbour = neighbour;
                            break;
                        }
                    }

                    // Check if this neighbor contains the escape point pixel
                    if (Neighbour != null && Neighbour.containsPixel(safeZonePos)) 
                    {
                        leadsToEscape = true;
                        currentRegion.setType(RegionTypes.OPEN_SPACE);
                    }
                }
            }
        }
    }
}
