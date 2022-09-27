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

public class Bugs {
	SQLReadRecord readRecord = new SQLReadRecord();
	private IntegerProperty bugId = new SimpleIntegerProperty(); // bug Id
	private StringProperty bugName = new SimpleStringProperty(); // bug name
	private StringProperty bugDescr = new SimpleStringProperty(); // bug description
	private IntegerProperty projectId = new SimpleIntegerProperty(); // project ID
	private StringProperty dateLog = new SimpleStringProperty(); // Date logged
	private StringProperty dateCompl = new SimpleStringProperty(); // date Completed
	private StringProperty nameLog = new SimpleStringProperty(); // name Issued
	private StringProperty nameWorker = new SimpleStringProperty(); // name Worker
	private BooleanProperty complStatus = new SimpleBooleanProperty(); // Completed
	
	// Bugs Data - Bug ID,Bug Name, Bug Description,Project ID, Date Log, Date
	// Completed, Name Log, Name Worker, Completed status

	public Bugs(Integer bugId, String bugName, String bugDescr, Integer projectId, String dateLog, String dateCompl,
			String nameLog, String nameWorker, Boolean complStatus) {
		this.bugId = new SimpleIntegerProperty(bugId);
		this.bugName = new SimpleStringProperty(bugName);
		this.bugDescr = new SimpleStringProperty(bugDescr);
		this.projectId = new SimpleIntegerProperty(projectId);
		this.dateLog = new SimpleStringProperty(dateLog);
		this.dateCompl = new SimpleStringProperty(dateCompl);
		this.nameLog = new SimpleStringProperty(nameLog);
		this.nameWorker = new SimpleStringProperty(nameWorker);
		this.complStatus = new SimpleBooleanProperty(complStatus);
	}
	
	// Bug Id
		public final IntegerProperty bugIdProperty() {
			if (bugId == null) {
				bugId = new SimpleIntegerProperty(this, "bugId");
			}
			return bugId;
		}

		public Integer getBugId() {
			return bugId.get();
		}

		public void setBugId(Integer value) {
			bugId.set(value);
		}

	// Bug Name
	public final StringProperty bugNameProperty() {
		if (bugName == null) {
			bugName = new SimpleStringProperty(this, "bugName");
		}
		return bugName;
	}

	public String getBugName() {
		return bugName.get();
	}

	public void setBugName(String value) {
		bugName.set(value);
	}

	// Bug Description
	public final StringProperty bugDescrProperty() {
		if (bugDescr == null) {
			bugDescr = new SimpleStringProperty(this, "bugDescr");
		}
		return bugDescr;
	}

	public String getBugDescr() {
		return bugDescr.get();
	}

	public void setBugDescr(String value) {
		bugDescr.set(value);
	}

	// Project ID
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
		
		public String getProjectIdName() {
			try {
				readRecord.setReadRecordById(1, projectId.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return readRecord.getProject().getName();
		}

	// Date Logged
	public final StringProperty dateLogProperty() {
		if (dateLog == null) {
			dateLog = new SimpleStringProperty(this, "dateLog");
		}
		return dateLog;
	}

	public String getDateLog() {
		return dateLog.get();
	}

	public void setDateLog(String value) {
		dateLog.set(value);
	}

	// Date Completed
	public final StringProperty dateComplProperty() {
		if (dateCompl == null) {
			dateCompl = new SimpleStringProperty(this, "dateCompl");
		}
		return dateCompl;
	}

	public String getDateCompl() {
		return dateCompl.get();
	}

	public void setDateCompl(String value) {
		dateCompl.set(value);
	}
	
	/*
	 * public String dateFormat(String date){ SimpleDateFormat sdf=new
	 * SimpleDateFormat("yyyy-MM-dd"); return sdf.format(date); }
	 */

	// Name Logged
		public final StringProperty nameLogProperty() {
			if (nameLog == null) {
				nameLog = new SimpleStringProperty(this, "nameLog");
			}
			return nameLog;
		}

		public String getNameLog() {
			return nameLog.get();
		}

		public void setNameLog(String value) {
			nameLog.set(value);
		}
	
		// Name Worker
		public final StringProperty nameWorkerProperty() {
			if (nameWorker == null) {
				nameWorker = new SimpleStringProperty(this, "nameWorker");
			}
			return nameWorker;
		}

		public String getNameWorker() {
			return nameWorker.get();
		}

		public void setNameWorker(String value) {
			nameWorker.set(value);
		}
		
	// Completed Status
	public final BooleanProperty complStatusProperty() {
		if (complStatus == null) {
			complStatus = new SimpleBooleanProperty(this, "complStatus");
		}
		return complStatus;
	}

	public Boolean getComplStatus() {
		return complStatus.get();
	}

	public String getComplStatusStr() {
		return String.valueOf(complStatus.get());
	}    
	
	public void setComplStatus(Boolean value) {
		complStatus.set(value);
	}
}
