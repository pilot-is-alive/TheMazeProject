package main;

import java.util.Vector;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class MyCanvas extends Canvas {
	private Line currentLine;
	private GraphicsContext gc;
	private Vector<Line> lines;
	
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
	
	public void clearCanvas() {
		gc.setFill(Color.WHITE); // or any other visible color
	    gc.fillRect(0, 0, this.getWidth(), this.getHeight()); // fill background
	}
	
	public void render() {
		System.out.println("render...");
		clearCanvas();

	    gc.setStroke(Color.BLACK);
	    for (Line line : lines) {
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
