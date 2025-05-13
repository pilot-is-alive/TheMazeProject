package main;

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
	private DrawChoice choice;
	private final double squareSideLength = 20;
	private final double lineWeight = 3;
	private Image backgroundImage;
	
	public MyCanvas() {
		System.out.println("Create my canvas");
		gc = super.getGraphicsContext2D();
		currentLine = new Line();
		lines = new Vector<Line>();
		intruders = new Vector<Rectangle>();
		escaper = null;
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
		backgroundImage = null;
		clearCanvas();
	}
	
	public void render() {
		System.out.println("render...");
		clearCanvas();
		if (backgroundImage != null) {
	        gc.drawImage(backgroundImage, 0, 0, getWidth(), getHeight());
	    }
	    gc.setStroke(Color.BLACK);
	    gc.setLineWidth(lineWeight);
	    for (Line line : lines) {
	        gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
	    }
	    
	    gc.setFill(Color.RED);
	    for (Rectangle intruder: intruders) {
			gc.fillRect(intruder.getX(), intruder.getY(), intruder.getWidth(), intruder.getHeight());
		}
	    
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
	
	public Line undo() {
		if (lines.isEmpty()) return null;
		Line removedLine = lines.removeLast();
		render();
		return removedLine;
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
			super.setOnMousePressed(new MousePressedHandler());
			// reset line to not be drawn
			super.setOnMouseDragged(null);
			super.setOnMouseReleased(null);
			break;
		}
		this.choice = choice;
		
		
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
			if (choice == DrawChoice.ESCAPER) escaper = rect;
			
			render();
		}
	}
	
}
