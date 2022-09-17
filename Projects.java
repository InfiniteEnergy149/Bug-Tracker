package application;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Projects {
	// private String name;
	// private String description;
	private StringProperty name = new SimpleStringProperty();
	private StringProperty description = new SimpleStringProperty();
	private IntegerProperty projectId = new SimpleIntegerProperty();

	public Projects(Integer projectId, String name, String description ) {
		this.projectId = new SimpleIntegerProperty(projectId);
		   this.name = new SimpleStringProperty(name);
	        this.description = new SimpleStringProperty(description);
	        
	        
	}
	//PROJECT ID
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
	
     //NAME
	public final StringProperty nameProperty() {
		if (name == null) {
			name = new SimpleStringProperty(this, "name");
		}
		return name;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String value) {
		name.set(value);
	}
	
     //DESRCIPTION
	public final StringProperty descriptionProperty() {
		if (description == null) {
			description = new SimpleStringProperty(this, "description");
		}
		return description;
	}

	public String getDescripton() {
		return description.get();
	}

	public void setDescription(String value) {
		description.set(value);
	}

}

