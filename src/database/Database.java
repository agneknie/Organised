package database;

import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class Database {
    private static String localDir = System.getProperty("user.dir");
    static String currentURL = "jdbc:sqlite:"+localDir+"/src/database/organisationDB.db";
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
     * Closes the connection stored by Database.connection.
     */
    public static void closeConnection() {
        if(connection == null) {
            // If no connection to close, throw error
            throw new RuntimeException("Current connection is null, no database connection to close.");
        }else {
            try {
                connection.close();
                System.out.println("Connection closed from " + currentURL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Getter for Database.connection, only returns if a connection has already been previously opened.
     *
     * @return Connection instance
     */
    public static Connection getConnection() {
        // If no connection is present, throw exception
        if(connection != null) return connection;
        else {
            throw new RuntimeException("Current connection is null. Start with Database.openConnection()");
        }
    }
}
