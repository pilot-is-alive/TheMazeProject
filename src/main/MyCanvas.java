package main;

import java.util.List;
import java.util.Vector;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class MyCanvas extends Canvas {
	private Line currentLine;
	private GraphicsContext gc;
	private Vector<Line> lines;
	private Vector<Rectangle> intruders;
	private Rectangle escaper;
	private Rectangle escapePoint;
	private DrawChoice choice;
	private final double squareSideLength = 20;
	private final double lineWeight = 3;
	private Image backgroundImage;
	private List<PixelCoordinate> currentPath; // Store path for drawing
	
	public MyCanvas() {
		System.out.println("Create my canvas");
		gc = super.getGraphicsContext2D();
		currentLine = new Line();
		lines = new Vector<Line>();
		intruders = new Vector<Rectangle>();
		escaper = null;
		escapePoint = null;
		currentPath = null;
		super.setOnMousePressed(new MouseDragEnteredHandler());
		super.setOnMouseReleased(new MouseDragExitHandler());
		this.setOnMouseDragged(new MouseDraggedHandler());
		System.out.println("Canvas created");
		clearCanvas();
	}
	
	public void setBackgroundImage(Image image) {
	    this.backgroundImage = image;
	    render(); // Immediately redraw with the new background
	}
	
	public void clearCanvas() {
		gc.setFill(Color.WHITE); // or any other visible color
	    gc.fillRect(0, 0, this.getWidth(), this.getHeight()); // fill background
	}
	
	public void reset() {
		lines = new Vector<Line>();
		intruders = new Vector<Rectangle>();
		escaper = null;
		escapePoint = null;
		backgroundImage = null;
		currentPath = null; // Reset path
		clearCanvas();
		drawChoice(DrawChoice.WALL); // Reset to wall drawing
	}
	
	public void render() {
		System.out.println("render...");
		clearCanvas();
		// Draw background/loaded image
		if (backgroundImage != null) {
	        gc.drawImage(backgroundImage, 0, 0, getWidth(), getHeight());
	    }
		// Draw walls
	    gc.setStroke(Color.BLACK);
	    gc.setLineWidth(lineWeight);
	    for (Line line : lines) {
	        gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
	    }
	    // Draw path if available (draw before objects so they appear on top)
	    if (currentPath != null && !currentPath.isEmpty()) {
	    	gc.setFill(Color.YELLOW);
	    	for (PixelCoordinate p: currentPath) {
	    		// Draw small dots for the path
	    		gc.fillOval(p.getX() - 1, p.getY() - 1, 3, 3);
	    	}
	    }
	    
	    // Draw intruders
	    gc.setFill(Color.RED);
	    for (Rectangle intruder: intruders) {
			gc.fillRect(intruder.getX(), intruder.getY(), intruder.getWidth(), intruder.getHeight());
		}
	    // Draw escaper if exists
	    if (escaper != null) {
	    	gc.setFill(Color.BLUE);
	    	gc.fillRect(escaper.getX(), escaper.getY(), escaper.getWidth(), escaper.getHeight());
	    }
	    
	}
	
	public void renderDraggedLine() {
		render();
		gc.setStroke(Color.BLACK);
		gc.strokeLine(currentLine.getStartX(), currentLine.getStartY(), currentLine.getEndX(), currentLine.getEndY());
	}
	
	public boolean undo() {
		if (lines.isEmpty()) return false;
		boolean removed = lines.remove(lines.lastElement());
		render();
		return removed;
	}
	
	/**
	 * Choose what entity to draw and
	 * assigning an appropriate event handler
	 * 
	 * @param choice
	 */
	public void drawChoice(DrawChoice choice) {
		if (this.choice == choice) return;
		switch (choice) {
		case WALL: 
			super.setOnMousePressed(new MouseDragEnteredHandler());
			super.setOnMouseReleased(new MouseDragExitHandler());
			this.setOnMouseDragged(new MouseDraggedHandler());
			break;
		case ESCAPER:
		case INTRUDER:
		case ESCAPE_POINT:
			super.setOnMousePressed(new MousePressedHandler());
			// reset line drag handlers
			super.setOnMouseDragged(null);
			super.setOnMouseReleased(null);
			break;
		}
		this.choice = choice;
	}
	
	/**
	 * 
	 * @param rect Rectangle
	 * @return center pixel coordinate of the rectangle
	 */
	private PixelCoordinate getCenterPixel(Rectangle rect) {
		if (rect == null)  return null;
		int centerX = (int) (rect.getX() + squareSideLength / 2);
		int centerY = (int) (rect.getY() + squareSideLength / 2);
		// Ensure coordinates are within canvas bounds
		centerX = Math.max(0, Math.min(centerX, (int) getWidth() - 1));
		centerY = Math.max(0, Math.min(centerY, (int) getHeight() - 1));
		return new PixelCoordinate(centerX, centerY);
	}
	
	public PixelCoordinate getEscaperLocation() {
		return getCenterPixel(escaper);
	}
	
	public PixelCoordinate getEscaperPointLocation() {
		return getCenterPixel(escapePoint);
	}
	
	public List<PixelCoordinate> getIntruderLocations() {
		List<PixelCoordinate> locations = new Vector<>(); // for thread-safety
		for (Rectangle intruder: intruders) {
			PixelCoordinate loc = getCenterPixel(intruder);
			if (loc != null) {
				locations.add(loc);
			}
		}
		return locations;
	}
	
	// Setter for drawing the path
	public void setPathToDraw(List<PixelCoordinate> path) {
		this.currentPath = path;
		render(); // Redraw the canvas to show the new path
	}
	
	// Move functionality for escaper
	public void moveEscaper(double newX, double newY) {
		if (escaper != null) {
			// Calculate top-left from center point (newX, newY)
			escaper.setX(newX - squareSideLength / 2);
			escaper.setY(newY - squareSideLength / 2);
			render();
			// Trigger path recalculation
		}
	}
	
	/**
	 *  Move functionality for intruder
	 * @param index Index of the selected intruder from the list of intruders
	 * @param newX
	 * @param newY
	 */
	public void moveIntruder(int index, double newX, double newY) {
		if (index >= 0 && index < intruders.size()) {
			Rectangle intruder = intruders.get(index); 
            intruder.setX(newX - squareSideLength / 2); 
            intruder.setY(newY - squareSideLength / 2); 
            render(); 
            // Trigger path recalculation
		}
	}
	
	class MouseDraggedHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			currentLine.setEndX(event.getX());
			currentLine.setEndY(event.getY());
			renderDraggedLine();
		}
	}
	
	class MouseDragEnteredHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			System.out.println(event.getEventType().getName());
			currentLine =  new Line();
			currentLine.setStartX(event.getX());
			currentLine.setStartY(event.getY());
			currentLine.setEndX(event.getX());
			currentLine.setEndY(event.getY());
			renderDraggedLine();
		}
	}
	
	class MouseDragExitHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			System.out.println("Exiting drag");
			currentLine.setEndX(event.getX());
			currentLine.setEndY(event.getY());
			lines.add(currentLine);
			render();
		}
	}
	
	/**
	 *  Event handler for when intruder or escaper is drawn
	 */
	class MousePressedHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			double topLeftX;
			double topLeftY;
			double x = event.getX();
			double y = event.getY();
			topLeftX = x - (squareSideLength/2);
			topLeftY = y - (squareSideLength/2);
			
			Rectangle rect = new Rectangle(topLeftX, topLeftY, squareSideLength, squareSideLength);
			if (choice == DrawChoice.INTRUDER) intruders.add(rect);
			else if (choice == DrawChoice.ESCAPER) escaper = rect;
			else if (choice == DrawChoice.ESCAPE_POINT) escapePoint = rect;
			
			render();
		}
	}
	
}
