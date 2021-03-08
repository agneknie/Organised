package core;

import core.enums.ScheduleTime;
import database.Database;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.DayOfWeek;
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
 * Class to represent a scheduled Event in the system.
 */
public class Event {
    private final int id;
    private final int userId;
    private int dayId;
    private int moduleId;
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
     * Setter for dayId.
     * @param dayId of the day the event belongs to
     */
    public void setDayId(int dayId) {
        this.dayId = dayId;
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
     * Setter for event module id
     * @param moduleId id of the module the event belongs to
     */
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
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
     * @return colour of event as a string
     */
    public String getEventColourString(){
        return this.getModule().getColourAsString();
    }

    /**
     * Returns the colour in which the event should be coloured.
     * Corresponds to the colour of the module.
     *
     * @return colour as colour object of event
     */
    public Color getEventColour(){
        return this.getModule().getColour();
    }

    /**
     * Method which returns the code of the module, which is associated
     * with the event.
     *
     * @return code of module associated with event
     */
    public String getModuleCode(){
        return this.getModule().getCode();
    }

    /**
     * Method which returns the module associated with the event.
     *
     * @return module of the event
     */
    public Module getModule(){
        return Module.moduleFromId(moduleId);
    }

    /**
     * Method which returns the day associated with the event.
     *
     * @return day of the event
     */
    public Day getDay(){
        return Day.dayFromId(dayId);
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
     * @return true if deletion successful, false otherwise
     */
    public boolean deleteEvent(){
        // Event doesn't exist in the database
        if(this.getId() == 0) return true;

        int rowsAffected = 0;

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;

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

        return rowsAffected!=0;
    }

    /**
     * Method which updates the name, description, moduleId
     * dayId, startTime and endTime
     * variables of the event in the database.
     */
    public void updateEvent() {
        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;

        // Sets up the query
        String query = "UPDATE Event SET dayId = ?, moduleId = ?, name = ?, description = ?,  " +
                "startTime = ?, endTime = ? WHERE id = ?";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, dayId);
            pStatement.setInt(2, moduleId);
            pStatement.setString(3, name);
            pStatement.setString(4, description);
            pStatement.setString(5, startTime.toString());
            pStatement.setString(6, endTime.toString());
            pStatement.setInt(7, id);

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

    /**
     * Method which takes an event and from its start and end times figures out
     * which time slots of the given weekdayPane have to be populated with event data.
     *
     * @param weekdayPane pane which holds all time slots/labels
     * @return List of Labels/time slots which should be populated with event data.
     */
    public List<Label> getTimeSlotsOfEvent(Pane weekdayPane){
        // Gets the list of all time slots of weekday pane
        List<Label> timeSlotsOfDay = new ArrayList<>();
        for(Node child : weekdayPane.getChildren()){
            timeSlotsOfDay.add((Label)child);
        }

        // Picks the time slot which is the start of the event
        int timeSlotNumber = 0;
        switch (this.startTime){
            case NINE:
                timeSlotNumber = 0;
                break;
            case TEN:
                timeSlotNumber = 1;
                break;
            case ELEVEN:
                timeSlotNumber = 2;
                break;
            case TWELVE:
                timeSlotNumber = 3;
                break;
            case THIRTEEN:
                timeSlotNumber = 4;
                break;
            case FOURTEEN:
                timeSlotNumber = 5;
                break;
            case FIFTEEN:
                timeSlotNumber = 6;
                break;
            case SIXTEEN:
                timeSlotNumber = 7;
                break;
        }

        // Goes through time slots of the day and picks the ones that belong to the event
        List<Label> timeSlotsOfEvent = new ArrayList<>();
        int eventLength = ScheduleTime.hoursBetweenTimes(this.startTime, this.endTime);
        while(eventLength!=0){
            timeSlotsOfEvent.add(timeSlotsOfDay.get(timeSlotNumber));
            timeSlotNumber++;
            eventLength--;
        }

        // Returns the time slots belonging to event
        return timeSlotsOfEvent;
    }

    /**
     * Checks if the event conflicts (is scheduled at the same time) as any
     * other event.
     *
     * @return true if event's time conflicts with another event's time.
     */
    public boolean isTimeConflicting(){
        // Gets all events of the day during which this event takes place
        List<Event> dayEvents = this.getDay().getAllEvents();
        dayEvents.remove(this);

        // Goes through that list and checks event times against times of this event
        int startTime = ScheduleTime.scheduleTimeToInt(this.startTime);
        int endTime = ScheduleTime.scheduleTimeToInt(this.endTime);
        for(Event event : dayEvents){
            int eventStartTime = ScheduleTime.scheduleTimeToInt(event.getStartTime());
            int eventEndTime = ScheduleTime.scheduleTimeToInt(event.getEndTime());
            // If event starts during another event
            if((eventStartTime>=startTime && eventStartTime<endTime) ||
                    (startTime>=eventEndTime && endTime<eventStartTime)) return true;
            // If event ends during another event
            if((eventEndTime>startTime && eventEndTime<=endTime) ||
                    (startTime>eventEndTime && endTime<=eventEndTime)) return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id && userId == event.userId && dayId == event.dayId && moduleId == event.moduleId && name.equals(event.name) && description.equals(event.description) && startTime == event.startTime && endTime == event.endTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, dayId, moduleId, name, description, startTime, endTime);
    }
}
