package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Display {

	int sceneSize = 500;

	// Design of introduction scene
	public Scene introDesign(Stage primaryStage) throws FileNotFoundException {

		BorderPane root = new BorderPane();

		VBox intro = new VBox();
		Label title = new Label("Bug Tracker");
		title.setStyle("-fx-text-fill: white;-fx-font: nor" + "mal bold 20px 'serif'");
		// Call image from files
		FileInputStream input = new FileInputStream("C:/Users/Cdani/eclipse-workspace/BugTracker/src/BT.png");
		Image imageLogo = new Image(input);
		ImageView logo = new ImageView(imageLogo);
		// Sign In Scene
		Button employeeSignIn = new Button("Employee Sign In");
		employeeSignIn.setOnAction(event -> primaryStage.setScene(signIn(primaryStage)));
		// New Bug Entry Scene
		Button bugEntry = new Button("New Bug Entry");
		bugEntry.setOnAction(event -> primaryStage.setScene(signIn(primaryStage)));//signIn then bugEntry
		// New Account Scene
		Button newAccount = new Button("Add new Account");
		newAccount.setOnAction(event -> primaryStage.setScene(newAccount(primaryStage)));
		intro.setSpacing(30);
		intro.getChildren().addAll(title, logo, employeeSignIn, bugEntry, newAccount);
		root.setStyle("-fx-background-color: blue;");

		root.setCenter(intro);
		Scene introScene = new Scene(root, sceneSize, sceneSize);
		introScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return introScene;
	}

	// Design of sign in scene
	public Scene signIn(Stage primaryStage) {
		GridPane gridRoot = new GridPane();
		
		Label title = new Label("Sign In");
		title.setStyle("-fx-text-fill: black;-fx-font: nor" + "mal bold 20px 'serif'");
		
		Label usernameTxt =  new Label("Username: ");
		TextField usernameEnter = new TextField();
		
		Label passwordTxt =  new Label("Password: ");
		TextField passwordEnter = new TextField();
		
		Button submit = new Button("Submit");
		
		
		//Column, Row
		gridRoot.add(title,3,0);
		gridRoot.add(usernameTxt, 2,1);
		gridRoot.add(usernameEnter,3,1);
		gridRoot.add(passwordTxt, 2,2);
		gridRoot.add(passwordEnter,3,2);
		gridRoot.add(submit,3,3);
		
		
		Scene signInScene = new Scene(gridRoot, sceneSize, sceneSize);
		signInScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return signInScene;
	}

	// Design of bug entry scene
	public Scene bugEntry(Stage primaryStage) {
		GridPane gridRoot = new GridPane();
		//VBox vertLayout = new VBox();
		Label title = new Label("Bug Entry");
		title.setStyle("-fx-text-fill: black;-fx-font: nor" + "mal bold 20px 'serif'");
		gridRoot.add(title, 0,3);
		//vertLayout.getChildren().addAll(title);
		//root.setCenter(vertLayout);
		Scene bugEntryScene = new Scene(gridRoot, sceneSize, sceneSize);
		bugEntryScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return bugEntryScene;
	}

	// Design of new account scene
	public Scene newAccount(Stage primaryStage) {
		GridPane gridRoot = new GridPane();
		
		Label title = new Label("New Account");
		title.setStyle("-fx-text-fill: black;-fx-font: nor" + "mal bold 20px 'serif'");
		Label nameTxt = new Label("Enter Name");
		TextField nameEnter = new TextField();
		Label emailTxt = new Label("Enter Email");
		TextField emailEnter = new TextField();
		Label passwordTxt = new Label("Enter Password");
		TextField passwordEnter = new TextField();
		Label passwordCheckTxt = new Label("ReEnter Password");
		TextField passwordCheckEnter = new TextField();
		Label roleTxt = new Label("Enter Job Role");
		TextField roleEnter = new TextField();
		Button submit = new Button("Submit");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event1) {
				//Add data to database
			}
			});
			
		
		gridRoot.add(title, 2, 0);
		gridRoot.add(nameTxt, 1, 2);
		gridRoot.add(nameEnter, 2, 2);
		gridRoot.add(emailTxt, 1, 3);
		gridRoot.add(emailEnter, 2, 3);
		gridRoot.add(passwordTxt, 1, 4);
		gridRoot.add(passwordEnter, 2, 4);
		gridRoot.add(passwordCheckTxt, 1, 5);
		gridRoot.add(passwordCheckEnter, 2, 5);
		gridRoot.add(roleTxt, 1, 6);
		gridRoot.add(roleEnter, 2, 6);
		gridRoot.add(submit,2,7);
		
		Scene newAccountScene = new Scene(gridRoot, sceneSize, sceneSize);
		newAccountScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return newAccountScene;
	}
}
