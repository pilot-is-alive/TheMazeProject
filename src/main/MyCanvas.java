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
		gc = super.getGraphicsContext2D();
		
		currentLine = new Line();
		lines = new Vector<Line>();
		super.setOnMouseDragEntered(new MouseDragEnteredHandler());
		super.setOnMouseDragExited(new MouseDragExitHandler());
		super.setOnMouseDragged(new MouseDraggedHandler());
	}
	
	public void render() {
		gc.clearRect(0, 0, this.getWidth(), this.getHeight());
		for(Line line: lines) {
			
			gc.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
			
		}
	}
	
	public void renderDraggedLine() {
		render();
		gc.strokeLine(currentLine.getStartX(), currentLine.getStartY(), currentLine.getEndX(), currentLine.getEndY());
	}
	
	public Line undo() {
		if (lines.isEmpty()) return null;
		Line removedLine = lines.removeLast();
		render();
		return removedLine;
	}
	
	class MouseDragEnteredHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			currentLine =  new Line();
			currentLine.setStartX(event.getX());
			currentLine.setStartY(event.getY());
			renderDraggedLine();
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
	
	class MouseDragExitHandler implements EventHandler<MouseEvent> {
		@Override
		public void handle(MouseEvent event) {
			currentLine.setEndX(event.getX());
			currentLine.setEndY(event.getY());
			lines.add(currentLine);
			render();
		}
	}
	
}
