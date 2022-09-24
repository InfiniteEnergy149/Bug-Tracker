package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class SignInController implements Initializable{
	SQLReadRecord readRecord = new SQLReadRecord();

	Parent root;
	Stage stage;
	Scene scene;
	

	@FXML
	TextField emailEnter;
	@FXML
	TextField passwordEnter;
	@FXML
	Label notice;
	
	int currentUserAccountId;

	public void setCurrentUserID(int id) {
		this.currentUserAccountId = id;
	}

	public int getCurrentUserID() {
		return this.currentUserAccountId;
	}

	IntroController introScene = new IntroController();
	public void initialize(URL arg0, ResourceBundle arg1) {
	try {
		stage = introScene.getStage();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	
	public void submit(ActionEvent event) throws IOException {
		System.out.println("Submit");
		Boolean checkCredentials = false;
		// read accounts from database
		// check if username and password matches an existing account
		try {
			String emailEntered;
			String emailFound;
			String passwordEntered;
			String passwordFound;

			emailEntered = emailEnter.getText();//Error
			passwordEntered = passwordEnter.getText();
			for (int i = 0; i < readRecord.getLastAccountId() + 1; i++) {
				readRecord.readRecordById(0, i);
				emailFound = readRecord.getAccountEmail();
				passwordFound = readRecord.getAccountPassword();
				if (emailEntered.equals(emailFound) && passwordEntered.equals(passwordFound)) {
					checkCredentials = true;
					currentUserAccountId = readRecord.getAccountId();
				}
			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		setCurrentUserID(currentUserAccountId);
		
	
		System.out.println("A" + currentUserAccountId );
		if (checkCredentials == true) {
			//Hide Scene
			//notice.getScene().getWindow().hide();
			
			
			System.out.println("Sumbit");
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileScene.fxml"));
			root = loader.load();
			ProfileController profileScene = loader.getController();
			profileScene.setCurrentUserAccountId(currentUserAccountId);
			//stage =  new Stage();
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		    
		} else {
			notice.setText("Email and Password don't match records");
		}
		// if exist then save account id in variable and sned to summary scene
		// if not then send warning that username and password don't match records
		
	}



	public void intro(ActionEvent event) throws IOException {
		System.out.println("Back");
		Parent root = FXMLLoader.load(getClass().getResource("IntroScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
