package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SQLReadRecord {
    private static final String ACCOUNT_QUERY = "select accountid,name,email,password,role from accounts where accountid =?";
    private static final String PROJECT_QUERY = "select id,name,email,country,password from users where id =?";
    private static final String BUG_QUERY = "select id,name,email,country,password from users where id =?";
    private Connection connection;
    private PreparedStatement preparedStatement;
    private static final String LastAccountId_Query = "SELECT TOP 1 accountid FROM accounts ORDER BY accountid DESC;";
    public void readRecord() throws ClassNotFoundException {

        // using try-with-resources to avoid closing resources (boilerplate code)

        // Step 1: Establishing a Connection
       /* try (Connection connection = SQLConnection.getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(ACCOUNT_QUERY);) {
            preparedStatement.setInt(1, 1);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            ResultSet rs = preparedStatement.executeQuery();

            // Step 4: Process the ResultSet object.
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String country = rs.getString("country");
                String password = rs.getString("password");
                System.out.println(id + "," + name + "," + email + "," + country + "," + password);
            }
        } catch (SQLException e) {
        	SQLConnection.printSQLException(e);
        }
        // Step 4: try-with-resource statement will auto close the connection.*/
    }
    
    
    //Used to create new account record
    public int getLastAccountId() throws SQLException, ClassNotFoundException {
    	
    	int lastAccountid = 0;
    	connection = SQLConnection.getConnection();
    	 preparedStatement = connection.prepareStatement(LastAccountId_Query);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
		lastAccountid = resultSet.getInt("accountid");	//ERROR HERE
		System.out.println(lastAccountid);
		}
    	return lastAccountid;
    }
}