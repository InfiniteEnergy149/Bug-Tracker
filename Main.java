package application;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application {
	
	DisplayScenes GUI = new DisplayScenes();
	
	//Creating the Graphic User Interface
	public void start(Stage primaryStage) {
		try {
			primaryStage.setScene(GUI.introDesign(primaryStage));
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		launch(args);
	}
}
