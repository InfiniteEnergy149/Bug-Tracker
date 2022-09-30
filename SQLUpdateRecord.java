package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUpdateRecord {

	// private static final String UPDATE_USERS_SQL = "update users set name = ?
	// where id = ?;";
	private static final String ACCOUNT_QUERY = "update accounts set fullName = ?, email = ?, password = ?,role = ?,projectId = ? where accountid =?";
	private static final String PROJECT_QUERY = "update projects set name = ?, description = ? where projectid =?";
	private static final String BUG_QUERY = "update bugs set bugName=?, bugDescr=?, projectId=?,dateLog=?,dateCompl=?,nameLog=?,nameWorker=?,complStatus=? where bugid =?";

	// public static void main(String[] argv) throws SQLException,
	// ClassNotFoundException {
	// SQLUpdateRecord updateStatementExample = new SQLUpdateRecord();
	// updateStatementExample.updateRecord();
	// }

	public void updateRecord(Object dataType) throws Exception {

		// Step 1: Establishing a Connection
		try {
			Connection connection = SQLConnection.getConnection();
			PreparedStatement preparedStatement;
			int dataTypeNum;

			if (dataType instanceof Accounts) {
				dataTypeNum = 0;// Accounts
			} else if (dataType instanceof Projects) {
				dataTypeNum = 1;// Projects
			} else if (dataType instanceof Bugs) {
				dataTypeNum = 2;// Bugs
			} else {
				System.out.println("Data type not recognised");
				throw new Exception();
			}

			if (dataTypeNum == 0) { // ACCOUNTS
				// "update accounts set fullName = ?, email = ?, password = ?,role = ?,projectId
				// = ? from accounts where accountid =?";
				Accounts account = (Accounts) dataType;
				preparedStatement = connection.prepareStatement(ACCOUNT_QUERY);
				preparedStatement.setString(1, account.getFullName());
				preparedStatement.setString(2, account.getEmail());
				preparedStatement.setString(3, account.getPassword());
				preparedStatement.setString(4, account.getRole());
				preparedStatement.setInt(5, account.getProjectId());
				preparedStatement.setInt(6, account.getAccountId());

			} else if (dataTypeNum == 1) { // PROJECTS
				// "update projects set name = ?, description = ? from projects where projectid
				// =?";
				Projects project = (Projects) dataType;
				preparedStatement = connection.prepareStatement(PROJECT_QUERY);
				preparedStatement.setString(1, project.getName());
				preparedStatement.setString(2, project.getDescripton());
				preparedStatement.setInt(3, project.getProjectId());

			} else { // BUGS
				// "update bugs set bugName, bugDescr,
				// projectId,dateLog,dateCompl,nameLog,nameWorker,complStatus from bugs where
				// bugid =?";

				Bugs bug = (Bugs) dataType;
				preparedStatement = connection.prepareStatement(BUG_QUERY);
				preparedStatement.setString(1, bug.getBugName());
				preparedStatement.setString(2, bug.getBugDescr());
				preparedStatement.setInt(3, bug.getProjectId());
				preparedStatement.setString(4, bug.getDateLog());
				preparedStatement.setString(5, bug.getDateCompl());
				preparedStatement.setString(6, bug.getNameLog());
				preparedStatement.setString(7, bug.getNameWorker());
				preparedStatement.setBoolean(8, bug.getComplStatus());
				preparedStatement.setInt(9, bug.getBugId());
			}
			// Step 3: Execute the query or update query
			preparedStatement.executeUpdate();
		} catch (SQLException e) {

			// print SQL exception information
			SQLConnection.printSQLException(e);
		}

		
	}
}

