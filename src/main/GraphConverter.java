package main;

import java.util.HashMap;
import java.util.Map;

import accse.datastructure.Graph;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


public class GraphConverter {
	// HouseGraph will store PixelCoordinates as vertices
	private Graph<PixelCoordinate> HouseGraph = null;
	// pixelColorMap will map vertices in HouseGraph with their respective color
	private Map<PixelCoordinate, Color> pixelColorMap;
	
	public GraphConverter() {
		HouseGraph = new Graph<PixelCoordinate>();
		pixelColorMap = new HashMap<>();
	}
	
	public Graph<PixelCoordinate> getGraph() {
		return HouseGraph;
	}
	
	public void createGraphFromCanvas(MyCanvas canvas) {
		WritableImage image = canvas.snapshot(null, null);
		PixelReader pixIn = image.getPixelReader();
		
		int width = (int) image.getWidth();
        int height = (int) image.getHeight();
		
		// Creating vertices and storing their color
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Color color = pixIn.getColor(x, y); // get color of pixel from given coordinate
				PixelCoordinate currentPixelCoord = new PixelCoordinate(x, y); // store coordinate as PixelCoordinate
				this.HouseGraph.insertVertex(currentPixelCoord); // insert the stored PixelCoordinate
				this.pixelColorMap.put(currentPixelCoord, color); // map the coordinate to its color
				
			}
		}
		
		// Creating edges between neighbouring pixels
		
		// define relative coordinate offsets for the 8 neighbours
		          //TL  T  TR  L  R   BL B  BR
		int[] dx = {-1, 0, 1, -1, 1, -1, 0, 1}; // x-offsets
		int[] dy = {-1, -1, -1, 0, 0 , 1, 1, 1}; // y-offsets
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < dy.length; x++) {
				PixelCoordinate currentPixelCoord = new PixelCoordinate(x, y); // storing the coordinate as PixelCoordinate
				Color currentColor = this.pixelColorMap.get(currentPixelCoord); // getting the color associated with the coordinate from the map
				if (currentColor == null) continue; // just in case
				
				// Iterate through the 8 neighbours
				for (int i = 0; i < 8; i++) {
					int neighbourX = x + dx[i]; // get X coord of (i+1)th neighbour
					int neighbourY = y + dy[i]; // get Y coord of (i+1)th neighbour
					
					// check if neighbour is within bounds (exists)
					if (neighbourX >= 0 && neighbourX < width && neighbourY >= 0 && neighbourY < height) {
						PixelCoordinate neighborPixelCoord = new PixelCoordinate(neighbourX, neighbourY); // storing neighbour coordinate as PixelCoordinate
						Color neighbourColor = this.pixelColorMap.get(neighborPixelCoord); // get color associated with coordinate, if it exists
						if (neighbourColor == null) continue; // just in case; but should not happen
						double weight = calcEdgeWeight(currentColor, neighbourColor); // calculate weight between current colour and neighbour colour
						this.HouseGraph.insertEdge(currentPixelCoord, neighborPixelCoord, weight); // insert edge between the vertices with calculated weight
					}
				}
			}
		}
		
		// Logging results:
		System.out.println("Graph creation complete. \n"
				+ "Vertices created: " + this.HouseGraph.getVertices().size() 
				+ "\nExpected verices: " + width*height
				);
		
		
	}
	
	/** 
	* Calculates the cost (weight) of moving between two pixels based on color. 
	* Lower weight means easier/cheaper to traverse (more similar colors, not walls). 
	* Higher weight means harder/more expensive (dissimilar colors, walls). 
	* 
	* @param A Color of the first pixel. 
	* @param B Color of the second pixel. 
	* @return The calculated edge weight (cost). 
	*/
	private double calcEdgeWeight(Color A, Color B) {
		double redDiff = Math.abs(A.getRed() - B.getRed()); 
        double greenDiff = Math.abs(A.getGreen() - B.getGreen()); 
        double blueDiff = Math.abs(A.getBlue() - B.getBlue()); 
        double colorDistance = redDiff + greenDiff + blueDiff; // Range 0.0 to 3.0
        
        // Basic cost: add 1.0 to ensure no zero-cost edges and provide base traversal cost
        double baseCost = 1.0 + colorDistance * 10;  // Scale difference to make it more significant than base cost
		
        // Penalize walls heavily
		double wallPenalty = 10000.0; // Very high cost for entering a wall pixel 
        boolean aIsWall = isWallColor(A); 
        boolean bIsWall = isWallColor(B); 
 
        // If moving *into* a wall pixel, make it extremely expensive 
	    if (bIsWall) { 
	         return wallPenalty; 
	    } 
	    
	    return baseCost;
	}
	
	/**
	 * Helper method to determine if a color represents a wall (e.g. black or very dark)
	 */
	private boolean isWallColor(Color c) {
		// Consider black or near-black as walls 
        double threshold = 0.1; // Adjustable 
        return c.getRed() < threshold && c.getGreen() < threshold && c.getBlue() < threshold; 
	}
	
}
