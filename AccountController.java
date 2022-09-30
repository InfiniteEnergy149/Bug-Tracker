package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccountController {
	SQLReadRecord readRecord = new SQLReadRecord();
	Parent root;
	Stage stage;
	Scene scene;

	@FXML
	TableView<Accounts> accountList;
	@FXML
	TextField filterSearch;
	@FXML
	TableColumn<Accounts, String> tbName;
	@FXML
	TableColumn<Accounts, String> tbEmail;
	@FXML
	TableColumn<Accounts, String> tbJobRole;
	@FXML
	TableColumn<Accounts, String> tbProject;


	FXMLLoader loader;
	int currentUserAccountId;

	ArrayList<Accounts> accountItems = new ArrayList<>();
	ObservableList<Accounts> accountListUpdate = FXCollections.observableList(accountItems);

	public void setCurrentUserAccountId(int id) {
		this.currentUserAccountId = id;
		initial();
	}

	@SuppressWarnings("unchecked")
	public void initial() {	
		
		
		
		try {
			readRecord.setReadRecordById(0, currentUserAccountId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Fill arrayList with data from database
		try {
			for (int i = 1; i < readRecord.getLastAccountId() + 1; i++) {
				readRecord.setReadRecordById(0, i);
				accountItems.add(readRecord.getAccount());
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		
		
	

		// Update TableView
		accountList.setItems(accountListUpdate);

		// TableColumns
		tbName = new TableColumn<Accounts, String>("Name");
		tbName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getFullName())); 

		tbEmail = new TableColumn<Accounts, String>("Email");
		tbEmail.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getEmail()));

		tbJobRole = new TableColumn<Accounts, String>("Job Role");
		tbJobRole.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getRole()));

		tbProject = new TableColumn<Accounts, String>("Project");
		tbProject.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getProjectName()));
		
		accountList.getColumns().setAll(tbName, tbEmail, tbJobRole, tbProject);
		
		accountList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		accountList.setItems(accountListUpdate);
		
		//Search Bar
		ObservableList<Accounts> dataList = FXCollections.observableArrayList(accountItems);
		FilteredList<Accounts> filteredData = new FilteredList<>(dataList,b-> true);
		filterSearch.textProperty().addListener((observable,oldValue,newValue)->{
		filteredData.setPredicate(account -> {
		
			if (newValue == null || newValue.isEmpty()) {
				return true;
			}
			
			String lowerCaseFilter = newValue.toLowerCase();
			
			if (account.getFullName().toLowerCase().indexOf(lowerCaseFilter) != -1){
			return true;
		} else if (account.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
			return true;
		} else if (account.getRole().toLowerCase().indexOf(lowerCaseFilter) != -1) {
			return true;
		} else if (account.getProjectName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
			return true;	
		} else {
			return false;
		}
		});
		
		});

		SortedList<Accounts> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(accountList.comparatorProperty());
		accountList.setItems(sortedData);
		
		
		
	}


	// Switch Scenes
	@FXML
	public void toSummary(ActionEvent event) throws IOException {

		loader = new FXMLLoader(getClass().getResource("SummaryScene.fxml"));
		root = loader.load();
		SummaryController summaryScene = loader.getController();
		summaryScene.setCurrentUserAccountId(currentUserAccountId);
		switchScene(root, event);
	}
	@FXML
	public void toProfile(ActionEvent event) throws IOException {
		loader = new FXMLLoader(getClass().getResource("ProfileScene.fxml"));
		root = loader.load();
		ProfileController profileScene = loader.getController();
		profileScene.setCurrentUserAccountId(currentUserAccountId);
		switchScene(root, event);
	}
	@FXML
	public void toProject(ActionEvent event) throws IOException {
		loader = new FXMLLoader(getClass().getResource("ProjectScene.fxml"));
		root = loader.load();
		ProjectController projectScene = loader.getController();
		projectScene.setCurrentUserAccountId(currentUserAccountId);
		switchScene(root, event);
	}
	@FXML
	public void toBug(ActionEvent event) throws IOException {
		loader = new FXMLLoader(getClass().getResource("BugScene.fxml"));
		root = loader.load();
		BugController bugScene = loader.getController();
		bugScene.setCurrentUserAccountId(currentUserAccountId);
		switchScene(root, event);
	}
	@FXML
	public void toIntro(ActionEvent event) throws IOException {
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
