package application;

import java.io.IOException;
import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class IntroController{// implements Initializable{
	Parent root;
	Parent root2;
	Stage stage;
	Stage stage2;
	Scene scene;

	@FXML Label label;
	Stage staged;
	
	public Stage getStage() throws IOException {
		return stage;
	}
	
	 
	//Switch to sign in scene
	public void toSignIn(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("SignInScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	
	//Switch to sign up scene
	public void toSignUp(ActionEvent event) throws IOException {
		root = FXMLLoader.load(getClass().getResource("SignUpScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
}

