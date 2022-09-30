package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class BugController {
	SQLReadRecord readRecord = new SQLReadRecord();
	SQLDeleteRecord deleteRecord = new SQLDeleteRecord();
	SQLUpdateRecord updateRecord = new SQLUpdateRecord();
	Parent root;
	Stage stage;
	Scene scene;
	FXMLLoader loader;

	int currentUserAccountId;
	int clickedBugId;
	boolean delete = false;
	@FXML
	Button deleteBtn;
	@FXML
	Button updateBtn;
	@FXML
	TabPane tabPane;
	@FXML
	Tab tabSummary;
	@FXML
	TableView<Bugs> fullBugList;
	@FXML
	TableColumn<Bugs, String> tbName;
	@FXML
	TableColumn<Bugs, String> tbDescr;
	@FXML
	TableColumn<Bugs, String> tbNameWorker;
	@FXML
	TableColumn<Bugs, String> tbNameLogged;
	@FXML
	TableColumn<Bugs, String> tbDateCompl;
	@FXML
	TableColumn<Bugs, String> tbDateLogged;
	@FXML
	TableColumn<Bugs, String> tbCompl;
	@FXML
	TableColumn<Bugs, String> tbProj;
	@FXML
	TextField searchEnter;
	@FXML
	TextField searchEnter2;
	@FXML
	Tab tabProject;
	@FXML
	Label numOfProjects;
	@FXML
	Label numOfCompl;
	@FXML
	Label numOfUncompl;
	@FXML
	Label numOfBugs;
	@FXML
	ComboBox<String> cbProject;
	@FXML
	TableView<Bugs> projectBugList;
	@FXML
	Tab tabAddBug;
	//update bug
	@FXML
	Tab tabUpdateBug;
	@FXML
	ComboBox<String> thisProjectCB;
	@FXML
	Label updNotice;
    @FXML
    TextField updNameEnter;
    @FXML
    TextField updDescrEnter;
    @FXML
    TextField updNameWorkerEnter;
    @FXML
    TextField updNameLoggedEnter;
    @FXML
    TextField updDateComplEnter;
    @FXML
    TextField updDateLoggedEnter;
    @FXML
    ComboBox<String> updComplStatusEnter;

	public void setCurrentUserAccountId(int id) {
		this.currentUserAccountId = id;
		initial();
	}

	public void initial() {
		try {
			SummaryTabPane();

			// Others are called when selected
			/*
			 * ProjectTabPane(); AddBugTabPane(); UpdateBugTabPane();
			 */

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// Summary Tab
	@SuppressWarnings("unchecked")
	public void SummaryTabPane() throws Exception {
		ArrayList<Bugs> bugItems = new ArrayList<>();
		int complBugs = 0;
		try {
			for (int i = 0; i < readRecord.getLastBugId(); i++) {
				readRecord.setReadRecordById(2, i + 1);
				bugItems.add(readRecord.getBug());
				if (readRecord.getBug().getComplStatus() == true) {
					complBugs += 1;
				}
			}

			// Set data in Labels
			numOfProjects.setText("No of Projects: " + String.valueOf(readRecord.getLastProjectId()));
			numOfCompl.setText("No of Compl: " + String.valueOf(complBugs));
			numOfUncompl.setText("No of Uncompl: " + String.valueOf((readRecord.getLastBugId() - complBugs)));
			numOfBugs.setText("No of Bugs: " + String.valueOf(readRecord.getLastBugId()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		tbName = new TableColumn<Bugs, String>("Name");
		tbName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getBugName()));

		tbDescr = new TableColumn<Bugs, String>("Descr");
		tbDescr.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getBugDescr()));

		tbNameWorker = new TableColumn<Bugs, String>("Name Worker");
		tbNameWorker.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getNameWorker()));

		tbNameLogged = new TableColumn<Bugs, String>("Name Logged");
		tbNameLogged.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getNameLog()));

		tbDateCompl = new TableColumn<Bugs, String>("Date Compl");
		tbDateCompl.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDateCompl()));

		tbDateLogged = new TableColumn<Bugs, String>("Date Logged");
		tbDateLogged.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDateLog()));

		tbCompl = new TableColumn<Bugs, String>("Compl Status");
		tbCompl.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getComplStatusStr()));

		tbProj = new TableColumn<Bugs, String>("Project");
		tbProj.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getProjectIdName()));

		fullBugList.getColumns().setAll(tbName, tbDescr, tbNameWorker, tbNameLogged, tbDateCompl, tbDateLogged, tbCompl,
				tbProj);
		ObservableList<Bugs> bugListUpdate = FXCollections.observableList(bugItems);
		fullBugList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		fullBugList.setItems(bugListUpdate);

		// Search Bar
		ObservableList<Bugs> dataList = FXCollections.observableArrayList(bugItems);
		FilteredList<Bugs> filteredData = new FilteredList<>(dataList, b -> true);
		searchEnter.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(bug -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (bug.getBugName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (bug.getBugDescr().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (bug.getNameWorker().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (bug.getNameLog().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (bug.getDateCompl().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (bug.getDateLog().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (bug.getComplStatusStr().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (bug.getProjectIdName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else {
					return false;
				}
			});

		});

		SortedList<Bugs> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(fullBugList.comparatorProperty());
		fullBugList.setItems(sortedData);

	}

	@FXML
	Label projComplBugs;
	@FXML
	Label projUncomplBugs;
	@FXML
	Label projStartDate;
	@FXML
	Label projLastDate;
	@FXML
	Label projNumOfBugs;
	@FXML
	Label projNumOfWorkers;

	// Project Tab
	@SuppressWarnings("unchecked")
	public void ProjectTabPane() throws Exception {
		delete = false;
		ArrayList<Bugs> bugItems = new ArrayList<>();
		if (cbProject.getValue() == null || cbProject.getValue().equals("")) {
			readRecord.setReadRecordById(1, 1);
			cbProject.setValue(readRecord.getProject().getName());
		}

// Labels
		int complBugs = 0;
		int uncomplBugs = 0;
		String startDate = "";
		String lastDate = "";
		int numOfWorkers = 0;

		for (int i = 0; i < readRecord.getLastBugId(); i++) {
			readRecord.setReadRecordById(2, i + 1);
			if (readRecord.getBug().getProjectIdName().equals(cbProject.getValue())) {
				if (i == 0) {
					startDate = readRecord.getBug().getDateLog();
				}
				if (i == readRecord.getLastBugId() + 1) {
					lastDate = readRecord.getBug().getDateLog();
				}
				if (readRecord.getBug().getComplStatus() == true) {
					complBugs += 1;
				} else {
					uncomplBugs += 1;
				}
			}

		}

		projComplBugs.setText("Compl Bugs: " + complBugs);
		projUncomplBugs.setText("Uncompl Bugs: " + uncomplBugs);
		projStartDate.setText("Start Date: " + startDate);
		projLastDate.setText("Last Date: " + lastDate);
		projNumOfBugs.setText("Num Of Bugs: " + (complBugs + uncomplBugs));
		projNumOfWorkers.setText("Num Of Workers: " + "Not incl yet");
		ArrayList<String> projListCB1 = new ArrayList<>();

		// Get current project Id
		int currentProjectId = 0;
		for (int i = 0; i < readRecord.getLastProjectId(); i++) {
			readRecord.setReadRecordById(1, i + 1);
			projListCB1.add(readRecord.getProject().getName());
			if (cbProject.getValue().equals(readRecord.getProject().getName())) {
				currentProjectId = i + 1;
			}
		}

		ObservableList<String> cbProjectList1 = FXCollections.observableList(projListCB1);
		cbProject.setItems(cbProjectList1);
		// Fill table view with only project bugs
		try {
			for (int i = 0; i < readRecord.getLastBugId(); i++) {
				readRecord.setReadRecordById(2, i + 1);
				if (readRecord.getBug().getProjectId() == currentProjectId)
					bugItems.add(readRecord.getBug());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		tbName = new TableColumn<Bugs, String>("Name");
		tbName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getBugName()));

		tbDescr = new TableColumn<Bugs, String>("Descr");
		tbDescr.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getBugDescr()));

		tbNameWorker = new TableColumn<Bugs, String>("Name Worker");
		tbNameWorker.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getNameWorker()));

		tbNameLogged = new TableColumn<Bugs, String>("Name Logged");
		tbNameLogged.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getNameLog()));

		tbDateCompl = new TableColumn<Bugs, String>("Date Compl");
		tbDateCompl.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDateCompl()));

		tbDateLogged = new TableColumn<Bugs, String>("Date Logged");
		tbDateLogged.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDateLog()));

		tbCompl = new TableColumn<Bugs, String>("Compl Status");
		tbCompl.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getComplStatusStr()));

		projectBugList.getColumns().setAll(tbName, tbDescr, tbNameWorker, tbNameLogged, tbDateCompl, tbDateLogged,
				tbCompl);

		ObservableList<Bugs> bugListUpdate = FXCollections.observableList(bugItems);
		projectBugList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		projectBugList.setItems(bugListUpdate);

		// Find project names
		ArrayList<String> projectNames = new ArrayList<>();
		for (int i = 0; i < readRecord.getLastProjectId(); i++) {
			readRecord.setReadRecordById(1, i + 1);
			projectNames.add(readRecord.getProject().getName());
		}

		// Search Bar
		ObservableList<Bugs> dataList = FXCollections.observableArrayList(bugItems);
		FilteredList<Bugs> filteredData = new FilteredList<>(dataList, b -> true);
		searchEnter2.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(bug -> {

				if (newValue == null || newValue.isEmpty()) {
					return true;
				}

				String lowerCaseFilter = newValue.toLowerCase();

				if (bug.getBugName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (bug.getBugDescr().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (bug.getNameWorker().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (bug.getNameLog().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (bug.getDateCompl().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (bug.getDateLog().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (bug.getComplStatusStr().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else if (bug.getProjectIdName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true;
				} else {
					return false;
				}
			});

		});

		SortedList<Bugs> sortedData = new SortedList<>(filteredData);
		sortedData.comparatorProperty().bind(projectBugList.comparatorProperty());
		projectBugList.setItems(sortedData);

		// Delete Button select Btn
		projectBugList.setRowFactory(tv -> {
			TableRow<Bugs> row = new TableRow<>();
			row.setOnMouseClicked(event -> {
				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
					deleteBtn.setStyle(" -fx-background-color: red;");
					updateBtn.setStyle(" -fx-background-color: yellow;");
					clickedBugId = row.getItem().getBugId();
					delete = true;
				}

				if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
					deleteBtn.setStyle(" -fx-background-color: yellow;");
					updateBtn.setStyle(" -fx-background-color: lightgrey;");
					delete = false;
				}
			});
			return row;
		});

	}

	public void comboAction(ActionEvent event) throws Exception {
		System.out.println("refresh table");
		if (cbProject.getValue() == null || cbProject.getValue() == "") {
			readRecord.setReadRecordById(1, 1);
			cbProject.setValue(readRecord.getProject().getName());
		}
		ArrayList<Bugs> bugItems = new ArrayList<>();
		int currentProjectId = 0;
		for (int i = 0; i < readRecord.getLastProjectId(); i++) {
			readRecord.setReadRecordById(1, i + 1);
			if (cbProject.getValue().equals(readRecord.getProject().getName())) {
				currentProjectId = i + 1;
			}
		}
		try {
			for (int i = 0; i < readRecord.getLastBugId(); i++) {
				readRecord.setReadRecordById(2, i + 1);
				if (readRecord.getBug().getProjectId() == currentProjectId)
					bugItems.add(readRecord.getBug());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ObservableList<Bugs> bugListUpdate = FXCollections.observableList(bugItems);
		projectBugList.setItems(bugListUpdate);

		// Labels
		int complBugs = 0;
		int uncomplBugs = 0;
		String startDate = "";
		String lastDate = "";
		int numOfWorkers = 0;

		for (int i = 0; i < readRecord.getLastBugId(); i++) {
			readRecord.setReadRecordById(2, i + 1);
			if (readRecord.getBug().getProjectIdName().equals(cbProject.getValue())) {
				if (i == 0) {
					startDate = readRecord.getBug().getDateLog();
				}
				if (i == readRecord.getLastBugId() + 1) {
					lastDate = readRecord.getBug().getDateLog();
				}
				if (readRecord.getBug().getComplStatus() == true) {
					complBugs += 1;
				} else {
					uncomplBugs += 1;
				}
			}
		}

		projComplBugs.setText("Compl Bugs: " + complBugs);
		projUncomplBugs.setText("Uncompl Bugs: " + uncomplBugs);
		projStartDate.setText("Start Date: " + startDate);
		projLastDate.setText("Last Date: " + lastDate);
		projNumOfBugs.setText("Num Of Bugs: " + (complBugs + uncomplBugs));
		projNumOfWorkers.setText("Num Of Workers: " + "Not incl yet");

	}

	// Delete Bug
	public void deleteBug(ActionEvent event) throws Exception {
		Bugs newBugItem = new Bugs(clickedBugId, "a", "a", 1, "a", "a", "a", "a", false);

		if (delete == true) {
			// delete record
			try {
				deleteRecord.deleteRecord(newBugItem);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
			//comboAction(event);
		}

	}



	@FXML
	Label bugNumberInProj;

	public void AddBugTabPane() throws Exception, SQLException {
		ArrayList<String> projectListCB = new ArrayList<>();
		// Set Label id and presumed compl status etc to default
		bugNumberInProj.setText("Bug Nuber/Id: " + (readRecord.getLastBugId() + 1));
		complStatusCB.setValue("false");
		// Set project Choice Box

		for (int i = 0; i < readRecord.getLastProjectId(); i++) {
			readRecord.setReadRecordById(1, i + 1);
			projectListCB.add(readRecord.getProject().getName());
		}
		ObservableList<String> projListCB = FXCollections.observableList(projectListCB);
		projChoiceCB.setItems(projListCB);
		if (projChoiceCB.getValue()==null) {
			projChoiceCB.setValue(projectListCB.get(0));
		}

	}

	@FXML
	ComboBox<String> projChoiceCB;
	@FXML
	Label addBugNotice;
	@FXML
	Label bugNumInProject;
	@FXML
	TextField nameEnter;
	@FXML
	TextField descrEnter;
	@FXML
	TextField nameWorkerEnter;
	@FXML
	TextField nameLoggedEnter;
	@FXML
	TextField dateComplEnter;
	@FXML
	TextField dateLoggedEnter;
	@FXML
	ComboBox<String> complStatusCB;
	SQLInsertRecord insertRecord = new SQLInsertRecord();
	int projChoiceCBNum = 0;
	boolean complStatus = false;

	// AddBug Tab
	@FXML
	public void AddBugSubmit(ActionEvent event) {
		// Find project Number
		try {
			for (int i = 0; i < readRecord.getLastProjectId(); i++) {
				readRecord.setReadRecordById(1, i + 1);
				if (projChoiceCB.getValue().equals(readRecord.getProject().getName())) {
					projChoiceCBNum = i + 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Find completion status
		if (complStatusCB.getValue().toLowerCase().equals("true")) {
			complStatus = true;
		} else {
			complStatus = false;
		}
		// ADD BUG
		if (nameEnter.getText().equals("") || descrEnter.getText().equals("") || nameWorkerEnter.getText().equals("")
				|| nameLoggedEnter.getText().equals("") || dateComplEnter.getText().equals("")
				|| dateLoggedEnter.getText().equals("") || cbProject.getValue() == null) {
			addBugNotice.setText("All fields must be filled");
		} else {
			// Add to arraylist projectItems
			Bugs newBug;
			try {
				newBug = new Bugs(readRecord.getLastBugId() + 1, nameEnter.getText(), descrEnter.getText(),
						projChoiceCBNum, dateLoggedEnter.getText(), dateComplEnter.getText(), nameLoggedEnter.getText(),
						nameWorkerEnter.getText(), complStatus);
				// add to db using insertRecord
				insertRecord.insertRecord(newBug);
				/*
				 * ObservableList<Bugs> bugListUpdate = FXCollections.observableList(bugItems);
				 * fullBugList.setItems(bugListUpdate); ObservableList<String> projListCB =
				 * FXCollections.observableList(projectListCB);
				 * projChoiceCB.setItems(projListCB);
				 */
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
				System.out.println("New bug not added");
			}
		}
	}

	// UpdateBug Tab
	public void UpdateBugTabPane() {
	}

	// Add Bug
	@FXML
	public void toAddBugTab(ActionEvent event) {
		// Set project choice box value
		projChoiceCB.setValue(cbProject.getValue());
		// Change Tab
		tabPane.getSelectionModel().select(tabAddBug);

	}

	// Update Bug
	@FXML
	public void toUpdateBugTab(ActionEvent event) throws Exception {
		thisProjectCB.setValue(cbProject.getValue());
		tabPane.getSelectionModel().select(tabUpdateBug);
		
		ArrayList<String> updProjList = new ArrayList<>();
		for (int i = 0; i< readRecord.getLastProjectId();i++) {
			readRecord.setReadRecordById(1, i+1);
			updProjList.add(readRecord.getProject().getName());	
		}
		ObservableList<String> updProjList2 = FXCollections.observableList(updProjList);
		thisProjectCB.setItems(updProjList2);
		
		//fill updateBugTab with selected data
		 readRecord.setReadRecordById(2,clickedBugId);
		   updNotice.setText("Change data you would like to update");
	       updNameEnter.setText(readRecord.getBug().getBugName());
		   updDescrEnter.setText(readRecord.getBug().getBugDescr());
		   updNameWorkerEnter.setText(readRecord.getBug().getNameWorker());
		   updNameLoggedEnter.setText(readRecord.getBug().getNameLog());
		   updDateComplEnter.setText(readRecord.getBug().getDateCompl());
		   updDateLoggedEnter.setText(readRecord.getBug().getDateLog());
		   
		   ArrayList<String> onOff = new ArrayList<>();
		   onOff.add("true");
		   onOff.add("false");
		   ObservableList<String> trueFalse = FXCollections.observableList(onOff);
		   updComplStatusEnter.setItems(trueFalse);
		   updComplStatusEnter.setValue(readRecord.getBug().getComplStatusStr());
		  
	}
	@FXML
	public void updateBug(ActionEvent event) throws Exception {
	
	// Update bug
		int projNameId = 0;
		for (int i = 0; i< readRecord.getLastProjectId();i++) {
			readRecord.setReadRecordById(1, i+1);
			if (thisProjectCB.getValue().equals(readRecord.getProject().getName()))	{
				projNameId = i+1;
			}
		}
		boolean updComplStatusBool = false;
		if (updComplStatusEnter.getValue().toLowerCase()=="true") {
			updComplStatusBool = true;
		}else {
			updComplStatusBool = false;
		}
		

	Bugs newBugItem = new Bugs(clickedBugId, updNameEnter.getText(),updDescrEnter.getText(),projNameId,updDateLoggedEnter.getText(),updDateComplEnter.getText(),updNameLoggedEnter.getText(),updNameWorkerEnter.getText(),updComplStatusBool);
	try {
		updateRecord.updateRecord(newBugItem);
	} catch (Exception e) {
		e.printStackTrace();
	}

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
	public void toAccount(ActionEvent event) throws IOException {
		loader = new FXMLLoader(getClass().getResource("AccountScene.fxml"));
		root = loader.load();
		AccountController accountScene = loader.getController();
		accountScene.setCurrentUserAccountId(currentUserAccountId);
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
	public void toProfile(ActionEvent event) throws IOException {
		loader = new FXMLLoader(getClass().getResource("ProfileScene.fxml"));
		root = loader.load();
		ProfileController profileScene = loader.getController();
		profileScene.setCurrentUserAccountId(currentUserAccountId);
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
