package core;

import database.Database;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class for representing a module in the system
 */
public class Module {
    private int id;
    private int userId;
    private String code;
    private String fullName;
    private int credits;
    private Semester semester;
    private int studyYear;
    private Color colour;

    /**
     * Getter for module id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for module userId
     * @return userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter for module code
     * @return module code
     */
    public String getCode() {
        return code;
    }

    /**
     * Setter for module code
     * @param code code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter for module full name
     * @return module full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Setter for module full name
     * @param fullName full name to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Getter for module credits
     * @return module credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Setter for module credits
     * @param credits credits to set
     */
    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * Getter for module semester
     * @return module semester
     */
    public Semester getSemester() {
        return semester;
    }

    /**
     * Setter for module semester
     * @param semester semester to set
     */
    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    /**
     * Getter for module study year number
     * @return module study year number
     */
    public int getStudyYear() {
        return studyYear;
    }

    /**
     * Getter for module colour
     * @return module colour
     */
    public Color getColour() {
        return colour;
    }

    /**
     * Setter for module colour
     * @param colour colour to set
     */
    public void setColour(Color colour) {
        this.colour = colour;
    }

    /**
     * Method which turns the Color object into string, which can be saved
     * in the database. Uses web format #xxxxxx.
     *
     * @return colour as a web format colour string
     */
    public String getColourAsString(){
        return "#" + colour.toString().substring(2,8);
    }

    /**
     * Constructor for Module.
     * Used for modules already in the database.
     *
     * @param id id of the module
     * @param userId id of the user the module belongs to
     * @param code module code of the module
     * @param fullName full name of the module
     * @param credits credits the module is worth
     * @param semester semester during which module takes place
     * @param studyYear year during which the module is studied
     * @param colour colour assigned to the module
     */
    public Module(int id, int userId, String code, String fullName, int credits, String semester, int studyYear, String colour) {
        this.id = id;
        this.userId = userId;
        this.code = code;
        this.fullName = fullName;
        this.credits = credits;
        // Semesters in the database are saved as strings
        this.semester = Semester.stringToSemester(semester);
        this.studyYear = studyYear;
        // Colours in the database are saved as strings, in the web format of #xxxxxx
        this.colour = Color.web(colour);
    }

    /**
     * Constructor for Module.
     * Used for when user is adding a new module.
     *
     * @param userId id of the user the module belongs to
     * @param code module code of the module
     * @param fullName full name of the module
     * @param credits credits the module is worth
     * @param semester semester during which module takes place
     * @param studyYear year during which the module is studied
     * @param colour colour assigned to the module
     */
    public Module(int userId, String code, String fullName, int credits, Semester semester, int studyYear, Color colour) {
        this.id = 0;        // Module is not reconstructed from db/not already in db
        this.userId = userId;
        this.code = code;
        this.fullName = fullName;
        this.credits = credits;
        this.semester = semester;
        this.studyYear = studyYear;
        this.colour = colour;
    }

    /**
     * Method which takes a string, which is supposed to be the new module code
     * and checks in the database whether that module code already exists for a given user.
     *
     * @param newModuleCode number to check for duplicates
     * @param user user whose modules need to be checked
     * @return true if newModuleCode is unique, false otherwise
     */
    public static boolean moduleCodeAvailable(String newModuleCode, User user){
        boolean moduleCodeAvailable = false;

        // Constructs SQL query
        String query = "SELECT * FROM Year WHERE moduleCode = ? AND userId = ?";

        // Opens the database, gets the user with the specified id
        PreparedStatement pStatement = null;
        ResultSet rs = null;
        try {
            // Prepares the statement
            pStatement = Database.getConnection().prepareStatement(query);
            pStatement.setString(1, newModuleCode);
            pStatement.setInt(2, user.getId());

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // If there are items in the result set, the module code is taken
            moduleCodeAvailable = !rs.next();

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
        return moduleCodeAvailable;
    }

    /**
     * Method which updates module information in the database.
     * Used when an already existing module has its information updated.
     *
     * @return boolean whether the update was successful
     */
    public boolean updateModule (){
        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        int rowsAffected = 0;

        // Sets up the query
        String query = "UPDATE Module SET code = ?, fullName = ?, credits = ?, " +
                "semester = ?, colour = ? WHERE id = ?";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, code);
            pStatement.setString(2, fullName);
            pStatement.setInt(3, credits);
            pStatement.setString(4, semester.toString());
            pStatement.setString(5, getColourAsString());
            pStatement.setInt(6, id);

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
