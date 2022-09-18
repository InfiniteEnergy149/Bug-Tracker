package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//intro, add new account and sign in scenes
public class DisplayScenes {
	// MainDisplay mainDisplay;

	int sceneSize = 550;
	SQLInsertRecord insertRecord = new SQLInsertRecord();
	SQLReadRecord readRecord = new SQLReadRecord();
	SQLUpdateRecord updateRecord = new SQLUpdateRecord();
	SQLDeleteRecord deleteRecord = new SQLDeleteRecord();
	int currentUserAccountId;
	int clickedProjectId;

	// Design of introduction scene
	public Scene introDesign(Stage primaryStage) throws FileNotFoundException {
		int sceneSize = 500;
		BorderPane root = new BorderPane();

		VBox intro = new VBox();
		Label title = new Label("Bug Tracker");
		title.setStyle("-fx-text-fill: white;-fx-font: nor" + "mal bold 20px 'serif'");
		// Call image from files
		FileInputStream input = new FileInputStream("C:/Users/Cdani/eclipse-workspace/BugTracker/src/BT.png");
		Image imageLogo = new Image(input);
		ImageView logo = new ImageView(imageLogo);
		// Sign In Scene
		Button employeeSignIn = new Button("Employee Sign In");
		employeeSignIn.setOnAction(event -> primaryStage.setScene(signIn(primaryStage)));

		// New Account Scene
		Button newAccount = new Button("Add new Account");
		newAccount.setOnAction(event -> primaryStage.setScene(newAccount(primaryStage)));
		intro.setSpacing(30);
		intro.getChildren().addAll(title, logo, employeeSignIn, newAccount);
		root.setStyle("-fx-background-color: blue;");

		root.setCenter(intro);
		Scene introScene = new Scene(root, sceneSize, sceneSize);
		introScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return introScene;
	}

	public void setCurrentUserID(int id) {
		this.currentUserAccountId = id;
	}
	
	public  int getCurrentUserID() {
		return currentUserAccountId;
	}
	
	// Design of sign in scene
	public Scene signIn(Stage primaryStage) {
		GridPane gridHeader = new GridPane();

		Label title = new Label("Sign In");
		title.setStyle("-fx-text-fill: black;-fx-font: nor" + "mal bold 20px 'serif'");

		Label emailTxt = new Label("Email: ");
		TextField emailEnter = new TextField();

		Label passwordTxt = new Label("Password: ");
		TextField passwordEnter = new TextField();

		Label text = new Label("Please enter details");

		Button submit = new Button("Submit");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event1) {
				Boolean checkCredentials = false;
				// read accounts from database
				// check if username and password matches an existing account
				try {
					String emailEntered;
					String emailFound;
					String passwordEntered;
					String passwordFound;

					for (int i = 0; i < readRecord.getLastAccountId() + 1; i++) {
						readRecord.readRecordById(0, i);
						emailEntered = emailEnter.getText();
						emailFound = readRecord.getAccountEmail();
						passwordEntered = passwordEnter.getText();
						passwordFound = readRecord.getAccountPassword();
						if (emailEntered.equals(emailFound) && passwordEntered.equals(passwordFound)) {
							checkCredentials = true;
							currentUserAccountId = readRecord.getAccountId();
						}
					}

				} catch (Exception e) {

					e.printStackTrace();
				}
				if (checkCredentials == true) {
					primaryStage.setScene(summary(primaryStage));
				} else {
					text.setText("Email and Password don't match records");
				}
				// if exist then save account id in variable and sned to summary scene
				// if not then send warning that username and password don't match records
			}
		});

		// Column, Row
		gridHeader.add(title, 3, 0);
		gridHeader.add(emailTxt, 2, 1);
		gridHeader.add(emailEnter, 3, 1);
		gridHeader.add(passwordTxt, 2, 2);
		gridHeader.add(passwordEnter, 3, 2);
		gridHeader.add(submit, 3, 3);
		gridHeader.add(text, 3, 4);

		Scene signInScene = new Scene(gridHeader, sceneSize, sceneSize);
		signInScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return signInScene;
	}

	// Design of new account scene
	public Scene newAccount(Stage primaryStage) {
		GridPane gridHeader = new GridPane();

		Label title = new Label("New Account");
		title.setStyle("-fx-text-fill: black;-fx-font: nor" + "mal bold 20px 'serif'");
		Label nameTxt = new Label("Enter Name");
		TextField nameEnter = new TextField();
		Label emailTxt = new Label("Enter Email");
		TextField emailEnter = new TextField();
		Label passwordTxt = new Label("Enter Password");
		TextField passwordEnter = new TextField();
		Label passwordCheckTxt = new Label("ReEnter Password");
		TextField passwordCheckEnter = new TextField();
		Label roleTxt = new Label("Enter Job Role");
		TextField roleEnter = new TextField();
		Label text = new Label("Please enter details");
		Button submit = new Button("Submit");
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event1) {
				// Add data to database
				// get account ID + 1 from database
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
					primaryStage.setScene(signIn(primaryStage));
				} else {
					// Tell user if passwords don't match
					text.setText("passwords don't match");
				}

			}
		});

		gridHeader.add(title, 2, 0);
		gridHeader.add(nameTxt, 1, 2);
		gridHeader.add(nameEnter, 2, 2);
		gridHeader.add(emailTxt, 1, 3);
		gridHeader.add(emailEnter, 2, 3);
		gridHeader.add(passwordTxt, 1, 4);
		gridHeader.add(passwordEnter, 2, 4);
		gridHeader.add(passwordCheckTxt, 1, 5);
		gridHeader.add(passwordCheckEnter, 2, 5);
		gridHeader.add(roleTxt, 1, 6);
		gridHeader.add(roleEnter, 2, 6);
		gridHeader.add(submit, 2, 7);
		gridHeader.add(text, 2, 8);

		Scene newAccountScene = new Scene(gridHeader, sceneSize, sceneSize);
		newAccountScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return newAccountScene;
	}

	// Design of summary scene
	public Scene summary(Stage primaryStage) {
		GridPane gridHeader = new GridPane();

		Button summary = new Button("Summary");
		summary.setStyle(" -fx-background-color: yellow;");
		Button profile = new Button("Profile");
		profile.setOnAction(event -> {
			primaryStage.setScene(profile(primaryStage));
		});
		Button accounts = new Button("Accounts");
		accounts.setOnAction(event -> {
			primaryStage.setScene(accounts(primaryStage));
		});
		Button projects = new Button("Project");
		projects.setOnAction(event -> {
			primaryStage.setScene(projects(primaryStage));
		});
		Button bugs = new Button("Bug");
		bugs.setOnAction(event -> {
			primaryStage.setScene(bugs(primaryStage));
		});
		Button logout = new Button("Log out");
		logout.setStyle(" -fx-background-color: cyan;");
		logout.setOnAction(event -> {
			try {
				primaryStage.setScene(introDesign(primaryStage));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});

		Label empty = new Label("                     ");
		gridHeader.add(empty, 0, 0);
		gridHeader.add(summary, 1, 0);
		gridHeader.add(profile, 2, 0);
		gridHeader.add(accounts, 3, 0);
		gridHeader.add(projects, 4, 0);
		gridHeader.add(bugs, 5, 0);
		gridHeader.add(logout, 6, 0);

		Scene summaryScene = new Scene(gridHeader, sceneSize, sceneSize);
		summaryScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return summaryScene;
	}

	public Scene profile(Stage primaryStage) {
		GridPane gridHeader = new GridPane();

		Button summary = new Button("Summary");
		summary.setOnAction(event -> {
			primaryStage.setScene(summary(primaryStage));
		});
		Button profile = new Button("Profile");
		profile.setStyle(" -fx-background-color: yellow;");
		Button accounts = new Button("Accounts");
		accounts.setOnAction(event -> {
			primaryStage.setScene(accounts(primaryStage));
		});
		Button projects = new Button("Project");
		projects.setOnAction(event -> {
			primaryStage.setScene(projects(primaryStage));
		});
		Button bugs = new Button("Bug");
		bugs.setOnAction(event -> {
			primaryStage.setScene(bugs(primaryStage));
		});
		Button logout = new Button("Log out");
		logout.setStyle(" -fx-background-color: cyan;");
		logout.setOnAction(event -> {
			try {
				primaryStage.setScene(introDesign(primaryStage));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});

		Label empty = new Label("                     ");
		gridHeader.add(empty, 0, 0);
		gridHeader.add(summary, 1, 0);
		gridHeader.add(profile, 2, 0);
		gridHeader.add(accounts, 3, 0);
		gridHeader.add(projects, 4, 0);
		gridHeader.add(bugs, 5, 0);
		gridHeader.add(logout, 6, 0);

		VBox showProfile = new VBox();
		GridPane gridShow = new GridPane();
		Label title = new Label("PROFILE");
		Label notice = new Label("TO DO - READ*,EDIT/UPDATE");
		Label showName = new Label("NAME: ");
		TextField Name = new TextField(readRecord.getAccountName());
		Label showEmail = new Label("EMAIL: ");
		TextField Email = new TextField(readRecord.getAccountEmail());
		Label showRole = new Label("ROLE: ");
		TextField Role = new TextField(readRecord.getAccountRole());
		Label showPassword = new Label("PASSWORD: ");
		TextField Password = new TextField(readRecord.getAccountPassword());
		Button updateRecords = new Button("Update Records");

		gridShow.add(showName, 1, 1);
		gridShow.add(Name, 2, 1);
		gridShow.add(showEmail, 1, 2);
		gridShow.add(Email, 2, 2);
		gridShow.add(showRole, 1, 3);
		gridShow.add(Role, 2, 3);
		gridShow.add(showPassword, 1, 4);
		gridShow.add(Password, 2, 4);
		gridShow.add(updateRecords, 1, 5);
		
		
//***************DELETING THE CURRENT ACCOUNT
		//Correct current user account id 
		//setCurrentUserID(getCurrentUserID()-1);
		//logout

		showProfile.getChildren().setAll(gridHeader, title, notice, gridShow);

		Scene profileScene = new Scene(showProfile, sceneSize, sceneSize);
		profileScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return profileScene;
	}

	public Scene accounts(Stage primaryStage) {
		GridPane gridHeader = new GridPane();

		Button summary = new Button("Summary");
		summary.setOnAction(event -> {
			primaryStage.setScene(summary(primaryStage));
		});
		Button profile = new Button("Profile");
		profile.setOnAction(event -> {
			primaryStage.setScene(profile(primaryStage));
		});
		Button accounts = new Button("Accounts");
		accounts.setStyle(" -fx-background-color: yellow;");
		Button projects = new Button("Project");
		projects.setOnAction(event -> {
			primaryStage.setScene(projects(primaryStage));
		});
		Button bugs = new Button("Bug");
		bugs.setOnAction(event -> {
			primaryStage.setScene(bugs(primaryStage));
		});
		Button logout = new Button("Log out");
		logout.setStyle(" -fx-background-color: cyan;");
		logout.setOnAction(event -> {
			try {
				primaryStage.setScene(introDesign(primaryStage));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});
		Label empty = new Label("                     ");

		try {
			readRecord.readRecordById(0, currentUserAccountId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		gridHeader.add(empty, 0, 0);
		gridHeader.add(summary, 1, 0);
		gridHeader.add(profile, 2, 0);
		gridHeader.add(accounts, 3, 0);
		gridHeader.add(projects, 4, 0);
		gridHeader.add(bugs, 5, 0);
		gridHeader.add(logout, 6, 0);

		Scene accountsScene = new Scene(gridHeader, sceneSize, sceneSize);
		accountsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return accountsScene;
	}

	@SuppressWarnings("unchecked")
	public Scene projects(Stage primaryStage) {
		GridPane gridHeader = new GridPane();

		Button summary = new Button("Summary");
		summary.setOnAction(event -> {
			primaryStage.setScene(summary(primaryStage));
		});
		Button profile = new Button("Profile");
		profile.setOnAction(event -> {
			primaryStage.setScene(profile(primaryStage));
		});
		Button accounts = new Button("Accounts");
		accounts.setOnAction(event -> {
			primaryStage.setScene(accounts(primaryStage));
		});
		Button projects = new Button("Project");
		projects.setStyle(" -fx-background-color: yellow;");
		Button bugs = new Button("Bug");
		bugs.setOnAction(event -> {
			primaryStage.setScene(bugs(primaryStage));
		});
		Button logout = new Button("Log out");
		logout.setStyle(" -fx-background-color: cyan;");
		logout.setOnAction(event -> {
			try {
				primaryStage.setScene(introDesign(primaryStage));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});

		TableView<Projects> projectList = new TableView<>();

		ArrayList<Projects> projectItems = new ArrayList<>();

		// Fill arrayList with data from database
		try {
			for (int i = 1; i < readRecord.getLastProjectId() + 1; i++) {
				readRecord.readRecordById(1, i);
				projectItems.add(new Projects(readRecord.getProjectId(), readRecord.getProjectName(),
						readRecord.getProjectDescription()));
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		// Update TableView
		ObservableList<Projects> projectListUpdate = FXCollections.observableList(projectItems);
		projectList.setItems(projectListUpdate);

		TableColumn<Projects, String> colName = new TableColumn<Projects, String>("name");
		colName.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getName()));

		TableColumn<Projects, String> colDescr = new TableColumn<Projects, String>("description");
		colDescr.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getDescripton()));

		projectList.getColumns().setAll(colName, colDescr);

		projectList.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		// ObservableList<Projects> projectListUpdate =
		// FXCollections.observableList(projectItems);
		projectList.setItems(projectListUpdate);

		VBox layout = new VBox();
		Label empty = new Label("                     ");
		gridHeader.add(empty, 0, 0);
		gridHeader.add(summary, 1, 0);
		gridHeader.add(profile, 2, 0);
		gridHeader.add(accounts, 3, 0);
		gridHeader.add(projects, 4, 0);
		gridHeader.add(bugs, 5, 0);
		gridHeader.add(logout, 6, 0);

		Label nameTxt = new Label("Enter Name");
		TextField nameEnter = new TextField();
		Label descriptionTxt = new Label("Enter Description");
		TextField descriptionEnter = new TextField();
		Button addProject = new Button("Add Project");
		Label notice = new Label("To do - ADD*,DELETE,EDIT,LINK");
		addProject.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event1) {
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
		});

		GridPane gridLayout = new GridPane();
		gridLayout.add(nameTxt, 0, 1);
		gridLayout.add(nameEnter, 1, 1);
		gridLayout.add(descriptionTxt, 2, 1);
		gridLayout.add(descriptionEnter, 3, 1);
		gridLayout.add(addProject, 4, 1);
		gridLayout.add(notice, 1, 2);

		
		// When row is clicked twice edit button turns yellow and can edit
		// When row is clicked once edit button turns grey and cant edit
		// When row is clicked and editProject is clicked then
		// Edit Name and Edit Description will update row in tableview and db
		Button editProject = new Button("Edit Project");
		Label editNameTxt = new Label("Edit Name");
		TextField editNameEnter = new TextField();
		Label editDescriptionTxt = new Label("Edit Description");
		TextField editDescriptionEnter = new TextField();
		Label editNoticeOne = new Label("Double click row to select edit");
		Label editNoticeTwo = new Label("Single click row to unselect edit");
		Button deleteProject = new Button("Delete project");
		

		
		projectList.setRowFactory(tv -> {
		    TableRow<Projects> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
		             && event.getClickCount() == 2) {
		        	editProject.setStyle(" -fx-background-color: yellow;");
		        	editProject.setText("Edit Row");
		        	deleteProject.setStyle(" -fx-background-color: yellow;");
		        	deleteProject.setText("Delete");
		        	clickedProjectId = row.getItem().getProjectId();
		        }
		    
		    if (! row.isEmpty() && event.getButton()==MouseButton.PRIMARY 
		             && event.getClickCount() == 1) {
		        	editProject.setStyle(" -fx-background-color: grey;");
		        	editProject.setText("Edit Project");
		        	deleteProject.setStyle(" -fx-background-color: grey;");
		        	deleteProject.setText("Edit Project");
		        }
		    });
		    return row;
		});
		
				editProject.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event1) {
			if (editProject.getText().equals("Edit Row")) {
				//Update row
				Projects newProjectItem = new Projects(clickedProjectId,editNameEnter.getText(),editDescriptionEnter.getText());
			    try {
					updateRecord.updateRecord(newProjectItem);
				} catch (Exception e) {
					e.printStackTrace();
				}
			    projectItems.set(clickedProjectId-1,newProjectItem);
			    projectList.setItems(projectListUpdate);
				projectList.refresh();
			}
			}
		});
		
				
				deleteProject.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event1) {
					if (editProject.getText().equals("Edit Row")) {
						//Update row
						ArrayList<Projects> newProjectItems = new ArrayList<>();
						Projects newProjectItem = new Projects(clickedProjectId,editNameEnter.getText(),editDescriptionEnter.getText());

						//delete record
						try {
							deleteRecord.deleteRecord(newProjectItem);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
						
						//Make projectItems read from updated database
											
						
						try {
							projectItems.remove(0);
							//reset readRecord	
							readRecord.readRecordById(1, 1);
							for (int i = 0; i < readRecord.getLastProjectId() ; i++) {
								readRecord.readRecordById(1, i+1);
								newProjectItems.add(new Projects(readRecord.getProjectId(), readRecord.getProjectName(),
										readRecord.getProjectDescription()));
								projectItems.set(i, newProjectItems.get(i));
							}
						} catch (Exception e) {
							e.printStackTrace();

						}
					    projectList.setItems(projectListUpdate);
						projectList.refresh();
					}
					}
				});

		gridLayout.add(editNameTxt, 0, 3);
		gridLayout.add(editNameEnter, 1, 3);
		gridLayout.add(editDescriptionTxt, 2, 3);
		gridLayout.add(editDescriptionEnter, 3, 3);
		gridLayout.add(editProject, 4, 3);
        gridLayout.add(editNoticeOne, 1, 4);
        gridLayout.add(editNoticeTwo, 3, 4);
        gridLayout.add(deleteProject,1,5);

		// gridHeader.add(projectList, 1, 1);
		// addNewProject.getChildren().setAll(nameTxt, nameEnter, descriptionTxt,
		// descriptionEnter, addProject);
		layout.getChildren().setAll(gridHeader, projectList, gridLayout);
		Scene projectsScene = new Scene(layout, sceneSize, sceneSize);
		projectsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return projectsScene;
	}

	public Scene bugs(Stage primaryStage) {
		GridPane gridHeader = new GridPane();

		Button summary = new Button("Summary");
		summary.setOnAction(event -> {
			primaryStage.setScene(summary(primaryStage));
		});
		Button profile = new Button("Profile");
		profile.setOnAction(event -> {
			primaryStage.setScene(profile(primaryStage));
		});
		Button accounts = new Button("Accounts");
		accounts.setOnAction(event -> {
			primaryStage.setScene(accounts(primaryStage));
		});
		Button projects = new Button("Project");
		projects.setOnAction(event -> {
			primaryStage.setScene(projects(primaryStage));
		});
		Button bugs = new Button("Bug");
		bugs.setStyle(" -fx-background-color: yellow;");
		Button logout = new Button("Log out");
		logout.setStyle(" -fx-background-color: cyan;");
		logout.setOnAction(event -> {
			try {
				primaryStage.setScene(introDesign(primaryStage));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		});

		Label empty = new Label("                     ");
		gridHeader.add(empty, 0, 0);
		gridHeader.add(summary, 1, 0);
		gridHeader.add(profile, 2, 0);
		gridHeader.add(accounts, 3, 0);
		gridHeader.add(projects, 4, 0);
		gridHeader.add(bugs, 5, 0);
		gridHeader.add(logout, 6, 0);

		Scene bugsScene = new Scene(gridHeader, sceneSize, sceneSize);
		bugsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return bugsScene;
	}

	public Scene bugEntry(Stage primaryStage) {
		GridPane gridHeader = new GridPane();

		Label bugEntry = new Label("BUG ENTRY ");
		gridHeader.add(bugEntry, 3, 0);

		Scene bugEntryScene = new Scene(gridHeader, sceneSize, sceneSize);
		bugEntryScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return bugEntryScene;
	}

}
