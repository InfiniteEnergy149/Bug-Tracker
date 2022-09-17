package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SQLReadRecord {
    private static final String QUERY = "select id,name,email,country,password from users where id =?";

    public static void main(String[] args) {

        // using try-with-resources to avoid closing resources (boilerplate code)

        // Step 1: Establishing a Connection
        try (Connection connection = SQLConnection.getConnection();

            // Step 2:Create a statement using connection object
            PreparedStatement preparedStatement = connection.prepareStatement(QUERY);) {
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
        // Step 4: try-with-resource statement will auto close the connection.
    }
}