package core;

import database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class to represent an Assignment in the application.
 */
public class Assignment {
    private final int id;
    private final int userId;
    private final String moduleCode;
    private String fullName;
    private double percentWorth;
    private double maxScore;
    private double score;

    /**
     * Getter for assignment id
     * @return id of the assignment
     */
    public int getId() {
        return id;
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
    public double getPercentWorth() {
        return percentWorth;
    }

    /**
     * Setter for assignment weight towards the module.
     * @param percentWorth percentage to set
     */
    public void setPercentWorth(double percentWorth) {
        if (percentWorth > 100 || percentWorth < 0)
            throw new IllegalArgumentException("Percentage has to be between 0% and 100");
        this.percentWorth = (double)Math.round(percentWorth * 10) / 10;
    }

    /**
     * Getter for assignment maximum score
     * @return maximum score of the assignment
     */
    public double getMaxScore() {
        return maxScore;
    }

    /**
     * Setter for the maximum score of the assignment
     * @param maxScore score to set
     */
    public void setMaxScore(double maxScore) {
        if (maxScore == 0)
            throw new IllegalArgumentException("Maximum Score can't be 0.");
        this.maxScore = (double)Math.round(maxScore * 10) / 10;
    }

    /**
     * Getter for assignment score
     * @return score of the assignment
     */
    public double getScore() {
        return score;
    }

    /**
     * Setter for the score of the assignment.
     * @param score score to set
     */
    public void setScore(double score) {
        if (score > maxScore)
            throw new IllegalArgumentException("Score can't be bigger than maximum score");
        this.score = (double)Math.round(score * 10) / 10;
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
    public Assignment(int id, int userId, String moduleCode, String fullName, double percentWorth, double maxScore, double score){
        this.id = id;
        this.userId = userId;
        this.moduleCode = moduleCode;
        this.fullName = fullName;
        this.percentWorth = (double)Math.round(percentWorth * 10) / 10;
        this.maxScore = (double)Math.round(maxScore * 10) / 10;
        this.score = (double)Math.round(score * 10) / 10;
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
    public Assignment(int userId, String moduleCode, String fullName, double percentWorth, double maxScore, double score){
        if (percentWorth > 100 || percentWorth < 0)
            throw new IllegalArgumentException("Percentage has to be between 0% and 100");
        if (score > maxScore)
            throw new IllegalArgumentException("Score can't be bigger than maximum score");
        if (maxScore == 0)
            throw new IllegalArgumentException("Maximum Score can't be 0.");
        this.id = 0;     // Assignment is not reconstructed from db/not already in db
        this.userId = userId;
        this.moduleCode = moduleCode;
        this.fullName = fullName;
        this.percentWorth = (double)Math.round(percentWorth * 10) / 10;
        this.maxScore = (double)Math.round(maxScore * 10) / 10;
        this.score = (double)Math.round(score * 10) / 10;
    }

    /**
     * Method which updates assignment's data in the database.
     * Used when already existing assignment has it's information updated.
     */
    public void updateAssignment(){
        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;

        // Sets up the query
        String query = "UPDATE Assignment SET fullName = ?, percentWorth = ?, " +
                "maxScore = ?, score = ? WHERE id = ?";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setString(1, fullName);
            pStatement.setDouble(2, percentWorth);
            pStatement.setDouble(3, maxScore);
            pStatement.setDouble(4, score);
            pStatement.setInt(5, id);

            pStatement.execute();

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
    }

    /**
     * Method which returns the grade of the assignment which
     * is calculated from score and maxScore.
     *
     * Returns -1 if maxScore & score is not set yet.
     *
     * @return grade of the assignment as percentage or -1
     */
    public double getGrade(){
        // If score is not added yet (assignment acts as a placeholder)
        if(maxScore == -1 || score == -1){
            return -1;
        }
        else return (double)Math.round((score/maxScore*100) * 10) / 10;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment that = (Assignment) o;
        return id == that.id && userId == that.userId && percentWorth == that.percentWorth &&
                maxScore == that.maxScore && score == that.score && moduleCode.equals(that.moduleCode)
                && fullName.equals(that.fullName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, moduleCode, fullName, percentWorth);
    }
}
