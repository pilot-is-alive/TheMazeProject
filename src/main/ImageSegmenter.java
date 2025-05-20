package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

/*
 * Segments an image into regions representing walls or open spaces.
 */
public class ImageSegmenter 
{
	/*
	 * Checks if the given colour corresponds to a wall pixel based on the threshold.
	 * 
	 * @param colour The pixel colour.
	 * @param threshold The threshold for determining if pixel is a wall.
	 * @return True if the pixel is a wall pixel, false otherwise.
	 */
	private boolean isWallPixel(Color colour, double threshold) 
	{
		return colour.getRed() < threshold && colour.getGreen() < threshold && colour.getBlue() < threshold;
	}

	/*
	 * Segments the image into connected regions of walls and open spaces.
	 * Uses Breadth-First Search (BFS) to group pixels with similar wall status.
	 */
	public List<Region> segmentImage(WritableImage image, double wallThreshold) 
	{
		// Reset region IDs for each new segmentation
		Region.resetIdCounter(); 
		PixelReader reader = image.getPixelReader();
		int width = (int) image.getWidth();
		int height = (int) image.getHeight();
		boolean[][] visited = new boolean[width][height];
		List<Region> regions = new ArrayList<>();
		
		// Iterate over every pixel.
		for (int y = 0; y < height; y++) 
		{
			for (int x = 0; x < width; x++) 
			{
				if (!visited[x][y]) 
				{
					List<PixelCoordinate> regionPixels = new ArrayList<>();
					Queue<PixelCoordinate> toVisit = new LinkedList<>();
					PixelCoordinate startPixel = new PixelCoordinate(x, y);
					Color startPixelColor = reader.getColor(x,y);
					boolean isCurrentSegmentWallType = isWallPixel(startPixelColor, wallThreshold);
					toVisit.add(startPixel);
					visited[x][y] = true;
					
					// BFS to find connected pixels of same wall status.
					while (!toVisit.isEmpty()) 
					{
						PixelCoordinate currentPixel = toVisit.poll();
						regionPixels.add(currentPixel);
						
						// Check neighbours
						int[] dirX = {0, 0, 1, -1};
						int[] dirY = {1, -1, 0, 0};
						for (int i = 0; i < 4; i++) 
						{
							int neighbourX = currentPixel.getX() + dirX[i];
							int neighbourY = currentPixel.getY() + dirY[i];
							if (neighbourX >= 0 && neighbourX < width && neighbourY >= 0 && neighbourY < height && !visited[neighbourX][neighbourY]) 
							{
								Color neighborColor = reader.getColor(neighbourX, neighbourY);
								if (isWallPixel(neighborColor, wallThreshold) == isCurrentSegmentWallType) 
								{
									visited[neighbourX][neighbourY] = true;
									toVisit.add(new PixelCoordinate(neighbourX, neighbourY));
								}
							}
						}
					}
					if (!regionPixels.isEmpty()) 
					{
						Region newRegion = new Region(regionPixels);
						newRegion.setType(isCurrentSegmentWallType ? RegionTypes.WALL_STRUCTURE : RegionTypes.OPEN_SPACE);
						regions.add(newRegion);
					}
				}
			}
		}
		return regions;
	}
}
