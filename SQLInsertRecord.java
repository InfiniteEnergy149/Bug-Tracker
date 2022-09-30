package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLInsertRecord {

	// insert employee
	private static final String INSERT_ACCOUNTS_SQL = "INSERT INTO accounts"
			+ "  (accountId, fullName, email, password,role,projectId) VALUES " + " (?, ?, ?, ?, ?,?);";

	// insert project
	private static final String INSERT_PROJECTS_SQL = "INSERT INTO projects"
			+ "  (projectId, name, description) VALUES " + " (?, ?, ?);";

	// insert bug
	private static final String INSERT_BUGS_SQL = "INSERT INTO bugs" 
	+ "  (bugId, bugName, bugDescr, projectId,dateLog,dateCompl,nameLog,nameWorker,complStatus) VALUES " + " (?, ?, ?, ?, ?, ?, ?, ?, ?);";

	private Connection connection;
	private PreparedStatement preparedStatement;

	// DataType = <Accounts>,<Projects> or <Bugs>
	//Object dataType contains the data to be added
	public void insertRecord(Object dataType) throws SQLException, ClassNotFoundException {
		int tableNum;
		// tableNum refers to which table is chosen(0-accounts,1-projects or 2-bugs)
		if (dataType instanceof Accounts) {
			tableNum = 0;
		}else if (dataType instanceof Projects){
			tableNum = 1;
		}else if (dataType instanceof Bugs){
			tableNum = 2;
		} else {
			System.out.println("Wrong dataType");
			throw new Error(); 
		}
		SQLInsertRecord table = new SQLInsertRecord();
		table.insertRecordData(tableNum, dataType);
	}
	

	
	public void insertRecordData(int tableNum, Object dataType) throws SQLException, ClassNotFoundException {
		//System.out.println(INSERT_ACCOUNTS_SQL);
		// Step 1: Establishing a Connection
		// Step 2:Create a statement using connection object
		try {

			connection = SQLConnection.getConnection();
			if (tableNum == 0) { // Accounts
				preparedStatement = connection.prepareStatement(INSERT_ACCOUNTS_SQL);
				 Accounts account = (Accounts) dataType; 
				 //get last id +1
				preparedStatement.setInt(1, account.getAccountId()); //ID
				preparedStatement.setString(2,  account.getFullName());//NAME
				preparedStatement.setString(3,  account.getEmail()  );//EMAIL
				preparedStatement.setString(4,  account.getPassword() );//PASSWORD
				preparedStatement.setString(5,  account.getRole() );//ROLE
				preparedStatement.setInt(6, account.getProjectId() ); //PROJECT ID
			} else if (tableNum == 1) { // Projects
				 Projects project = (Projects) dataType; 
				preparedStatement = connection.prepareStatement(INSERT_PROJECTS_SQL);
				preparedStatement.setInt(1, project.getProjectId()); //ID
				preparedStatement.setString(2,  project.getName() );//NAME
				preparedStatement.setString(3,  project.getDescripton() );//DESCRIPTION	
			} else { // Bugs
				 Bugs bug = (Bugs) dataType; 
				preparedStatement = connection.prepareStatement(INSERT_BUGS_SQL);
				preparedStatement.setInt(1, bug.getBugId()); //ID
				preparedStatement.setString(2,  bug.getBugName() );//NAME
				preparedStatement.setString(3,  bug.getBugDescr() );//DESCRIPTION
				preparedStatement.setInt(4, bug.getProjectId());//PROJECTID
				preparedStatement.setString(5,  bug.getDateLog()  );//DATEISSUED
				preparedStatement.setString(6,  bug.getDateCompl() );//DATECOMPLETED
				preparedStatement.setString(7,  bug.getNameLog() );//NAMEISSUED
				preparedStatement.setString(8,  bug.getNameWorker() );//NAMEWORKER
				preparedStatement.setBoolean(9, bug.getComplStatus());//COMPLETED
			}
			
			
			// Step 3: Execute the query or update query
			preparedStatement.executeUpdate();
			connection.close();

		} catch (SQLException e) {

			// print SQL exception information
			SQLConnection.printSQLException(e);
		}


	}
}