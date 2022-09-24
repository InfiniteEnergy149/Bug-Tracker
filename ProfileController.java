package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class ProfileController {//implements Initializable {
	SQLReadRecord readRecord = new SQLReadRecord();
	SQLDeleteRecord deleteRecord = new SQLDeleteRecord();
	SQLUpdateRecord updateRecord = new SQLUpdateRecord();
	FXMLLoader loader;
	// private SignInController signInScene;

	Parent root;
	Stage stage;
	Scene scene;

	@FXML
	TextField name;
	@FXML
	TextField email;
	@FXML
	TextField role;
	@FXML
	TextField password;
	@FXML
	ComboBox<String> projectBox;

	ArrayList<String> projListItems;

	int currentUserAccountId;
	
	public void setCurrentUserAccountId(int id) {
		this.currentUserAccountId = id;
		initial();
	}

	public void initial() {
		// public void start() {
		// NOT GETTING CURRENT ID

	//	System.out.println("+=_-" + signInScene.getCurrentUserID());
	//	currentUserAccountId = signInScene.getCurrentUserID();
		System.out.println("B" + currentUserAccountId);
		System.out.println();
		// currentUserAccountId RESETS TO 0 WHEN CONTROLLER IS CALLED AGAIN
		System.out.println("E" + currentUserAccountId);
		try {
			readRecord.readRecordById(0, currentUserAccountId);
		} catch (Exception e1) {
			e1.printStackTrace();
		} //

		name.setText(readRecord.getAccountName());
		email.setText(readRecord.getAccountEmail());
		role.setText(readRecord.getAccountRole());
		password.setText(readRecord.getAccountPassword());

		int currentAccountProjectId = readRecord.getAccountProjectId();
		try {
			readRecord.readRecordById(1, currentAccountProjectId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		String currentAccountProjectName;
		if (readRecord.getProjectName() == null) {
			currentAccountProjectName = "None selected";
		} else {
			currentAccountProjectName = readRecord.getProjectName();
		}
		// Label ProjectName = new Label(currentAccountProjectName);

		// Change Project
		// Label, listView, Change Project
		// arrayList of project names
		projectBox = new ComboBox();

		int defaultIndex = readRecord.getAccountProjectId();
		// Set default value
		projectBox.setValue(currentAccountProjectName); // ERROR
		projectBox.setPrefSize(200, 100);

		projListItems = new ArrayList<>();
		try {
			projListItems.add("None Selected");
			for (int i = 1; i < readRecord.getLastProjectId() + 1; i++) {
				readRecord.readRecordById(1, i);
				projListItems.add(readRecord.getProjectName());
				System.out.println("*");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Update ListView
		System.out.println("()");
		ObservableList<String> listOfProjectsUpdate = FXCollections.observableArrayList(projListItems);
		projectBox.setItems(listOfProjectsUpdate);
		System.out.println(")(");

	}
	
	@FXML
	public void updateAccount(ActionEvent event) {
		// UPDATE PROJECT
		// if selected combo box != current project
		// update account project
		int comboIndex = 0;
		for (int i = 0; i < projListItems.size(); i++) {
			if (projListItems.get(i) == projectBox.getValue()) {
				comboIndex = i;
			}
		}

		// update account project
		// System.out.println("****************" + name.getText());
		Accounts text = new Accounts(readRecord.getAccountId(), name.getText(), email.getText(), password.getText(),
				role.getText(), comboIndex);
		try {
			updateRecord.updateRecord(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// projectId = comboIndex
	}

	@FXML
	public void deleteAccount(ActionEvent event) {
		// ***************DELETING THE CURRENT ACCOUNT
		// Correct current user account id
		// setCurrentUserID(getCurrentUserID()-1);
		Accounts thisAccount = new Accounts(readRecord.getAccountId(), readRecord.getAccountName(),
				readRecord.getAccountEmail(), readRecord.getAccountPassword(), readRecord.getAccountRole(),
				readRecord.getAccountProjectId());
		try {
			deleteRecord.deleteRecord(thisAccount);
			// primaryStage.setScene(introDesign(primaryStage));
		} catch (Exception e) {

			e.printStackTrace();
		}
	}


	// Switch Scenes
	public void toSummary(ActionEvent event) throws IOException {

		loader = new FXMLLoader(getClass().getResource("SummaryScene.fxml"));
		root = loader.load();
		SummaryController summaryScene = loader.getController();
		summaryScene.setCurrentUserAccountId(currentUserAccountId);
        switchScene(root, event);
	}

	public void toAccount(ActionEvent event) throws IOException {
		loader = new FXMLLoader(getClass().getResource("AccountScene.fxml"));
		root = loader.load();
		AccountController accountScene = loader.getController();
		accountScene.setCurrentUserAccountId(currentUserAccountId);
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
