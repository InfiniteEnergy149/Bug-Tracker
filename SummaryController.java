package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class SummaryController {
	SQLReadRecord readRecord = new SQLReadRecord();

	Parent root;
	Stage stage;
	Scene scene;
	FXMLLoader loader;

	@FXML
	Label projectNum;
	@FXML
	Label complNum;
	@FXML
	Label uncomplNum;
	@FXML
	Label bugNum;
	//@FXML
	//ComboBox<String> projectNames;
	@FXML
	Label projComplNum;
	@FXML
	Label projUncomplNum;
	@FXML
	Label projBugNum;
	@FXML
	BarChart<?,?> projectBarChart;
	NumberAxis axisBugs;
	CategoryAxis  axisProjects;

	int currentUserAccountId;
	int comboBoxProjectId;
	ArrayList<Bugs> bugItems = new ArrayList<>();
	ObservableList<Bugs> bugListUpdate = FXCollections.observableList(bugItems);

	public void setCurrentUserAccountId(int id) {
		this.currentUserAccountId = id;
		initial();
	}

	@SuppressWarnings("unchecked")
	public void initial() {
		// Get ArrayList of projects

		// Fill top labels
		try {
			int numOfProjects = readRecord.getLastProjectId();
			int numOfComplProj = 0;
			int numOfUncomplProj = 0;

			// Add all bugs to arrayList bugItems
			for (int i = 0; i < readRecord.getLastBugId(); i++) {
				readRecord.setReadRecordById(2, i + 1);
				bugItems.add(readRecord.getBug());
				if (readRecord.getBug().getComplStatus() == true) {
					numOfComplProj += 1;
				} else {
					numOfUncomplProj += 1;
				}
			}

			projectNum.setText(String.valueOf(numOfProjects));
			complNum.setText(String.valueOf(numOfComplProj));
			uncomplNum.setText(String.valueOf(numOfUncomplProj));
			bugNum.setText(String.valueOf((numOfComplProj + numOfUncomplProj)));

			ArrayList<String> comboBoxProjectItems = new ArrayList<>();
			// Fill comboBox data info
			for (int i = 0; i < numOfProjects; i++) {
				readRecord.setReadRecordById(1, i + 1);
				comboBoxProjectItems.add(readRecord.getProject().getName());
			}
			//ObservableList<String> projectNamesList = FXCollections.observableList(comboBoxProjectItems);
			//projectNames.setItems(projectNamesList);
			//readRecord.setReadRecordById(1, 1);
			//projectNames.setValue(readRecord.getProject().getName());

			// Get projectId from comboBox
			//ArrayList<Projects> projectItems = new ArrayList<>();

			//for (int i = 0; i < readRecord.getLastProjectId(); i++) {
			//	readRecord.setReadRecordById(1, i + 1);
			//	projectItems.add(readRecord.getProject());
			//	// find ComboBox project Id
			//	if (projectNames.getValue().equals(readRecord.getProject().getName())) {
			//		comboBoxProjectId = i;
			//	}
			//}

			// Get Data from bugs by project id
			int numOfComplProj2 = 0;
			int numOfUncomplProj2 = 0;

			// Add all bugs to arrayList bugItems
			for (int i = 0; i < readRecord.getLastBugId(); i++) {
				readRecord.setReadRecordById(2, i + 1);
				if (readRecord.getBug().getProjectId() == comboBoxProjectId) {
					if (readRecord.getBug().getComplStatus() == true) {
						numOfComplProj2 += 1;
					} else {
						numOfUncomplProj2 += 1;
					}
				}
			}
		//	projComplNum.setText(String.valueOf(numOfComplProj2));
		//	projUncomplNum.setText(String.valueOf(numOfUncomplProj2));
		//	projBugNum.setText(String.valueOf((numOfComplProj2 + numOfUncomplProj2)));

			// ProjectItems = list of all bugs

		} catch (Exception e) {
			e.printStackTrace();
		}
		
//Bar Chart

	/*	XYChart.Series series1 = new XYChart.Series<>();
		series1.setName("Compl Bugs");
	//	XYChart.Series<String, Number> series2 = new XYChart.Series<>();
	//	series2.setName("Uncompl Bugs");
		//ArrayList<Bugs> bugList = new ArrayList<>();
		
		try {
			int bugCount= 0 ;
			int complStatNum = 0;
			for (int i = 0; i<readRecord.getLastProjectId();i++ ) {
				bugCount = 0;
				complStatNum = 0;
				for (int j = 0;j<readRecord.getLastBugId();j++) {
					readRecord.setReadRecordById(2, j+1);
				if (readRecord.getBug().getProjectId() == i+1) {
					bugCount +=1;
					if (readRecord.getBug().getComplStatus() == true) {
						complStatNum +=1;
					}
				}
				readRecord.setReadRecordById(1, i+1);
				series1.getData().add(new XYChart.Data<>(("\"" +readRecord.getProject().getName()+ "\""),complStatNum));
				series2.getData().add(new XYChart.Data<>(("\"" +readRecord.getProject().getName()+ "\""),(bugCount - complStatNum)));
			 //	}
		//	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		series1.getData().add(new XYChart.Data<>(("project1"),10));
		series1.getData().add(new XYChart.Data<>(("project2"),15));
	//	series2.getData().add(new XYChart.Data<>(("project2"),15));
		projectBarChart.getData().addAll(series1);//,series2);*/ 
	}

	// Switch Scenes
	public void toProfile(ActionEvent event) throws IOException {
		loader = new FXMLLoader(getClass().getResource("ProfileScene.fxml"));
		root = loader.load();
		ProfileController profileScene = loader.getController();
		profileScene.setCurrentUserAccountId(currentUserAccountId);
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
