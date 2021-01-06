package database;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
    private static String localDir = System.getProperty("user.dir");
    static String currentURL = "jdbc:sqlite:"+localDir+"/src/database/chinook.db";
    private static Connection connection = null;

    /**
     * Opens a connection to the SQLite Database
     */
    public static void openConnection() {
        try{
            SQLiteDataSource ds = new SQLiteDataSource();
            ds.setUrl(currentURL);
            connection = ds.getConnection();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Connection opened to " + currentURL);
    }

    /**
     * Closes the connection stored by Database.connection.<br>
     * Sets Database.currentURL to an empty string if successful.
     */
    public static void closeConnection() {
        if(connection == null) {
            //no connection to close, throw error
            throw new RuntimeException("Current connection is null, no database connection to close.");
        }else {
            try {
                connection.close();
                System.out.println("Connection closed from " + currentURL);
                currentURL = "";
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Getter for Database.connection, only returns if a connection has already been previously opened.
     * @return Connection instance
     */
    public static Connection getConnection() {

        if(connection != null) return connection;
        else {
            throw new RuntimeException("Current connection is null. Start with Database.openConnection()");
        }
    }

    public static void main(String[] args) {
        openConnection();

        //get the database connection
        Connection connection = Database.getConnection();

        String query = "SELECT * FROM invoices WHERE BillingCity = 'London';";
        PreparedStatement pStatement = null;
        int counter =0;
        try {
            // Prepares the statement
            pStatement = connection.prepareStatement(query);
            //Executes the statement, gets the result set
            ResultSet rs = pStatement.executeQuery();
            while(rs.next()) {
                System.out.println(rs.getString("BillingCountry"));
                counter++;
            }
            System.out.println(counter);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeConnection();
    }
}
