package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProjectController {
	SQLReadRecord readRecord = new SQLReadRecord();
	SQLInsertRecord insertRecord = new SQLInsertRecord();
	SQLUpdateRecord updateRecord = new SQLUpdateRecord();
	SQLDeleteRecord deleteRecord = new SQLDeleteRecord();
	
	Parent root;
	Stage stage;
	Scene scene;
	FXMLLoader loader;
	int currentUserAccountId;

	@FXML
	TextField nameEnter;
	@FXML
	TextField descriptionEnter;
	@FXML
	TableView<Projects> projectList;
	@FXML
	TableColumn<Projects, String> tbName;
	@FXML
	TableColumn<Projects, String> tbDescription;
	@FXML
	Label notice;
	@FXML
	Button updateBtn;
	@FXML
	Button deleteBtn;
	
	ArrayList<Projects> projectItems = new ArrayList<>();
	int clickedProjectId;
	ObservableList<Projects> projectListUpdate = FXCollections.observableList(projectItems);

	public void setCurrentUserAccountId(int id) {
		this.currentUserAccountId = id;
		initial();
	}

	@SuppressWarnings("unchecked")
	public void initial() {

		// Fill arrayList with data from database
		try {
			for (int i = 1; i < readRecord.getLastProjectId() + 1; i++) {
				readRecord.setReadRecordById(1, i);
				projectItems.add(readRecord.getProject());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Update TableView
		
		tbName = new TableColumn<Projects, String>("Name");
		tbName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));

		tbDescription = new TableColumn<Projects, String>("Description");
		tbDescription.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDescripton()));

		projectList.getColumns().setAll(tbName, tbDescription);

		projectList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		projectList.setItems(projectListUpdate);

		// When row is clicked twice edit button turns yellow and can edit
		// When row is clicked once edit button turns grey and cant edit
		// When row is clicked and editProject is clicked then
		// Edit Name and Edit Description will update row in tableview and db

		projectList.setRowFactory(tv -> {
			TableRow<Projects> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					updateBtn.setStyle(" -fx-background-color: yellow;");
					updateBtn.setText("Edit Row");
					deleteBtn.setStyle(" -fx-background-color: yellow;");
					deleteBtn.setText("Delete");
					clickedProjectId = row.getItem().getProjectId();
				}

				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
					updateBtn.setStyle("-fx-background-color: lightgrey;");
					updateBtn.setText("Edit Project");
					deleteBtn.setStyle(" -fx-background-color: lightgrey;");
					deleteBtn.setText("Edit Project");
				}
			});
			return row;
		});

	}

	public void addProject(ActionEvent event) {
		if (nameEnter.getText().equals("") || descriptionEnter.getText().equals("")) {
			notice.setText("name and description must be used");
		} else {
			// Add to arraylist projectItems
			Projects newProject;
			try {
				newProject = new Projects(readRecord.getLastProjectId() + 1, nameEnter.getText(),
						descriptionEnter.getText());
				projectItems.add(newProject);
				// Update tableview
				projectList.setItems(projectListUpdate);
				projectList.refresh();
				// add to db using insertRecord
				insertRecord.insertRecord(newProject);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				System.out.println("New project not added");
			}

		}
	}

	public void updateProject(ActionEvent event) {
		if (updateBtn.getText().equals("Edit Row")) {
			// Update row
			Projects newProjectItem = new Projects(clickedProjectId, nameEnter.getText(),
					descriptionEnter.getText());
			try {
				updateRecord.updateRecord(newProjectItem);
			} catch (Exception e) {
				e.printStackTrace();
			}
			projectItems.set(clickedProjectId - 1, newProjectItem);
			projectList.setItems(projectListUpdate);
			projectList.refresh();
		}
	}

	public void deleteProject(ActionEvent event) {
		if (updateBtn.getText().equals("Edit Row")) {
			// Update row
			ArrayList<Projects> newProjectItems = new ArrayList<>();
			Projects newProjectItem = new Projects(clickedProjectId, nameEnter.getText(),
					descriptionEnter.getText());

			// delete record
			try {
				deleteRecord.deleteRecord(newProjectItem);
			} catch (Exception e2) {
				e2.printStackTrace();
			}

			// Make projectItems read from updated database

			try {
				projectItems.remove(0);
				// reset readRecord
				readRecord.setReadRecordById(1, 1);
				for (int i = 0; i < readRecord.getLastProjectId(); i++) {
					readRecord.setReadRecordById(1, i + 1);//ERROR
					newProjectItems.add(readRecord.getProject());
					projectItems.set(i, newProjectItems.get(i));
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
			projectList.setItems(projectListUpdate);
			projectList.refresh();
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

	public void toBug(ActionEvent event) throws IOException {
		loader = new FXMLLoader(getClass().getResource("BugScene.fxml"));
		root = loader.load();
		BugController bugScene = loader.getController();
		bugScene.setCurrentUserAccountId(currentUserAccountId);
		switchScene(root, event);
	}

	public void toProfile(ActionEvent event) throws IOException {
		loader = new FXMLLoader(getClass().getResource("ProfileScene.fxml"));
		root = loader.load();
		ProfileController profileScene = loader.getController();
		profileScene.setCurrentUserAccountId(currentUserAccountId);
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
