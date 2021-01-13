package core;

import database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for representing a year in the system
 */
public class Year {
    private int id;
    private int userId;
    private int yearNumber;
    private int credits;
    private int percentWeight;

    /**
     * Getter for id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for userId.
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter for yearNumber.
     * @return yearNumber
     */
    public int getYearNumber() {
        return yearNumber;
    }

    /**
     * Setter for yearNumber.
     * @param yearNumber yearNumber to set
     */
    public void setYearNumber(int yearNumber) {
        this.yearNumber = yearNumber;
    }

    /**
     * Getter for credits.
     * @return credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Setter for credits.
     * @param credits credits to set
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Getter for year weight in percentage.
     * @return percentWeight
     */
    public int getPercentWeight() {
        return percentWeight;
    }

    /**
     * Setter for year weight in percent.
     * @param percentWeight percentWeight to set.
     */
    public void setPercentWeight(int percentWeight) {
        this.percentWeight = percentWeight;
    }

    /**
     * Constructor for creating a new Year instance.
     * Used for when user is adding a new Year.
     *
     * @param yearNumber number of the year
     * @param credits how many credits does the year have
     * @param percentWeight how much does it weight toward the final degree mark
     */
    public Year (int userId, int yearNumber, int credits, int percentWeight){
        this.id = 0;    // Year is not reconstructed from db/not already in db
        this.userId = userId;
        this.yearNumber = yearNumber;
        this.credits = credits;
        this.percentWeight = percentWeight;
    }

    /**
     * Constructor for a Year already existing in the database.
     * Used for accessing existing years in the database.
     *
     * @param id id of the year
     * @param userId userId of the user to whom the year belongs to
     * @param yearNumber number of the year
     * @param credits how many credits is the year worth
     * @param percentWeight how much the year weights towards final degree mark
     */
    public Year (int id, int userId, int yearNumber, int credits, int percentWeight){
        this.id = id;
        this.userId = userId;
        this.yearNumber = yearNumber;
        this.credits = credits;
        this.percentWeight = percentWeight;
    }

    /**
     * Method which takes an integer, which is supposed to be the new year number
     * and checks in the database whether that yearNumber already exists.
     *
     * @param newYearNumber number to check for duplicates
     * @return true if newYearNumber is unique, false otherwise
     */
    public static boolean yearNumberAvailable(int newYearNumber){
        boolean yearNumberAvailable = false;

        // Constructs SQL query
        String query = "SELECT * FROM Year WHERE yearNumber = ?";

        // Opens the database, gets the user with the specified id
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            // Prepares the statement
            pStatement = Database.getConnection().prepareStatement(query);
            pStatement.setInt(1, newYearNumber);

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // If there are items in the result set, the username is taken
            yearNumberAvailable = !rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return yearNumberAvailable;
    }

    /**
     * Method which updates year information in the database.
     * Used when an already existing year has its information updated.
     *
     * @return boolean whether the update was successful
     */
    public boolean updateYear (){
        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        int rowsAffected = 0;

        // Sets up the query
        String query = "UPDATE Year SET yearNumber = ?, credits = ?, percentWeight = ? " +
                "WHERE id = ?";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, yearNumber);
            pStatement.setInt(2, credits);
            pStatement.setInt(3, percentWeight);
            pStatement.setInt(4, id);

            // Result of query is true if SQL command worked
            rowsAffected = pStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement and result set
            if (pStatement != null) {
                try {
                    pStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        // Returns whether insertion was successful
        return rowsAffected == 1;
    }
}
