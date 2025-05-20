package main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import accse.datastructure.Graph;

/*
 * A class responsible in building a Region Adjacency Graph based on relationships of pixels between regions.
 * Each region gets treated like a vertex in a graph. However, an edge gets added between two regions if two regions are adjacent, pixel-wise.
 */
public class RegionAdjacencyGraphBuilder 
{
	/*
	 * A method that constructs a graph from a list of image regions where each region becomes a node in the graph.
	 * Edges are generated between regions if those regions are adjacent.
	 * 
	 * @param regionList The list of all segmented regions in the image.
	 * @param width The width of the canvas in pixels.
	 * @param height The height of the canvas in pixels.
	 * @return A graph where each region is a node & edges represent direct pixel-level adjacency.
	 */
	public Graph<Region> buildRegionGraph(List<Region> regionList, int width, int height) 
	{
		Graph<Region> regionGraph = new Graph<>();
		// This helps us keep track as to which region each pixel belongs to.
		Map<PixelCoordinate, Region> pixelOwnership = new HashMap<>();
		
		// Add each region to the graph & register each pixel with its owning region.
		for (Region currentRegion : regionList) 
		{
			regionGraph.insertVertex(currentRegion);
			for (PixelCoordinate pixel : currentRegion.getPixels()) 
			{
				pixelOwnership.put(pixel, currentRegion);
			}
		}
		
		/* Directions are defined to search for adjacent pixels.
		 * up, down, left, and right
		 */
		int[] dirX = {0, 0, 1, -1};
		int[] dirY = {1, -1, 0, 0};
		
		// We need to ensure that for every region, we must check the edge of each pixel to find neighbouring regions.
		for (Region sourceRegion : regionList) 
		{
			for (PixelCoordinate pixel : sourceRegion.getPixels()) 
			{	
				for (int direction = 0; direction < 4; direction++) 
				{
					int neighbourX = pixel.getX() + dirX[direction];
					int neighbourY = pixel.getY() + dirY[direction];
					
					// Only consider pixels that are still within canvas bounds.
					if (neighbourX >= 0 && neighbourX < width && neighbourY >= 0 && neighbourY < height) 
					{
						PixelCoordinate neighbourPixel = new PixelCoordinate(neighbourX, neighbourY);
						Region neighbourRegion = pixelOwnership.get(neighbourPixel);
						
						// Connect neighbouring pixels if they belong to a different region.
						if (neighbourRegion != null && !neighbourRegion.equals(sourceRegion)) 
						{
							// Avoiding any duplicate edges.
							if (!regionGraph.hasEdge(sourceRegion, neighbourRegion)) 
							{
								regionGraph.insertEdge(sourceRegion, neighbourRegion, 1.0);
							}
						}
					}
				}
			}
		}
		return regionGraph;
	}
}
