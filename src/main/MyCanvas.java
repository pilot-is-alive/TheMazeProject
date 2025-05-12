package main;

import java.util.Vector;


import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MyCanvas extends Canvas {
	private Line currentLine;
	private GraphicsContext gc;
	private Vector<Line> lines;
	private Image backgroundImage;

	public MyCanvas() {
		
		System.out.println("Create my canvas");
		gc = super.getGraphicsContext2D();
		
		currentLine = new Line();
		lines = new Vector<Line>();
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
	
	public void render() {

		System.out.println("render...");
		clearCanvas();
		//Draw the image first, if it exists
		
	    if (backgroundImage != null) {
	        gc.drawImage(backgroundImage, 0, 0, getWidth(), getHeight());
	    } else {
	        gc.setFill(Color.WHITE);
	        gc.fillRect(0, 0, getWidth(), getHeight());
	    }

	    gc.setStroke(Color.BLACK);
	    for (Line line : lines) {
	        gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
	    }

		gc.clearRect(0, 0, this.getWidth(), this.getHeight());
		for(Line line: lines) {
			
			gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
			
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
	
}
