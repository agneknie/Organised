package core;

import core.enums.TaskStatus;
import database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class to represent a Period in the system.
 */
public class Period {
    private final int id;
    private final int userId;
    private final int associatedYear;
    private final String name;
    private int minutesLeft;

    /**
     * Getter for the period id.
     *
     * @return id of the period
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for id of the user the period belongs to.
     *
     * @return userId of the period
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter for year number, which is associated with the period.
     *
     * @return year number of the period
     */
    public int getAssociatedYear() {
        return associatedYear;
    }

    /**
     * Getter for period name.
     *
     * @return name of the period.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for minutes left unused.
     *
     * @return minutesLeft unused for Period
     */
    public int getMinutesLeft() {
        return minutesLeft;
    }

    /**
     * Method which goes through the database and returns the id of the period,
     * which is the last added period.
     *
     * @return id of the most recent added period. -1 if something is wrong.
     */
    protected static int getLastId() {
        int lastPeriod = -1;

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;

        // Sets up the query
        String query = "SELECT * FROM Period ORDER BY id DESC LIMIT 1;";
        try {
            // Executes the statement
            pStatement = connection.prepareStatement(query);
            // Gets the number of the most recent period
            lastPeriod = pStatement.executeQuery().getInt("id");

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
        // Returns the id of last period
        return lastPeriod;
    }

    /**
     * Constructor for Period.
     * Used when a Period instance is reconstructed from the database.
     *
     * @param id id of the Period
     * @param userId id of the user the period belongs to
     * @param associatedYear year number associated with the period
     * @param name name of the period
     * @param minutesLeft minutes left unused for the period
     */
    protected Period(int id, int userId, int associatedYear, String name, int minutesLeft) {
        this.id = id;
        this.userId = userId;
        this.associatedYear = associatedYear;
        this.name = name;
        this.minutesLeft = minutesLeft;
    }

    /**
     * Constructor for Period.
     * Used when a Period instance is created by the User.
     *
     * @param userId id of the user
     * @param associatedYear year number associated with the period
     * @param name name of the period
     */
    public Period(int userId, int associatedYear, String name){
        this.id = 0;    // Period id not reconstructed from db/not already in db
        this.userId = userId;
        this.associatedYear = associatedYear;
        this.name = name;
        this.minutesLeft = 0;   // No minutes left yet
    }

    /**
     * Method which takes minutes and a day to add those minutes to
     * and adds them to the day. If some minutes are leftover, adds them
     * to the leftover minutes of the period.
     *
     * @param day day to add the minutes to
     * @param minutesToAdd minutes to add to the week
     */
    public void addMinutes(Day day, int minutesToAdd){
        // Adds leftover minutes of the period
        minutesToAdd += this.minutesLeft;

        // Splits the time into hours an minutes
        int hours = minutesToAdd / 60;
        int minutes = minutesToAdd % 60;

        // Assigns the hours to day and unused minutes to the period
        day.addHour(hours);
        minutesLeft = minutes;

        // Updates both the day and the period in the database
        this.updatePeriod();
        day.updateDay();
    }

    /**
     * Method which adds the Period to the database.
     */
    public void addPeriod(){
        // Checks if period is not in the database
        if(id == 0){
            // Gets Database connection
            Connection connection = Database.getConnection();
            PreparedStatement pStatement = null;

            // Sets up the query
            String query = "INSERT INTO Period VALUES(null,?,?,?,?);";
            try {
                // Fills prepared statement and executes
                pStatement = connection.prepareStatement(query);
                pStatement.setInt(1, userId);
                pStatement.setInt(2, associatedYear);
                pStatement.setString(3, name);
                pStatement.setInt(4, minutesLeft);

                pStatement.executeUpdate();

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
    }

    /**
     * Method which gets all Weeks of the Period existing in the database.
     *
     * @return List of Weeks of the Period
     */
    public List<Week> getAllWeeks(){
        ArrayList<Week> weeks = new ArrayList<>();

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        ResultSet rs = null;

        // Sets up the query
        String query = "SELECT * FROM Week WHERE periodId = ? AND userId = ?;";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, id);
            pStatement.setInt(2, userId);

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // If there are items in the result set, reconstructs the Weeks and saves them in a list
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("userId");
                int weekNumber = rs.getInt("weekNumber");
                String startDate = rs.getString("startDate");

                Week newWeek = new Week(id, userId, this.id, weekNumber, startDate);
                weeks.add(newWeek);
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
        // Returns a list of Week objects
        return weeks;
    }

    /**
     * Method which updates the minutesLeft variable of the period
     * in the database.
     */
    public void updatePeriod(){
        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;

        // Sets up the query
        String query = "UPDATE Period SET minutesLeft = ? WHERE id = ?";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, minutesLeft);
            pStatement.setInt(2, id);

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
     * Method which returns all tasks which belong to the weeks in this period.
     *
     * @return tasks of the period
     */
    private List<Task> getAllTasks(){
        List<Task> allTasks = new ArrayList<>();

        // Goes through all weeks of period and collects all tasks of all weeks
        for(Week week : this.getAllWeeks()){
            allTasks.addAll(week.getAllTasks());
        }

        // Returns all tasks of period
        return allTasks;
    }

    /**
     * Method which returns the number of tasks with the given status in the period.
     * If taskStatus is null, returns number of all tasks in a period.
     *
     * @param taskStatus desired status of the task
     * @return number of tasks with desired status
     */
    public int getTasksByStatus(TaskStatus taskStatus){
        // Lists for saving tasks
        List<Task> allTasks = this.getAllTasks();
        List<Task> desiredTasks = new ArrayList<>();

        // If taskStatus is null, returns the number of all tasks in a period
        if(taskStatus==null) return allTasks.size();

        // Goes through all tasks and checks whether they are of specified type
        for(Task task : allTasks){
            if(task.getStatus() == taskStatus) desiredTasks.add(task);
        }

        // Returns the number of such tasks
        return desiredTasks.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return id == period.id && userId == period.userId && associatedYear == period.associatedYear && name.equals(period.name) && minutesLeft == period.minutesLeft;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, associatedYear, name, minutesLeft);
    }
}
