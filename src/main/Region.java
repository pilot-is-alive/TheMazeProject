package main;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/*
 * A class that represents a connected group of pixels with shared properties.
 */
public class Region 
{
	private static int nextId = 0;
    private final int id;
    private List<PixelCoordinate> pixels;
    private Set<PixelCoordinate> pixelSet;
    private RegionTypes type;    
    private double area;
    private PixelCoordinate centroid;
    private double boundingBoxWidth;
    private double boundingBoxHeight;
    private double aspectRatio;

    /*
     * The constructor that initialises the region with a list of pixels, assigns ID & computes features.
     * @param pixels List of connected pixels that define the region. 
     */
    public Region(List<PixelCoordinate> pixels) {
        this.id = nextId++;
        this.pixels = pixels;
        this.pixelSet = new HashSet<>(pixels);
        this.type = RegionTypes.UNDEFINED;
        calculateBaseFeatures();
    }

    /**
     * Calculates geometric features of the regions:
     */
    private void calculateBaseFeatures() {
        this.area = pixels.size();
        
        if (pixels.isEmpty()) 
        {
        	return;
        }

        double sumX = 0, sumY = 0;
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;

        for (PixelCoordinate pixel : pixels) {
            int posX = pixel.getX();
            int posY = pixel.getY();
            sumX += posX;
            sumY += posY;

            // Update bounding box limits
            minX = Math.min(minX, posX);
            minY = Math.min(minY, posY);
            maxX = Math.max(maxX, posX);
            maxY = Math.max(maxY, posY);
        }

        this.centroid = new PixelCoordinate((int) (sumX / area), (int) (sumY / area));
        this.boundingBoxWidth = maxX - minX + 1;
        this.boundingBoxHeight = maxY - minY + 1;
        this.aspectRatio = (boundingBoxHeight > 0) ? boundingBoxWidth / boundingBoxHeight : 1.0;
    }

    public int getId() { 
    	return id; 
    }
    
    public List<PixelCoordinate> getPixels() {
    	return pixels; 
    }
    
    public RegionTypes getType() { 
    	return type; 
    }
    
    public double getArea() {
    	return area; 
    }
    
    public PixelCoordinate getCentroid() { 
    	return centroid; 
    }
    
    public double getBoundingBoxWidth() { 
    	return boundingBoxWidth; 
    }
    
    public double getBoundingBoxHeight() { 
    	return boundingBoxHeight; 
    }
    
    public double getAspectRatio() {
    	return aspectRatio; 
    }

    /*
     * Assigns a classification type to this region.
     * @param type A value from RegionTypes enum.
     */
    public void setType(RegionTypes type) {
        this.type = type;
    }

    /**
     * Efficiently checks whether a specific pixel belongs to this region.
     * @param pc Pixel to test
     * @return true if the pixel is part of this region, false otherwise.
     */
    public boolean containsPixel(PixelCoordinate pc) {
        return pixelSet.contains(pc);
    }

    @Override
    public boolean equals(Object o) 
    {
        if (this == o) {
        	return true;
        }
        if (o == null || getClass() != o.getClass()) {
        	return false;
        }
        Region region = (Region) o;
        return id == region.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /*
     * Returns a string representation of the region, useful for debugging.
     */
    @Override
    public String toString() {
        return String.format("Region{id=%d, type=%s, area=%.0f, w/h=%.2f/%.2f, ratio=%.2f}",
                id, type, area, boundingBoxWidth, boundingBoxHeight, aspectRatio);
    }

    /*
     * Resets the ID counter
     */
    public static void resetIdCounter() {
        nextId = 0;
    }
}
