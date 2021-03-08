package core;

import core.enums.TaskStatus;
import database.Database;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
 * Class to represent a Task in the system.
 */
public class Task {
    private final int id;
    private final int userId;
    private int moduleId;
    private final int weekId;
    private String description;
    private TaskStatus status;

    /**
     * Getter for task id.
     * @return id of task
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for id of the user to whom the task belongs to.
     * @return userId of task
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter for description of the task.
     * @return description of task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for completion status of the task.
     * @return status of task
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Setter for moduleId variable.
     * @param moduleId to set
     */
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * Setter for description variable.
     * @param description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter for status variable.
     * @param status to set
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    /**
     * Constructor for a Task.
     * Used when a task is reconstructed from the database.
     *
     * @param id id of the task
     * @param userId id of the user the task belongs to
     * @param moduleId id of the module the task is associated with
     * @param weekId id of the week the task is associated with
     * @param description description of the task
     * @param status completion status of the task
     */
    public Task(int id, int userId, int moduleId, int weekId, String description, TaskStatus status) {
        this.id = id;
        this.userId = userId;
        this.moduleId = moduleId;
        this.weekId = weekId;
        this.description = description;
        this.status = status;
    }

    /**
     * Constructor for a Task.
     * Used when a new task is created by the user.
     *
     * @param userId id of the user the task belongs to
     * @param moduleId id of the module the task is associated with
     * @param weekId id of the week the task is associated with
     * @param description description of the task
     */
    public Task(int userId, int moduleId, int weekId, String description) {
        this.id = 0;    // Task is not reconstructed from db/not already in db
        this.userId = userId;
        this.moduleId = moduleId;
        this.weekId = weekId;
        this.description = description;
        this.status = TaskStatus.NO;    // Task is not completed by default
    }

    /**
     * Returns the colour in which the task should be coloured.
     * Corresponds to the colour of the module.
     *
     * @return colour of task as a string
     */
    public String getTaskColourString(){
        return this.getModule().getColourAsString();
    }

    /**
     * Returns the colour in which the task should be coloured.
     * Corresponds to the colour of the module.
     *
     * @return colour as colour object of task
     */
    public Color getTaskColour(){
        return this.getModule().getColour();
    }

    /**
     * Method which returns the module associated with the task.
     *
     * @return module of the task
     */
    public Module getModule(){
        return Module.moduleFromId(moduleId);
    }

    /**
     * Method which adds a Task to the database.
     */
    public void addTask(){
        // Checks if task is not in the database
        if(id == 0){
            // Gets Database connection
            Connection connection = Database.getConnection();
            PreparedStatement pStatement = null;

            // Sets up the query
            String query = "INSERT INTO Task VALUES(null,?,?,?,?,?);";
            try {
                // Fills prepared statement and executes
                pStatement = connection.prepareStatement(query);
                pStatement.setInt(1, userId);
                pStatement.setInt(2, moduleId);
                pStatement.setInt(3, weekId);
                pStatement.setString(4, description);
                pStatement.setString(5, status.toString());

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
     * Method which adds a recurring task to the user's tasks.
     *
     * @param userId id of the user who is adding the task
     * @param weeksOfTask weeks in which the task should be present
     * @param moduleId id of the module associated with task
     * @param description description of the task
     */
    public static void addRecurringTask(int userId, List<Week> weeksOfTask, int moduleId, String description){
        // Goes through all weeks the task has to appear on and creates the tasks
        for(Week week : weeksOfTask){
            // Formats the description if needed
            String alteredDescription = Task.alterTaskDescription(week.getWeekNumber(), description);

            // Creates the task and adds it to the database
            Task newTask = new Task(userId, moduleId, week.getId(), alteredDescription);
            newTask.addTask();
        }
    }

    /**
     * Takes the description of the task with special symbols =, <, > and replaces
     * them with the desired week numbers.
     *
     * Symbol = gets replaced with week number
     * Symbol < gets replaced with week number - 1
     * Symbol > gets replaced with week number + 1
     *
     * @param weekNumber number of the week during which the task should be carried out
     * @param description unaltered description of task
     * @return altered description
     */
    public static String alterTaskDescription(int weekNumber, String description) {
        // Deals with symbol =
        description = description.replace("=", "%d");
        description = String.format(description, weekNumber);

        // Deals with symbol <
        description = description.replace("<", "%d");
        description = String.format(description, weekNumber-1);

        // Deals with symbol >
        description = description.replace(">", "%d");
        description = String.format(description, weekNumber+1);

        // Returns the altered description
        return description;
    }

    /**
     * Method which deletes a Task from the system.
     *
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteTask(){
        // Task doesn't exist in the database
        if(this.getId() == 0) return true;

        int rowsAffected = 0;

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;

        // Sets up the query
        String query = "DELETE FROM Task WHERE id = ?;";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, this.getId());

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

        return rowsAffected!=0;
    }

    /**
     * Method which updates the moduleId, weekId, description and status
     * variables of the task in the database.
     */
    public void updateTask() {
        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;

        // Sets up the query
        String query = "UPDATE Task SET weekId = ?, moduleId = ?, description = ?,  " +
                "status = ? WHERE id = ?";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, weekId);
            pStatement.setInt(2, moduleId);
            pStatement.setString(3, description);
            pStatement.setString(4, status.toString());
            pStatement.setInt(5, id);

            pStatement.execute();

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
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && userId == task.userId && moduleId == task.moduleId && weekId == task.weekId && description.equals(task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, moduleId, weekId, description, status);
    }
}
