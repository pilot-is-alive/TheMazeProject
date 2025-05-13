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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
		Canvas.setWidth(600);
		Canvas.setHeight(550);
		Canvas.prefWidth(500);
		Canvas.prefHeight(500);
		
		StackPane canvasWrapper = new StackPane(Canvas);
		canvasWrapper.setPrefSize(500, 500);
		canvasWrapper.setStyle("-fx-background-color: beige;");
		
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
		
		Button exitbtn = new Button("Exit");
		
		Button Startbtn = new Button("Start");
		Startbtn.setPrefWidth(120);
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
		Undobtn.setStyle("-fx-background-color: Grey; -fx-text-fill: Black;");
		Image undoIcon = new Image(getClass().getResourceAsStream("/ProjectDesign/Resources/undo.png"));
		ImageView undoView = new ImageView(undoIcon);
		undoView.setFitHeight(20);
		undoView.setFitWidth(20);
		Undobtn.setGraphic(undoView);
	    
	    TextArea logArea = new TextArea();
        logArea.setText("Logs, test scores, and error messages...\n");
        logArea.setPrefWidth(10);
        logArea.setPrefHeight(200);
        logArea.setEditable(true);
		
		Undobtn.setOnAction(e -> {
            Canvas.undo();
            logArea.appendText("Undo last line.\n");
        });
		
	
		HBox Bottompanel = new HBox(10, Wallbtn, Invaderbtn, Homeownerbtn, Undobtn);
        Bottompanel.setAlignment(Pos.CENTER);
        Bottompanel.setPadding(new Insets(0));

        Region srtspacer = new Region();
        srtspacer.setPrefHeight(150);
        
        Region logspacer= new Region();
        logspacer.setPrefHeight(50);

        VBox rightPanel = new VBox(10, resetBtn, saveBtn, loadBtn,logspacer, logArea ,srtspacer, Startbtn);
        rightPanel.setAlignment(Pos.TOP_CENTER);
        rightPanel.setPadding(new Insets(0));
        
		
		 GridPane rightPane = new GridPane(); 
		 rightPane.setPrefWidth(150);
		 rightPane.setPrefHeight(150); rightPane.setPadding(new Insets(10));
		 rightPane.add(rightPanel, 0, 0);
		  
		  GridPane grid = new GridPane();
		  grid.setPadding(new Insets(10));
		  grid.setHgap(10);
		  grid.setVgap(10);
		  GridPane.setFillWidth(rightPane, false);
		  GridPane.setFillHeight(rightPane, false);
		  GridPane.setHalignment(rightPane,javafx.geometry.HPos.CENTER); GridPane.setValignment(rightPane, javafx.geometry.VPos.CENTER);
		 
        
       // grid.add(leftPanel, 0, 0);

		 BorderPane root = new BorderPane();
		 root.setLeft(grid);
		 root.setRight(rightPanel); 
		 root.setCenter(canvasWrapper);
		 root.setBottom(Bottompanel);
		 root.setStyle("-fx-background-color: lightblue;");
		
		 Scene scene = new Scene(root, 800, 650);
		 primaryStage.setTitle("Home Invader Simulator ");
		 primaryStage.setScene(scene);
		 primaryStage.show();
		 Canvas.render();
	}
	

}
