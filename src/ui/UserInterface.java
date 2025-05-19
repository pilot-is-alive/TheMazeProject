package ui;

import java.io.File;
import java.io.FileInputStream;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.DrawChoice;
import main.GraphConverter;
import main.MyCanvas;

public class UserInterface extends Application {

	private GraphConverter converter = null;

	@Override
	public void start(Stage primaryStage) throws Exception {
		MyCanvas Canvas = new MyCanvas();
		Canvas.setWidth(800);
		Canvas.setHeight(600);
		Canvas.minWidth(500);
		Canvas.minHeight(500);
		//Canvas.minHeight(550);

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
			converter =  new GraphConverter();
			converter.createGraphFromCanvas(Canvas);

		});

		Button resetBtn = new Button("Reset");
		resetBtn.setOnAction(e->{
			Canvas.reset();
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
				} catch (Exception ex) {
					ex.printStackTrace();
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

		TextArea logArea = new TextArea();
		logArea.setText("Logs, test scores, and error messages...\n");
		// logArea.setPrefWidth(10);
		logArea.setPrefHeight(200);
		logArea.setEditable(true);

		Undobtn.setOnAction(e -> {
			Canvas.undo();
			logArea.appendText("Undo last line.\n");
		});

		
		GridPane.setFillWidth(loadBtn, true);
		GridPane.setFillWidth(saveBtn, true);
		GridPane.setFillWidth(resetBtn, true);
		GridPane.setFillWidth(logArea, true);
		GridPane.setFillWidth(Startbtn, true);
		GridPane.setFillWidth(Canvas, true);
		GridPane.setFillHeight(Canvas, true);
		
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
		
		GridPane rootGrid = new GridPane();
		rootGrid.add(Canvas, 0, 0, 5, 4);
		rootGrid.add(loadBtn, 5, 0);
		rootGrid.add(saveBtn, 5, 1);
		rootGrid.add(resetBtn, 5, 2);
		rootGrid.add(logArea, 5, 3);
		rootGrid.add(Startbtn, 5, 4);
		rootGrid.add(Undobtn, 4, 4);
		rootGrid.add(EscapePointBtn, 3, 4);
		rootGrid.add(Invaderbtn, 2, 4);
		rootGrid.add(Homeownerbtn, 1, 4);
		rootGrid.add(Wallbtn, 0, 4);
		
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
		for (int i = 0; i < 5; i++) {
	        RowConstraints rowConst = new RowConstraints();
	        if (i == 3) { // want to set vertical grow policy for column index 3
	            rowConst.setVgrow(Priority.ALWAYS); // Row 3 grows to take available space
	        }
	        rootGrid.getRowConstraints().add(rowConst);
	    }
		
		rootGrid.setGridLinesVisible(false); // for debug purposes set true

		Scene scene = new Scene(rootGrid, 1600, 900);
		primaryStage.setFullScreen(true);
		primaryStage.setResizable(false);
		primaryStage.setFullScreenExitHint("Drag on canvas to draw walls\nPress on canvas to insert Invader or Homeowner\n");
		primaryStage.setTitle("Home Invader Simulator ");
		primaryStage.setScene(scene);
		primaryStage.show();
		Canvas.render();
	}


}
