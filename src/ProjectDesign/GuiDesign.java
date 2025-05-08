package ProjectDesign;


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
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import main.MyCanvas;

public class GuiDesign extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		MyCanvas Canvas = new MyCanvas();
		Canvas.setWidth(500);
		Canvas.setHeight(500);
		Canvas.prefWidth(500);
		Canvas.prefHeight(500);
		
		StackPane canvasWrapper = new StackPane(Canvas);
		canvasWrapper.setPrefSize(500, 500);
		canvasWrapper.setStyle("-fx-background-color: beige;");
		
		Button Wallbtn = new Button("Walls");
		Image wallIcon = new Image(getClass().getResourceAsStream("/ProjectDesign/Resources/Wall.png"));
		ImageView wallView = new ImageView(wallIcon);
		wallView.setFitHeight(25);
		wallView.setFitWidth(25);
		Wallbtn.setGraphic(wallView);
		
		Button Invaderbtn = new Button("Invader");
		Image invIcon = new Image(getClass().getResourceAsStream("/ProjectDesign/Resources/Invader.png"));
		ImageView inView = new ImageView(invIcon);
		inView.setFitHeight(30);
		inView.setFitWidth(30);
		Invaderbtn.setGraphic(inView);
		
		Button Homeownerbtn = new Button("Homeowner");
		Image homeIcon = new Image(getClass().getResourceAsStream("/ProjectDesign/Resources/HomeOwner.png")); 
		ImageView homeView = new ImageView(homeIcon);
		homeView.setFitHeight(30);
		homeView.setFitWidth(30);
		Homeownerbtn.setGraphic(homeView);
		
		Button Undobtn = new Button("Undo");
		Image undoIcon = new Image(getClass().getResourceAsStream("/ProjectDesign/Resources/undo.png"));
		ImageView undoView = new ImageView(undoIcon);
		undoView.setFitHeight(20);
		undoView.setFitWidth(20);
		Undobtn.setGraphic(undoView);
		
		//Button exitbtn = new Button("Exit");
		Button Startbtn = new Button("Start");
		Startbtn.setPrefWidth(120);
		Startbtn.setPrefHeight(40);
		Startbtn.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
		Startbtn.setStyle("-fx-background-color: Lightgreen; -fx-text-fill: white;");
		
		Button resetBtn = new Button("Reset");
	    Button saveBtn = new Button("Save Map");
	    Button loadBtn = new Button("Load Map");
	    
	    TextArea logArea = new TextArea();
        logArea.setText("Logs, test scores, and error messages...\n");
        logArea.setEditable(true);
		
		Undobtn.setOnAction(e -> {
            Canvas.undo();
            logArea.appendText("Undo last line.\n");
        });
		
	
		VBox leftPanel = new VBox(10, Wallbtn, Invaderbtn, Homeownerbtn, Undobtn);
        leftPanel.setAlignment(Pos.TOP_CENTER);
        leftPanel.setPadding(new Insets(20,10,20,10));

        Region spacer = new Region();
        spacer.setPrefHeight(200);

        VBox rightPanel = new VBox(10, resetBtn, saveBtn, loadBtn, spacer, Startbtn);
        rightPanel.setAlignment(Pos.TOP_CENTER);
        rightPanel.setPadding(new Insets(20,10,20,10));
        
        GridPane rightPane = new GridPane();
        rightPane.setPrefWidth(150);
        rightPane.setPrefHeight(150);
        rightPane.setPadding(new Insets(10));
        rightPane.add(rightPanel, 0, 0);
	     
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        GridPane.setFillWidth(rightPane, false);
        GridPane.setFillHeight(rightPane, false);
        GridPane.setHalignment(rightPane, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(rightPane, javafx.geometry.VPos.CENTER);
        
        grid.add(leftPanel, 0, 0);

		 BorderPane root = new BorderPane();
		 root.setLeft(grid);
		 root.setRight(rightPane); 
		 root.setCenter(canvasWrapper);
		 root.setBottom(logArea);
		 root.setStyle("-fx-background-color: lightblue;");
		
		 Scene scene = new Scene(root, 800, 650);
		 primaryStage.setTitle("Home Invader Simulator ");
		 primaryStage.setScene(scene);
		 primaryStage.show();
	}
	

}
