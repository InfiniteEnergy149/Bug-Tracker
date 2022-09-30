package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLDeleteRecord {
	private static final String ACCOUNT_QUERY = "delete from accounts where accountid = ?";
	private static final String PROJECT_QUERY = "delete from projects where projectid = ?";
	private static final String BUG_QUERY = "delete from bugs where bugid = ?";
	SQLUpdateRecord updateRecord = new SQLUpdateRecord();
	SQLReadRecord readRecord = new SQLReadRecord();

	public void deleteRecord(Object dataType) throws Exception {

		// System.out.println(deleteTableSQL);
		// Step 1: Establishing a Connection
		try {
			Connection connection = SQLConnection.getConnection();
			PreparedStatement preparedStatement;

			if (dataType instanceof Accounts) {// Accounts
				Accounts account = (Accounts) dataType;
				preparedStatement = connection.prepareStatement(ACCOUNT_QUERY);
				preparedStatement.setInt(1, account.getAccountId());

			} else if (dataType instanceof Projects) {// Projects
				Projects project = (Projects) dataType;
				preparedStatement = connection.prepareStatement(PROJECT_QUERY);
				preparedStatement.setInt(1, project.getProjectId());

			} else if (dataType instanceof Bugs) {// Bugs
				Bugs bug = (Bugs) dataType;
				preparedStatement = connection.prepareStatement(BUG_QUERY);
				preparedStatement.setInt(1, bug.getBugId());

			} else {
				System.out.println("Data type not recognised");
				throw new Exception();
			}
			preparedStatement.execute();
			// accounts-0,projects-1,bugs-2
			resetAllIds(dataType);
		} catch (SQLException e) {
			// print SQL exception information
			SQLConnection.printSQLException(e);
		}
	}

	// Reset all id so that there is no gap is id increments

	public void resetAllIds(Object dataType) throws Exception {
		// where id > dataTypeId , id = id - 1
		// Reset all id so that there is no gap is id increments
		Accounts newAccount;
		Projects newProject;
		Bugs newBug;
		if (dataType instanceof Accounts) {// Accounts
			//"update accounts set fullName = ?, email = ?, password = ?,role = ?,projectId = ? where accountid =?";
			Accounts account = (Accounts) dataType;
			for (int i = account.getAccountId() + 1; i < readRecord.getLastAccountId() + 1; i++) {
				readRecord.setReadRecordById(0, i);
				newAccount = new Accounts(i - 1, readRecord.getAccount().getFullName(), readRecord.getAccount().getEmail(),
						readRecord.getAccount().getPassword(), readRecord.getAccount().getRole(), readRecord.getAccount().getProjectId());
				updateRecord.updateRecord(newAccount);
			}
			
			
		} else if (dataType instanceof Projects) {// Projects
			//"update projects set name = ?, description = ? where projectid =?";

			Projects project = (Projects) dataType;
			for (int i = project.getProjectId() + 1; i < readRecord.getLastProjectId() + 1; i++) {
				readRecord.setReadRecordById(1, i);
				newProject = new Projects(i - 1, readRecord.getProject().getName(), readRecord.getProject().getDescripton());
				updateRecord.updateRecord(newProject);
			}

		} else {// Bugs
			//"update bugs set bugName, bugDescr, projectId,dateLog,dateCompl,nameLog,nameWorker,complStatus where bugid =?";

			Bugs bug = (Bugs) dataType;
			for (int i = bug.getBugId() + 1; i < readRecord.getLastBugId() + 1; i++) {
				readRecord.setReadRecordById(2, i);
				newBug = new Bugs(i - 1, readRecord.getBug().getBugName(), readRecord.getBug().getBugDescr(),
						readRecord.getBug().getProjectId(), readRecord.getBug().getDateLog(), readRecord.getBug().getDateCompl()
						,readRecord.getBug().getNameLog(), readRecord.getBug().getNameWorker(), readRecord.getBug().getComplStatus());
				updateRecord.updateRecord(newBug);
			}
		}
		
	
		
	}
}