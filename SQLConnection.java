package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLConnection {
//change to local file
	//
    private static String jdbcURL = "jdbc:hsqldb:file:C:/Users/Cdani/eclipse-workspace/BugTracker/src/HSQLDB/DB;hsqldb.lock_file=false";
    private static String jdbcUsername = "SA";
    private static String jdbcPassword = "";
    
    public static Connection getConnection() throws ClassNotFoundException {
        Connection connection = null;
        try {
        	Class.forName("org.hsqldb.jdbc.JDBCDriver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
          
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return connection;
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
}