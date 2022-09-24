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
	
	/* @Override
	    public void initialize(URL url, ResourceBundle rb) {
	        try 
	        {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("SignInScene.fxml"));
	            root2 = loader.load();
	            SignInController dac = loader.getController();
	            stage2 = new Stage();
	        } 
	        catch (IOException ex) 
	        {
	            //Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
	        }
	    } */
	 
	//Switch to sign in scene
	public void toSignIn(ActionEvent event) throws IOException {
		System.out.println("SignIn");
		root = FXMLLoader.load(getClass().getResource("SignInScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		 /*try
	        { 
			  
			    
	            Scene scene2 = new Scene(root2);            
	            stage2.setScene(scene2); 
	            stage2.setTitle("second stage");
	            stage2.show();//AndWait();
	              //Close Old stage
	           staged = (Stage) ((Node) event.getSource()).getScene().getWindow();
	           staged.close();
	           
	        }
	        catch(IllegalArgumentException ex)
	        {
	            stage2.show();
	        }*/
	}
	
	//Switch to sign up scene
	public void toSignUp(ActionEvent event) throws IOException {
		System.out.println("SignUp");
		root = FXMLLoader.load(getClass().getResource("SignUpScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	//Temporary
	public void toSummary(ActionEvent event) throws IOException {
		System.out.println("Summary");
		root = FXMLLoader.load(getClass().getResource("SummaryScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
}

