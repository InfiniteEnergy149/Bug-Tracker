package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AccountController {
	Parent root;
	Stage stage;
	Scene scene;
	FXMLLoader loader;
int currentUserAccountId;
	
	public void setCurrentUserAccountId(int id) {
		this.currentUserAccountId = id;
		initial();
	}

	public void initial() {
		
	}
	//Switch Scenes
	public void toSummary(ActionEvent event) throws IOException {

		loader = new FXMLLoader(getClass().getResource("SummaryScene.fxml"));
		root = loader.load();
		SummaryController summaryScene = loader.getController();
		summaryScene.setCurrentUserAccountId(currentUserAccountId);
        switchScene(root, event);
	}

	public void toProfile(ActionEvent event) throws IOException {
		loader = new FXMLLoader(getClass().getResource("ProfileScene.fxml"));
		root = loader.load();
		ProfileController profileScene = loader.getController();
		profileScene.setCurrentUserAccountId(currentUserAccountId);
		switchScene(root, event);
	}

	public void toProject(ActionEvent event) throws IOException {
		loader = new FXMLLoader(getClass().getResource("ProjectScene.fxml"));
		root = loader.load();
		ProjectController projectScene = loader.getController();
		projectScene.setCurrentUserAccountId(currentUserAccountId);
		switchScene(root, event);
	}

	public void toBug(ActionEvent event) throws IOException {
		loader = new FXMLLoader(getClass().getResource("BugScene.fxml"));
		root = loader.load();
		BugController bugScene = loader.getController();
		bugScene.setCurrentUserAccountId(currentUserAccountId);
		switchScene(root, event);
	}

	
	public void toIntro(ActionEvent event) throws IOException {
		System.out.println("LogOut");
		root = FXMLLoader.load(getClass().getResource("IntroScene.fxml"));
		switchScene(root, event);
	}

	public void switchScene(Parent root, ActionEvent event) {
		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}
