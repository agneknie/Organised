package core;

import database.Database;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        if(credits < 0)
            throw new IllegalArgumentException("Credits can't be less than 0");
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
        if(percentWeight < 0)
            throw new IllegalArgumentException("Percentage worth can't be less than 0");
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
        if(yearNumber < 0)
            throw new IllegalArgumentException("Year can't be less than 0");
        if(credits < 0)
            throw new IllegalArgumentException("Credits can't be less than 0");
        if(percentWeight < 0)
            throw new IllegalArgumentException("Percentage worth can't be less than 0");
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
     * and checks in the database whether that yearNumber already exists for a given user.
     *
     * @param newYearNumber number to check for duplicates
     * @param user user whose years need to be checked
     * @return true if newYearNumber is unique, false otherwise
     */
    public static boolean yearNumberAvailable(int newYearNumber, User user){
        boolean yearNumberAvailable = false;

        // Constructs SQL query
        String query = "SELECT * FROM Year WHERE yearNumber = ? AND userId = ?";

        // Opens the database, gets the user with the specified id
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            // Prepares the statement
            pStatement = Database.getConnection().prepareStatement(query);
            pStatement.setInt(1, newYearNumber);
            pStatement.setInt(2, user.getId());

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // If there are items in the result set, the year number is taken
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

    /**
     * Method which returns the weighted overall grade of the year
     * @return overall weighted year grade
     */
    public double getOverallGrade(){
        List<Module> modules = getAllModules();

        // Variables to save interim calculation results
        double overallAchieved = 0;
        double creditsAvailable = 0;

        // Goes through modules and saves their data
        for(Module module: modules){
            double moduleMark = module.getOverallGrade()*module.getCredits();
            if(moduleMark != 0){
                overallAchieved += moduleMark;
                creditsAvailable += module.getCredits();
            }
        }
        // Calculates the grade and returns it
        return overallAchieved/creditsAvailable;
    }

    // Methods concerning Modules of this year
    /**
     * Method which adds a newly created module to the database.
     * Used when user creates a new module.
     *
     * @param module Module to add
     * @return true if operation was successful, false otherwise
     */
    public boolean addModule(Module module) {
        // Module already exists in the database
        if (module.getId() != 0) return false;

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        int rowsAffected = 0;

        // Sets up the query
        String query = "INSERT INTO Module VALUES(null,?,?,?,?,?,?,?);";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, userId);
            pStatement.setString(2, module.getCode());
            pStatement.setString(3, module.getFullName());
            pStatement.setInt(4, module.getCredits());
            pStatement.setString(5, module.getSemester().toString());
            pStatement.setInt(6, module.getStudyYear());
            pStatement.setString(7, module.getColourAsString());

            // Result of query is true if SQL command worked
            rowsAffected = pStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Closes the prepared statement
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

    /**
     * Method which gets all modules of belonging to the year in the database.
     *
     * @return List od Module objects
     */
    public List<Module> getAllModules(){
        ArrayList<Module> modules = new ArrayList<>();

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        ResultSet rs = null;

        // Sets up the query
        String query = "SELECT * FROM Module WHERE userId = ? AND studyYear = ?;";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, userId);
            pStatement.setInt(2, yearNumber);

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // If there are items in the result set, reconstructs the Modules and them saves in a list
            while (rs.next()) {
                int id = rs.getInt("id");
                String code = rs.getString("code");
                String fullName = rs.getString("fullName");
                int credits = rs.getInt("credits");
                String semester = rs.getString("semester");
                String colour = rs.getString("colour");

                Module currentModule = new Module(id, userId, code, fullName, credits,
                        semester, yearNumber, colour);
                modules.add(currentModule);
            }

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
        // Returns a list of Module objects
        return modules;
    }
}
