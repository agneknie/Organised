package core;

import core.enums.ScheduleTime;
import database.Database;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class to represent a scheduled Event in the system.
 */
public class Event {
    private final int id;
    private final int userId;
    private final int dayId;
    private final int moduleId;
    private String name;
    private String description;
    private ScheduleTime startTime;
    private ScheduleTime endTime;

    /**
     * Getter for id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for userId.
     * @return id of the user the event belongs to
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter for dayId.
     * @return id of the day the event belongs to
     */
    public int getDayId() {
        return dayId;
    }

    /**
     * Getter for moduleId.
     * @return id of the module the event is associated with
     */
    public int getModuleId() {
        return moduleId;
    }

    /**
     * Getter for event name.
     * @return name of event
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for event description.
     * @return description of event
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for event start time.
     * @return event start time
     */
    public ScheduleTime getStartTime() {
        return startTime;
    }

    /**
     * Getter for event end time.
     * @return event end time
     */
    public ScheduleTime getEndTime() {
        return endTime;
    }

    /**
     * Setter for event name
     * @param name of the event
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for event description
     * @param description of the event
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter for event start time
     * @param startTime of the event
     */
    public void setStartTime(ScheduleTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Setter for event end time
     * @param endTime of the event
     */
    public void setEndTime(ScheduleTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Constructor for creating an Event instance.
     * Used when an Event is reconstructed from the database.
     *
     * @param id id of event
     * @param userId userId of event
     * @param dayId dayId of day which the event belongs to
     * @param moduleId moduleId of the module the event is associated with
     * @param name name of event
     * @param description description of event
     * @param startTime start time of event
     * @param endTime end time of event
     */
    public Event(int id, int userId, int dayId, int moduleId, String name, String description,
                 ScheduleTime startTime, ScheduleTime endTime){
        this.id = id;
        this.userId = userId;
        this.dayId = dayId;
        this.moduleId = moduleId;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Constructor for creating an Event instance.
     * Used when a new event is created by the user.
     *
     * @param userId userId of event
     * @param dayId dayId of day which the event belongs to
     * @param moduleId moduleId of the module the event is associated with
     * @param name name of event
     * @param description description of event
     * @param startTime start time of event
     * @param endTime end time of event
     */
    public Event(int userId, int dayId, int moduleId, String name, String description,
                 ScheduleTime startTime, ScheduleTime endTime){
        this.id = 0;    // Event is not reconstructed from db/not already in db
        this.userId = userId;
        this.dayId = dayId;
        this.moduleId = moduleId;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns the colour in which the event should be coloured.
     * Corresponds to the colour of the module.
     *
     * @return colour of event
     */
    public Color getEventColour(){
        return Module.moduleFromId(this.id).getColour();
    }

    /**
     * Method which adds an Event to the database.
     */
    public void addEvent(){
        // Checks if event is not in the database
        if(id == 0){
            // Gets Database connection
            Connection connection = Database.getConnection();
            PreparedStatement pStatement = null;

            // Sets up the query
            String query = "INSERT INTO Event VALUES(null,?,?,?,?,?,?,?);";
            try {
                // Fills prepared statement and executes
                pStatement = connection.prepareStatement(query);
                pStatement.setInt(1, userId);
                pStatement.setInt(2, dayId);
                pStatement.setInt(3, moduleId);
                pStatement.setString(4, name);
                pStatement.setString(5, description);
                pStatement.setString(6, startTime.toString());
                pStatement.setString(7, endTime.toString());

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
     * Method which adds a recurring event to the user's schedule.
     *
     * @param userId id of the user who is adding the event
     * @param day day of the week the event takes place
     * @param weeksOfEvent weeks in which the event takes place
     * @param moduleId id of the module associated with event
     * @param name name of the event
     * @param description description of the event
     * @param startTime start time of the event
     * @param endTime end time of the event
     */
    public static void addRecurringEvent(int userId, DayOfWeek day, List<Week> weeksOfEvent, int moduleId, String name,
                                  String description, ScheduleTime startTime, ScheduleTime endTime){
        // List to save the events in
        List<Event> recurringEvents = new ArrayList<>();

        // Goes through all weeks the event has to appear on and creates the events
        for(Week week : weeksOfEvent){
            // Gets the event day id
            int dayId = week.getDay(day).getId();

            // Formats the description if needed
            String alteredDescription = Event.alterEventDescription(week.getWeekNumber(), description);

            // Creates the event and adds it to the database
            Event newEvent = new Event(userId, dayId, moduleId, name, alteredDescription, startTime, endTime);
            newEvent.addEvent();
        }
    }

    /**
     * Takes the description of the event with special symbols =, <, > and replaces
     * them with the desired week numbers.
     *
     * Symbol = gets replaced with week number
     * Symbol < gets replaced with week number - 1
     * Symbol > gets replaced with week number + 1
     *
     * @param weekNumber number of the week during which the event takes place
     * @param description unaltered description of event
     * @return altered description
     */
    public static String alterEventDescription(int weekNumber, String description){
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
     * Method which deletes an Event from the system.
     *
     * @return true if successful, false otherwise
     */
    public boolean deleteEvent(){
        // Event doesn't exist in the database
        if(this.getId() == 0) return true;

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        int rowsAffected = 0;

        // Sets up the query
        String query = "DELETE FROM Event WHERE id = ?;";
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
        // Returns whether deletion was successful
        return rowsAffected == 1;
    }

    /**
     * Method which updates the name, description, startTime and endTime
     * variables of the event in the database.
     */
    public void updateEvent() {
        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;

        // Sets up the query
        String query = "UPDATE Event SET moduleId = ?, name = ?, description = ?,  " +
                "startTime = ?, endTime = ? WHERE id = ?";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, moduleId);
            pStatement.setString(2, name);
            pStatement.setString(3, description);
            pStatement.setString(4, startTime.toString());
            pStatement.setString(5, endTime.toString());
            pStatement.setInt(6, id);

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
}
