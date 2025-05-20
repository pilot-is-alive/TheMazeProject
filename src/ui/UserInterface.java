package ui;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import accse.datastructure.Graph;
import accse.datastructure.GraphDataStructure;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.DrawChoice;
import main.GraphConverter;
import main.MyCanvas;
import main.PixelCoordinate;
import main.User;

public class UserInterface extends Application {

	private GraphConverter converter = null;
	private Graph<PixelCoordinate> mazeGraph = null;
	private MyCanvas Canvas; // make canvas accessible
	private User<PixelCoordinate> pathFinder = new User<>();
	private TextArea logArea = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Canvas = new MyCanvas();
		Canvas.onObjectMovedProperty().set(()-> recalculatePathsAfterMove());
		Canvas.setWidth(800);
		Canvas.setHeight(600);
		Canvas.minWidth(500);
		Canvas.minHeight(500);
		//Canvas.minHeight(550);
		
		logArea = new TextArea();
		logArea.setText("Logs, test scores, and error messages...\n");
		// logArea.setPrefWidth(10);
		logArea.setPrefHeight(200);
		logArea.setEditable(true);
		
		Button ExitBtn = new Button("Exit");
		Image exIcon = new Image(getClass().getResourceAsStream("/ProjectDesign/Resources/esc door.png"));
		ImageView exView = new ImageView(exIcon);
		exView.setFitHeight(16);
		exView.setFitWidth(16);
		ExitBtn.setGraphic(exView);
		ExitBtn.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		ExitBtn.setStyle("-fx-background-color: grey; -fx-text-fill: white;");
		ExitBtn.setOnAction(e ->{
			
			primaryStage.close();
		});
		

		Button Wallbtn = new Button("Walls");
		Wallbtn.setPrefWidth(200);
		Wallbtn.setPrefHeight(40);
		Image wallIcon = new Image(getClass().getResourceAsStream("/ProjectDesign/Resources/Wall.png"));
		ImageView wallView = new ImageView(wallIcon);
		wallView.setFitHeight(25);
		wallView.setFitWidth(25);
		Wallbtn.setGraphic(wallView);
		Wallbtn.setOnAction(e -> {
			Canvas.drawChoice(DrawChoice.WALL);
		});

		Button Invaderbtn = new Button("Invader");
		Invaderbtn.setPrefWidth(200);
		Invaderbtn.setPrefHeight(40);
		Image invIcon = new Image(getClass().getResourceAsStream("/ProjectDesign/Resources/Invader.png"));
		ImageView inView = new ImageView(invIcon);
		inView.setFitHeight(30);
		inView.setFitWidth(30);
		Invaderbtn.setGraphic(inView);
		Invaderbtn.setOnAction(e -> {
			Canvas.drawChoice(DrawChoice.INTRUDER);
		});

		Button Homeownerbtn = new Button("Homeowner");
		Homeownerbtn.setPrefWidth(200);
		Homeownerbtn.setPrefHeight(40);
		Image homeIcon = new Image(getClass().getResourceAsStream("/ProjectDesign/Resources/HomeOwner.png")); 
		ImageView homeView = new ImageView(homeIcon);
		homeView.setFitHeight(30);
		homeView.setFitWidth(30);
		Homeownerbtn.setGraphic(homeView);
		Homeownerbtn.setOnAction(e -> {
			Canvas.drawChoice(DrawChoice.ESCAPER);
		});
		
		Button EscapePointBtn = new Button("Escape Pt.");
		EscapePointBtn.setPrefWidth(200);
		EscapePointBtn.setPrefHeight(40);
		// Image escIcon = new Image(getClass().getResourceAsStream("/ProjectDesign/Resources/EscapePoint.png"))
		// Make sure you have an icon 
		// ImageView escView = new ImageView(escIcon); 
		// escView.setFitHeight(30); 
		// escView.setFitWidth(30); 
		// EscapePointBtn.setGraphic(escView); 
		EscapePointBtn.setOnAction(e -> {
			Canvas.drawChoice(DrawChoice.ESCAPE_POINT);
		});

		Button Startbtn = new Button("Start");
//		Startbtn.setPrefWidth(120);
		Startbtn.setPrefHeight(40);
		Startbtn.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
		Startbtn.setStyle("-fx-background-color: Lightgreen; -fx-text-fill: white;");
		Startbtn.setOnAction(e -> {
			// Create/Recreate the graph from the current canvas state
			logArea.appendText("Building graph from canvas...\n");
			converter =  new GraphConverter();
			converter.createGraphFromCanvas(Canvas);
			mazeGraph = converter.getGraph(); // store the graph
			logArea.appendText("Graph built. Vertices: " + (mazeGraph != null ? mazeGraph.getVertices().size() : 0) + "\n");
			// Find and draw the Escaper's path
			findAndDrawEscaperPath();
			// Find and draw Intruder paths
			findAndDrawIntruderPaths();
			//Enabling drag-and-drop movements
			logArea.appendText("Graph built.Movement handlers potentially enabled.\n");
		});

		Button resetBtn = new Button("Reset");
		resetBtn.setOnAction(e->{
			Canvas.reset();
			mazeGraph = null;
			converter = null;
			logArea.setText("Canvas Reset. \nLogs, test scores, and error messages...\n");
		});
		Button saveBtn = new Button("Save Map");

		Button loadBtn = new Button("Load Map");

		loadBtn.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Blueprint Image");
			fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
					);

			File selectedFile = fileChooser.showOpenDialog(primaryStage);

			if (selectedFile != null) {
				try {
					FileInputStream input = new FileInputStream(selectedFile);
					Image blueprintImage = new Image(input);

					// Draw image on canvas
					Canvas.setBackgroundImage(blueprintImage);
					// reset graph state when loading a new map
					mazeGraph = null;
					converter = null;
					Canvas.setPathToDraw(null);
					logArea.appendText("Loaded map. Draw elements and click 'Build Graph' again.\n");
				} catch (Exception ex) {
					ex.printStackTrace();
					logArea.appendText("Error loading map: " + ex.getMessage() + "\n");
				}
			}
		});


		Button Undobtn = new Button("Undo");
		Undobtn.setPrefWidth(200);
		Undobtn.setPrefHeight(40);
		Undobtn.setStyle("-fx-text-fill: Black;");
		Image undoIcon = new Image(getClass().getResourceAsStream("/ProjectDesign/Resources/undo.png"));
		ImageView undoView = new ImageView(undoIcon);
		undoView.setFitHeight(20);
		undoView.setFitWidth(20);
		Undobtn.setGraphic(undoView);

		Undobtn.setOnAction(e -> {
			Canvas.undo();
			logArea.appendText("Undo last line. Graph may be outdated.\n");
		});

		
		GridPane.setFillWidth(loadBtn, true);
		GridPane.setFillWidth(saveBtn, true);
		GridPane.setFillWidth(resetBtn, true);
		GridPane.setFillWidth(logArea, true);
		GridPane.setFillWidth(Startbtn, true);
		GridPane.setFillWidth(Canvas, true);
		GridPane.setFillHeight(Canvas, true);
		GridPane.setFillWidth(ExitBtn, true);
		
		GridPane.setHalignment(Canvas,javafx.geometry.HPos.CENTER);
		GridPane.setValignment(Canvas, javafx.geometry.VPos.CENTER);
		
		loadBtn.setMaxWidth(Double.MAX_VALUE);
		saveBtn.setMaxWidth(Double.MAX_VALUE);
		resetBtn.setMaxWidth(Double.MAX_VALUE);
		Startbtn.setMaxWidth(Double.MAX_VALUE);
		Invaderbtn.setMaxWidth(Double.MAX_VALUE);
		Homeownerbtn.setMaxWidth(Double.MAX_VALUE);
		Wallbtn.setMaxWidth(Double.MAX_VALUE);
		EscapePointBtn.setMaxWidth(Double.MAX_VALUE);
		Undobtn.setMaxWidth(Double.MAX_VALUE);
		ExitBtn.setMaxWidth(Double.MAX_VALUE);
		
		GridPane rootGrid = new GridPane();
		rootGrid.add(Canvas, 0, 0, 5, 5);
		rootGrid.add(loadBtn, 5, 0);
		rootGrid.add(saveBtn, 5, 1);
		rootGrid.add(resetBtn, 5, 2);
		rootGrid.add(ExitBtn, 5, 3);
		rootGrid.add(logArea, 5, 4);
		rootGrid.add(Startbtn, 5, 5);
		rootGrid.add(Undobtn, 4, 5);
		rootGrid.add(EscapePointBtn, 3, 5);
		rootGrid.add(Invaderbtn, 2, 5);
		rootGrid.add(Homeownerbtn, 1, 5);
		rootGrid.add(Wallbtn, 0, 5);
		
		// Add column constraints to any column of choice
		for (int i = 0; i < 6; i++) {
	        ColumnConstraints colConst = new ColumnConstraints();
	        if (i == 5) { // want to set horizontal grow policy for column index 5
	            //colConst.setHgrow(Priority.ALWAYS); // Column 5 grows to take available space
	            // colConst.setMaxWidth(200);
	        }
	        if (i != 5) {
	        	colConst.setHgrow(Priority.ALWAYS);
	        }
	        rootGrid.getColumnConstraints().add(colConst);
	    }
		
		// Add row constraints to any column of choice
		for (int i = 0; i < 6; i++) {
	        RowConstraints rowConst = new RowConstraints();
	        if (i == 4) { // want to set vertical grow policy for column index 3
	            rowConst.setVgrow(Priority.ALWAYS); // Row 3 grows to take available space
	        }
	        rootGrid.getRowConstraints().add(rowConst);
	    }
		
		rootGrid.setGridLinesVisible(false); // for debug purposes set true

		Scene scene = new Scene(rootGrid, 1600, 900);
		primaryStage.setFullScreen(true);
		primaryStage.setResizable(true);
		primaryStage.setFullScreenExitHint("Drag on canvas to draw walls\nPress on canvas to insert Invader or Homeowner\n");
		primaryStage.setTitle("Home Invader Simulator ");
		primaryStage.setUserData(this);
		primaryStage.setScene(scene);
		primaryStage.show();
		Canvas.render();
	}
	
	private void findAndDrawEscaperPath() {
		if (mazeGraph == null) { 
            logArea.appendText("Error: Graph not built yet. Click 'Build Graph' first.\n"); 
            return; 
        } 
 
        PixelCoordinate startNode = Canvas.getEscaperLocation(); 
        PixelCoordinate endNode = Canvas.getEscapePointLocation(); 
 
        if (startNode == null) { 
            logArea.appendText("Error: Escaper position not set.\n"); 
            Canvas.setPathToDraw(null); // Clear any old path 
            return; 
        } 
        if (endNode == null) { 
            logArea.appendText("Error: Escape point not set.\n"); 
            Canvas.setPathToDraw(null); // Clear any old path 
            return;
        }
        
     // Ensuring start/end nodes exist in the graph (they should if placed within bounds) 
	    if (!mazeGraph.getVertices().contains(startNode)) { 
	         logArea.appendText("Error: Escaper location " + startNode + " not found in graph vertices.\n"); 
	         Canvas.setPathToDraw(null); 
	         return; 
	    } 
	    if (!mazeGraph.getVertices().contains(endNode)) { 
	         logArea.appendText("Error: Escape point location " + endNode + " not found in graph vertices.\n"); 
             Canvas.setPathToDraw(null); 
             return; 
	    }
	    logArea.appendText("Finding path from " + startNode + " to " + endNode + "...\n"); 

	    try { 
	    	// Run Dijkstra 
	    	Map<PixelCoordinate, PixelCoordinate> cameFrom = GraphDataStructure.dijkstraAlgorithm(mazeGraph, startNode, endNode); 

	    	// Reconstruct the path 
	    	List<PixelCoordinate> path = pathFinder.routeTracking(cameFrom, startNode, endNode); 

	    	if (path.isEmpty() || !path.get(path.size() - 1).equals(endNode)) { 
	    		logArea.appendText("Path not found or incomplete.\n"); 
	    		Canvas.setPathToDraw(null); 
	    	} else { 
	    		logArea.appendText("Path found! Length: " + path.size() + " steps.\n"); 
	    		// Tell the canvas to draw this path 
	    		Canvas.setPathToDraw(path); 
	    	} 
	    } catch (Exception ex) { 
	    	logArea.appendText("Error during pathfinding: " + ex.getMessage() + "\n"); 
	    	ex.printStackTrace(); // Print stack trace for debugging 
	    	Canvas.setPathToDraw(null); 
	    } 
	}

	private void findAndDrawIntruderPaths()
	{
		if(mazeGraph==null || Canvas.getEscaperLocation()==null) return;
		PixelCoordinate targetNode=Canvas.getEscaperLocation();
		List<PixelCoordinate> intruderStarts= Canvas.getIntruderLocations();
		
		for(int i=0; i<intruderStarts.size();i++)
		{
			PixelCoordinate StartNode=Canvas.getEscaperLocation();
			try {
				 Map<PixelCoordinate, PixelCoordinate> cameFrom=GraphDataStructure.dijkstraAlgorithm(mazeGraph, StartNode, targetNode);
				 List<PixelCoordinate> path=pathFinder.routeTracking(cameFrom, StartNode, targetNode);
				 
				 if(path.isEmpty()|| !path.get(path.size()-1).equals(targetNode))
				 {
					 logArea.appendText("Path for Intruder "+ i+" not found.\n");
				 }else
				 {
					 logArea.appendText("Path for Intruder "+ i+" found! Length: "+ path.size()+"step.\n");
				 }
			}catch(Exception ex)
			{
				 logArea.appendText("Error finding path for Intruder " + i + ": " +ex.getMessage() + "\n"); 
			}
		}
	}

	public void recalculatePathsAfterMove() {
		// TODO Auto-generated method stub
		logArea.appendText("Object moved.Recalculating paths...\n");
		// recalculate if the graph exist
		if(mazeGraph!=null)
		{
			findAndDrawEscaperPath();//Recalculate for escaper
			findAndDrawIntruderPaths();//Recalculating for Intruder
			
		}else
		{
			logArea.appendText("Cannot recalculate paths: Graph not built.\n");
		}
	}


}
