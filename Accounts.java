package application;

import java.text.SimpleDateFormat;
import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Accounts {
	SQLReadRecord readRecord = new SQLReadRecord();
    private IntegerProperty accountId = new SimpleIntegerProperty();
	private StringProperty fullName = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	private StringProperty password = new SimpleStringProperty();
	private StringProperty role = new SimpleStringProperty();
	private IntegerProperty projectId = new SimpleIntegerProperty();

	// Employee Data - Full Name,Email,Password, ProjectId, Role
	// status
	public Accounts(Integer accountId ,String fullName, String email, String password, String role, Integer projectId) {
		this.accountId = new SimpleIntegerProperty(accountId);
		this.fullName = new SimpleStringProperty(fullName);
		this.email = new SimpleStringProperty(email);
		this.password = new SimpleStringProperty(password);
		this.role = new SimpleStringProperty(role);
		this.projectId = new SimpleIntegerProperty(projectId);
	}

	// accountId
		public final IntegerProperty accountIdProperty() {
			if (accountId == null) {
				accountId = new SimpleIntegerProperty(this, "accountId");
			}
			return accountId;
		}

		public Integer getAccountId() {
			return accountId.get();
		}

		public void setAccountId(Integer value) {
			accountId.set(value);
		}
	
	// Full Name
	public final StringProperty fullNameProperty() {
		if (fullName == null) {
			fullName = new SimpleStringProperty(this, "fullName");
		}
		return fullName;
	}

	public String getFullName() {
		return fullName.get();
	}

	public void setFullName(String value) {
		fullName.set(value);
	}

	// Email
	public final StringProperty emailProperty() {
		if (email == null) {
			email = new SimpleStringProperty(this, "email");
		}
		return email;
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String value) {
		email.set(value);
	}

	// Password
	public final StringProperty passwordProperty() {
		if (password == null) {
			password = new SimpleStringProperty(this, "password");
		}
		return password;
	}

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String value) {
		password.set(value);
	}

	// Role
	public final StringProperty roleProperty() {
		if (role == null) {
			role = new SimpleStringProperty(this, "role");
		}
		return role;
	}

	public String getRole() {
		return role.get();
	}

	public void setRole(String value) {
		role.set(value);
	}
	
	// ProjectId
		public final IntegerProperty projectIdProperty() {
			if (projectId == null) {
				projectId = new SimpleIntegerProperty(this, "projectId");
			}
			return projectId;
		}

		public Integer getProjectId() {
			return projectId.get();
		}

		public void setProjectId(Integer value) {
			projectId.set(value);
		}
		
		public String getProjectName() {
			String projectName;
			try {
				readRecord.setReadRecordById(1,getProjectId());
				readRecord.getProject().getName();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (readRecord.getProject().getName() == null) {
				projectName = "None Selected";
			}else {
				projectName = readRecord.getProject().getName();
			}
			return projectName;
		}
}
