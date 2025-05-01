package main;

import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class GraphConverter {
	public static Graph graph;
	
	public static Graph toGraph(MyCanvas canvas) {
		WritableImage image = canvas.snapshot(null, null);
		PixelReader pixIn = image.getPixelReader();
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				pixIn.getColor(x, y);
			}
		}
		return null;
	}
	

}
