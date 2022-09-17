package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;

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
	SQLInsertRecord insertRecord = new SQLInsertRecord();
	SQLReadRecord readRecord = new SQLReadRecord();
int currentUserAccountId;
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
		
		// New Account Scene
		Button newAccount = new Button("Add new Account");
		newAccount.setOnAction(event -> primaryStage.setScene(newAccount(primaryStage)));
		intro.setSpacing(30);
		intro.getChildren().addAll(title, logo, employeeSignIn, newAccount);
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

		Label emailTxt = new Label("Email: ");
		TextField emailEnter = new TextField();

		Label passwordTxt = new Label("Password: ");
		TextField passwordEnter = new TextField();
		
		Label text = new Label("Please enter details");

		Button submit = new Button("Submit");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event1) {
				Boolean checkCredentials = false;
				//read accounts from database 
				    //check if username and password matches an existing account
				try {
					String emailEntered;
					String emailFound;
					String passwordEntered;
					String passwordFound;
					
					for (int i=0;i<readRecord.getLastAccountId()+1;i++) {
						readRecord.readRecordById(0,i);
						emailEntered = emailEnter.getText();
						emailFound = readRecord.getAccountEmail();
						passwordEntered = passwordEnter.getText();
						passwordFound = readRecord.getAccountPassword();
						if (emailEntered.equals(emailFound) && passwordEntered.equals(passwordFound)){
							checkCredentials = true;
							currentUserAccountId = readRecord.getAccountId();
						}
					}
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				 if (checkCredentials == true) {
					 primaryStage.setScene(summary(primaryStage));
					// Send user to summary Pane (currentUserAccountId)
				 }else {
					 text.setText("Email and Password don't match records");
				 }
				//if exist then save account id in variable and sned to summary scene
				//if not then send warning that username and password don't match records
			}
		});

		// Column, Row
		gridRoot.add(title, 3, 0);
		gridRoot.add(emailTxt, 2, 1);
		gridRoot.add(emailEnter, 3, 1);
		gridRoot.add(passwordTxt, 2, 2);
		gridRoot.add(passwordEnter, 3, 2);
		gridRoot.add(submit, 3, 3);
		gridRoot.add(text,3,4);

		
		
		Scene signInScene = new Scene(gridRoot, sceneSize, sceneSize);
		signInScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return signInScene;
	}

	// Design of bug entry scene
	public Scene summary(Stage primaryStage) {
		GridPane gridRoot = new GridPane();
		// VBox vertLayout = new VBox();
		Label title = new Label("Summary");
		title.setStyle("-fx-text-fill: black;-fx-font: nor" + "mal bold 20px 'serif'");
		gridRoot.add(title, 0, 3);
		// vertLayout.getChildren().addAll(title);
		// root.setCenter(vertLayout);
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
		Label text = new Label("Please enter details");
		Button submit = new Button("Submit");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event1) {
				// Add data to database
				// get account ID + 1 from database
				int accountId;
				
				//CHECK THAT DATA/ACCOUNTS ARENT DUPLICATED
				
				// Checks to make sure both passwords are the same
				if (passwordEnter.getText().contentEquals(passwordCheckEnter.getText())) {
					try {
						accountId = readRecord.getLastAccountId() + 1;
						Accounts account = new Accounts(accountId, nameEnter.getText(), emailEnter.getText(),
								passwordEnter.getText(), roleEnter.getText(), 0);
						accountId = 0;
						insertRecord.insertRecord(account);
					} catch (SQLException | ClassNotFoundException e) {
						
						e.printStackTrace();
					}
					//change scene
					primaryStage.setScene(signIn(primaryStage));
				} else {
					//Tell user if passwords don't match
                    text.setText("passwords don't match");
				}

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
		gridRoot.add(submit, 2, 7);
		gridRoot.add(text, 2, 8);

		Scene newAccountScene = new Scene(gridRoot, sceneSize, sceneSize);
		newAccountScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return newAccountScene;
	}
}
