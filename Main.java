package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	// DisplayScenes GUI = new DisplayScenes();
	Scene scene;
	// Creating the Graphic User Interface

	// Store currentUserAccountId
	int currentUserAccountId;

	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("IntroScene.fxml"));
			scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setCurrentUserAccountId(int id) {
		this.currentUserAccountId = id;
		System.out.println("C" + currentUserAccountId);
	}

	public int getCurrentUserAccountId() {
		System.out.println("D" + currentUserAccountId);

		return this.currentUserAccountId;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
