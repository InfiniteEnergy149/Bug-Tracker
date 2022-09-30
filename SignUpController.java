package application;

import java.io.IOException;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignUpController {
	SQLInsertRecord insertRecord = new SQLInsertRecord();
	SQLReadRecord readRecord = new SQLReadRecord();
	
	Parent root;
	Stage stage;
	Scene scene;
	
	@FXML
	TextField nameEnter;
	@FXML
	TextField emailEnter;
	@FXML
	TextField passwordEnter;
	@FXML
	TextField passwordCheckEnter;
	@FXML
	TextField roleEnter;
	@FXML
	Label notice;
	
	public void submit(ActionEvent event) throws IOException {
		
				int accountId;
				// CHECK THAT DATA/ACCOUNTS ARENT DUPLICATED
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
					// change scene
					//Switch to Scene
						Parent root = FXMLLoader.load(getClass().getResource("SignInScene.fxml"));
						stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
						scene = new Scene(root);
						stage.setScene(scene);
						stage.show();
				} else {
					// Tell user if passwords don't match
					notice.setText("passwords don't match");
				}
	}
	
	public void intro(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("IntroScene.fxml"));
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
