package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class which handles Database initialization and connections.
 */
public class Database {
    private final static String URL = "jdbc:sqlite::resource:database/organisedDB.db";
    private static Connection connection = null;

    /**
     * Opens a connection to the SQLite Database
     */
    public static void openConnection() {
        try{
            connection = DriverManager.getConnection(URL);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Connection opened to " + URL);
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
                System.out.println("Connection closed from " + URL);
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
