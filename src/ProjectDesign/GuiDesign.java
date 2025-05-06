package ProjectDesign;


import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
		
		
		
		Button Wallbtn = new Button("walls");
		Button Invaderbtn = new Button("Invader");
		Button Homeownerbtn = new Button("Homeowner");
		Button exitbtn = new Button("Exit");
		Button Startbtn = new Button("Start");
		Button Undobtn = new Button("Undo");
		TextArea txtdescrip = new TextArea();
		
		Undobtn.setOnAction(e -> {
            Canvas.undo();
            txtdescrip.appendText("Undo last line.\n");
        });
		
		Label txtarea= new Label("Description Box");
		
		//ToolBar btnBar = new ToolBar(Wallbtn, Invaderbtn, Homeownerbtn, exitbtn, Startbtn, Undobtn);
		VBox leftPanel = new VBox(10, Wallbtn, Invaderbtn, Homeownerbtn, Undobtn, Startbtn);
        leftPanel.setAlignment(Pos.TOP_CENTER);
        leftPanel.setPadding(new Insets(10));
		 
		Button resetBtn = new Button("Reset");
	    Button saveBtn = new Button("Save Map");
	    Button loadBtn = new Button("Load Map");
	    
	    VBox rightPanel = new VBox(10, resetBtn, saveBtn, loadBtn);
        rightPanel.setAlignment(Pos.TOP_CENTER);
        rightPanel.setPadding(new Insets(10));
		 
		/*
		 * BorderPane brdr = new BorderPane(); brdr.setBottom(btnBar);
		 * brdr.setCenter(Canvas);
		 */
	    TextArea logArea = new TextArea();
        logArea.setText("Logs, test scores, and error messages...");
        logArea.setEditable(false);
        //logArea.setPrefRowCount(4);
        
        
        StackPane centerPane = new StackPane(Canvas);
        centerPane.setPrefWidth(500);
        centerPane.setPrefHeight(500);
        centerPane.setPadding(new Insets(10));
	     
		/*
		 * BorderPane root = new BorderPane();
		 * root.setLeft(leftPanel);
		 * root.setRight(rightPanel); root.setCenter(centerPane);
		 * root.setBottom(logArea);
		 */
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.setVgap(10);
        GridPane.setFillWidth(centerPane, false);
        GridPane.setFillHeight(centerPane, false);
        GridPane.setHalignment(centerPane, javafx.geometry.HPos.CENTER);
        GridPane.setValignment(centerPane, javafx.geometry.VPos.CENTER);

        grid.add(leftPanel, 0, 0);
        grid.add(centerPane, 1, 0);
        grid.add(rightPanel, 2, 0);
        grid.add(logArea, 0, 1, 3, 1); // span all 3 columns
		
		Scene scene = new Scene(grid, 700, 650);
        primaryStage.setTitle("Home Blueprint Designer");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	

}
