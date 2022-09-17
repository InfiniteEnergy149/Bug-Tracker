package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SQLUpdateRecord {

    private static final String UPDATE_USERS_SQL = "update users set name = ? where id = ?;";

   // public static void main(String[] argv) throws SQLException, ClassNotFoundException {
   //     SQLUpdateRecord updateStatementExample = new SQLUpdateRecord();
   //     updateStatementExample.updateRecord();
   // }

    public void updateRecord() throws SQLException, ClassNotFoundException {
        System.out.println(UPDATE_USERS_SQL);
        // Step 1: Establishing a Connection
        try (Connection connection = SQLConnection.getConnection();
            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USERS_SQL)) {
            preparedStatement.setString(1, "Ram");
            preparedStatement.setInt(2, 1);

            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {

            // print SQL exception information
        	SQLConnection.printSQLException(e);
        }

        // Step 4: try-with-resource statement will auto close the connection.
    }
}