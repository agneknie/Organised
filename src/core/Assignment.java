package core;

import database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class to represent an Assignment in the application.
 */
public class Assignment {
    private int id;
    private int userId;
    private String moduleCode;
    private String fullName;
    private int percentWorth;
    private int maxScore;
    private int score;

    /**
     * Getter for assignment id
     * @return id of the assignment
     */
    public int getId() {
        return id;
    }
    /**
     * Getter for assignment user id
     * @return user id of the assignment
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter for assignment module code
     * @return module code of the assignment
     */
    public String getModuleCode() {
        return moduleCode;
    }

    /**
     * Getter for assignment name
     * @return full name of the assignment
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Setter for assignment name
     * @param fullName name to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * Getter for percentage which the assignment contributes to
     * module overall mark.
     * @return weight as % of the assignment
     */
    public int getPercentWorth() {
        return percentWorth;
    }

    /**
     * Setter for assignment weight towards the module.
     * @param percentWorth percentage to set
     */
    public void setPercentWorth(int percentWorth) {
        if (percentWorth > 100 || percentWorth < 0)
            throw new IllegalArgumentException("Percentage has to be between 0% and 100");
        this.percentWorth = percentWorth;
    }

    /**
     * Getter for assignment maximum score
     * @return maximum score of the assignment
     */
    public int getMaxScore() {
        return maxScore;
    }

    /**
     * Setter for the maximum score of the assignment
     * @param maxScore score to set
     */
    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * Getter for assignment score
     * @return score of the assignment
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter for the score of the assignment.
     * @param score score to set
     */
    public void setScore(int score) {
        if (score > maxScore)
            throw new IllegalArgumentException("Score can't be bigger than maximum score");
        this.score = score;
    }

    /**
     * Constructor for Assignment.
     * Used for assignments already in the database.
     *
     * @param id id of the assignment
     * @param userId userId of the assignment
     * @param moduleCode module code of the assignment
     * @param fullName full name of the assignment
     * @param percentWorth how much assignment contributes to module mark
     * @param maxScore maximum available score for the assignment
     * @param score score achieved in the assignment
     */
    public Assignment(int id, int userId, String moduleCode, String fullName, int percentWorth, int maxScore, int score){
        this.id = id;
        this.userId = userId;
        this.moduleCode = moduleCode;
        this.fullName = fullName;
        this.percentWorth = percentWorth;
        this.maxScore = maxScore;
        this.score = score;
    }

    /**
     * Constructor for Assignment.
     * Used for user newly added assignments.
     *
     * @param userId userId of the assignment
     * @param moduleCode module code of the assignment
     * @param fullName full name of the assignment
     * @param percentWorth how much assignment contributes to module mark
     * @param maxScore maximum available score for the assignment
     * @param score score achieved in the assignment
     */
    public Assignment(int userId, String moduleCode, String fullName, int percentWorth, int maxScore, int score){
        if (percentWorth > 100 || percentWorth < 0)
            throw new IllegalArgumentException("Percentage has to be between 0% and 100");
        if (score > maxScore)
            throw new IllegalArgumentException("Score can't be bigger than maximum score");
        this.id = 0;     // Assignment is not reconstructed from db/not already in db
        this.userId = userId;
        this.moduleCode = moduleCode;
        this.fullName = fullName;
        this.percentWorth = percentWorth;
        this.maxScore = maxScore;
        this.score = score;
    }

    /**
     * Method which updates assignment's data in the database.
     * Used when already existing assignment has it's information updated.
     *
     * @return boolean whether the update was successful
     */
    public boolean updateAssignment(){
        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        int rowsAffected = 0;

        // Sets up the query
        String query = "UPDATE Assignment SET fullName = ?, percentWorth = ?, " +
                "maxScore = ?, score = ? WHERE id = ?";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, fullName);
            pStatement.setInt(2, percentWorth);
            pStatement.setInt(3, maxScore);
            pStatement.setInt(4, score);
            pStatement.setInt(5, id);

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
