package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLReadRecord {
	private static final String ACCOUNT_QUERY = "select accountId, fullName, email, password,role,projectId from accounts where accountid =?";
	private static final String PROJECT_QUERY = "select projectId, name, description from projects where projectid =?";
	private static final String BUG_QUERY = "select bugId, bugName, bugDescr, projectId,dateLog,dateCompl,nameLog,nameWorker,complStatus from bugs where bugid =?";
	private Connection connection;
	private PreparedStatement preparedStatement;

	// Account
	private int accountId;
	private String accountName;
	private String accountEmail;
	private String accountPassword;
	private String accountRole;
	private int accountProjectId;

	// Project
	private int projectId;
	private String projectName;
	private String projectDescription;

	// Bug
	private int bugId;
	private String bugName;
	private String bugDescr;
	private int bugProjectId;
	private String bugDateLog;
	private String bugDateCompl;
	private String bugNameLog;
	private String bugNameWorker;
	private Boolean bugComplStatus;

	// int datatype = 0-accounts,1-projects,2-bugs
	public void readRecordById(int dataType, int dataTypeid) throws Exception {
	
		try {
			Connection connection = SQLConnection.getConnection();
	
			if (dataType == 0) { //Accounts
				//"accountId, fullName, email, password,role,projectId from accounts where accountid =?";
				PreparedStatement preparedStatement = connection.prepareStatement(ACCOUNT_QUERY);
				preparedStatement.setInt(1, dataTypeid);
				//System.out.println(preparedStatement);
				ResultSet rs = preparedStatement.executeQuery();

				while (rs.next()) {
					 accountId = rs.getInt("accountid");
					 accountName = rs.getString("fullname");
					accountEmail = rs.getString("email");
					accountPassword = rs.getString("password");
					accountRole = rs.getString("role");
					accountProjectId = rs.getInt("projectid");
					System.out.println(accountId + "," + accountName + "," + accountEmail + "," + accountPassword + "," + accountRole  + "," + accountProjectId);
				}
			} else if (dataType == 1) { //Projects
				//"select projectId, name, description from projects where projectid =?";
				PreparedStatement preparedStatement = connection.prepareStatement(PROJECT_QUERY);
				preparedStatement.setInt(1, dataTypeid);
				//System.out.println(preparedStatement);
				ResultSet rs = preparedStatement.executeQuery();

				while (rs.next()) {
					 projectId = rs.getInt("projectid");
					 projectName = rs.getString("name");
					projectDescription = rs.getString("description");
					
					System.out.println(projectId + "," + projectName + "," + projectDescription);
				}
			} else if (dataType == 2){ //Bugs
				//"select bugId, bugName, bugDescr, projectId,dateLog,dateCompl,nameLog,nameWorker,complStatus from bugs where bugid =?";
				PreparedStatement preparedStatement = connection.prepareStatement(BUG_QUERY);
				preparedStatement.setInt(1, dataTypeid);
				//System.out.println(preparedStatement);
				ResultSet rs = preparedStatement.executeQuery();

				while (rs.next()) {
					int bugId = rs.getInt("bugid");
					String bugName = rs.getString("bugName");
					String bugDescr = rs.getString("bugDescr");
					 int bugProjectId = rs.getInt("projectId");
					 String bugDateLog = rs.getString("dateLog");
					 String bugDateCompl = rs.getString("dateCompl");
					 String bugNameLog = rs.getString("nameLog");
					 String bugNameWorker = rs.getString("nameWorker");
					 Boolean bugComplStatus = rs.getBoolean("complStatus");
					System.out.println(bugId + "," + bugName + "," + bugDescr + "," + bugProjectId + "," + bugDateLog  
							+ "," + bugDateCompl + "," + bugNameLog + "," + bugNameWorker + "," + bugComplStatus);
				}
			} else {
				System.out.println("Number should've been between 0 and 2 exclusively");
				throw new Exception();
			}
		} catch (SQLException e) {
			SQLConnection.printSQLException(e);
		}
		// Step 4: try-with-resource statement will auto close the connection.
	}

	// Account getters
	public int getAccountId() {
		return accountId;
	}

	public String getAccountName() {
		return accountName;
	}

	public String getAccountEmail() {
		return accountEmail;
	}

	public String getAccountPassword() {
		return accountPassword;
	}

	public String getAccountRole() {
		return accountRole;
	}

	public int getAccountProjectId() {
		return accountProjectId;
	}

	// Project getters
	public int getProjectId() {
		return projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	// Bug getters
	public int getBugId() {
		return bugId;
	}
	public String getBugName() {
		return bugName;
	}
	public String getBugDescr() {
		return bugDescr;
	}
	public int getBugProjectId() {
		return bugProjectId;
	}
	public String getBugDateLog () {
		return bugDateLog;
	}
	public String getBugDateCompl() {
		return bugDateCompl;
	}
	public String getBugNameLog() {
		return bugNameLog;
	}
	public String getBugNameWorker() {
		return bugNameWorker;
	}
	public Boolean getBugComplStatus() {
		return bugComplStatus;
	}
	
	
	// Used to create new account record by finding the new account id -1
	private static final String LastAccountId_Query = "SELECT TOP 1 accountid FROM accounts ORDER BY accountid DESC;";

	public int getLastAccountId() throws SQLException, ClassNotFoundException {

		int lastAccountid = 0;
		connection = SQLConnection.getConnection();
		preparedStatement = connection.prepareStatement(LastAccountId_Query);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			lastAccountid = resultSet.getInt("accountid"); // ERROR HERE
			//System.out.println(lastAccountid);
		}
		return lastAccountid;
	}
}