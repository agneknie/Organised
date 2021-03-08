package core;

import core.enums.ScheduleTime;
import database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class to represent a Day in the system.
 */
public class Day {
    private final int id;
    private final int userId;
    private final int weekId;
    private final LocalDate date;
    private int hoursSpent;

    public static final int MAX_WORK_HOURS = 12;

    /**
     * Getter for id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the Date variable.
     * @return date of the day
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Getter for hours spent studying/working this day
     * @return hours of day spent working
     */
    public int getHoursSpent() {
        return hoursSpent;
    }

    /**
     * Returns the name of the day as a String.
     * e.g. Monday, Friday, etc.
     * @return name of the day
     */
    public String getName(){
        return this.getDate().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
    }

    /**
     * Returns the short name of the day as a String.
     * e.g. Mon, Fri, etc.
     * @return name of the day
     */
    public String getShortName(){
        return this.getDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
    }

    /**
     * Returns short date of the day as a String. The date includes month and day.
     * e.g. 05/28, 07/08, 04/25
     * @return date of day
     */
    public String getShortDate(){
        // Creates date formatter
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd");
        return this.getDate().format(dateFormatter);
    }

    /**
     * Constructor for creating a new Day instance.
     * Used for when user is adding a new Period with Weeks and Days.
     *
     * @param userId id of the user the day belongs to
     * @param weekId id of the week the day belongs to
     * @param date date of the day
     */
    public Day (int userId, int weekId, LocalDate date){
        this.id = 0;    // Day is not reconstructed from db/not already in db
        this.userId = userId;
        this.weekId = weekId;
        this.date = date;
        this.hoursSpent = 0;    // Day just created, no hours spent yet
    }

    /**
     * Constructor for creating a new Day instance.
     * Used when a Day is reconstructed from the database.
     *
     * @param id id of the day
     * @param userId id of the user the day belongs to
     * @param weekId id of the week the day belongs to
     * @param date date of the day
     * @param hoursSpent hours spent working during this day
     */
    public Day (int id, int userId, int weekId, String date, int hoursSpent){
        this.id = id;
        this.userId = userId;
        this.weekId = weekId;
        this.date = LocalDate.parse(date);  // Converts string to a date
        this.hoursSpent = hoursSpent;
    }

    /**
     * Constructor for day.
     * Constructs a day instance from the database based on day id.
     *
     * @param id id of the day
     */
    public static Day dayFromId(int id){
        // Creates empty day object
        Day newDay = null;

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        ResultSet rs = null;

        // Sets up the query
        String query = "SELECT * FROM Day WHERE id = ?;";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, id);

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // If there are items in the result set, reconstructs the Module
            int userId = rs.getInt("userId");
            int weekId = rs.getInt("weekId");
            String date = rs.getString("date");
            int hoursSpent = rs.getInt("hoursSpent");

            newDay = new Day(id, userId, weekId, date, hoursSpent);

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

        // Returns the day
        return newDay;
    }

    /**
     * Adds a specified amount of hours to the hoursSpent variable of the day.
     *
     * Increments user's hours spent variable by the specified amount,
     * indicating that more hours have been spent working during this day.
     *
     * @param hours hours to increment hoursSpent by
     */
    public void addHour(int hours){
        if ((hoursSpent+hours)>MAX_WORK_HOURS)
            throw new IllegalArgumentException("Hours are more than maximum work hours" +
                    "allowed: " + MAX_WORK_HOURS);
        else hoursSpent += hours;
    }

    /**
     * Method which checks whether given number of minutes can be added to a day.
     * Minutes cannot be added if they go over the MAX_WORK_HOURS limit.
     *
     * @param minutes minutes to add
     * @return true if minutes can be added, false otherwise
     */
    public boolean canAdd(int minutes){
        return (minutes / 60 + hoursSpent) <= MAX_WORK_HOURS;
    }

    /**
     * Method which checks whether given number of minutes can be removed from a day.
     * Minutes cannot be removed if they the day's hours spent variable will go bellow 0.
     *
     * @param minutes minutes to remove
     * @return true if minutes can be removed, false otherwise
     */
    public boolean canRemove(int minutes){
        return (hoursSpent * 60 - minutes) >= 0;
    }

    /**
     * Method which removes a specified number of hours from the Day's hoursSpent variable.
     *
     * @param hours hours to decrease hoursSpent by
     */
    public void removeHours(int hours){
        if((hoursSpent-hours)<0)
            throw new IllegalArgumentException("If hours are removed, day work hours fall below 0");
        else hoursSpent -= hours;
    }

    /**
     * Method which adds a Day to the database
     */
    protected void addDay(){
        // Checks if day is not in the database
        if(id == 0){
            // Gets Database connection
            Connection connection = Database.getConnection();
            PreparedStatement pStatement = null;

            // Sets up the query
            String query = "INSERT INTO Day VALUES(null,?,?,?,?);";
            try {
                // Fills prepared statement and executes
                pStatement = connection.prepareStatement(query);
                pStatement.setInt(1, userId);
                pStatement.setInt(2, weekId);
                pStatement.setString(3, date.toString());
                pStatement.setInt(4, hoursSpent);

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
     * Method which deletes a Day from the system.
     *
     * @return true if successful, false otherwise
     */
    public boolean deleteDay(){
        // Day doesn't exist in the database
        if(this.getId() == 0) return true;

        // Deletes all events of the day
        int eventCounter = 0;
        List<Event> events = this.getAllEvents();
        for(Event event : events){
            if(event.deleteEvent()) eventCounter++;
        }

        // If event deletion was successful, deletes the day
        int rowsAffected = 0;
        if(eventCounter == events.size()){
            // Gets Database connection
            Connection connection = Database.getConnection();
            PreparedStatement pStatement = null;

            // Sets up the query
            String query = "DELETE FROM Day WHERE id = ?;";
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
        }
        // Returns whether deletion was successful
        return rowsAffected == 1;
    }

    /**
     * Method which updates the hoursSpent variable of the day
     * in the database.
     */
    public void updateDay(){
        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;

        // Sets up the query
        String query = "UPDATE Day SET hoursSpent = ? WHERE id = ?";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, hoursSpent);
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
     * Method which returns all events of the Day.
     *
     * @return List of all Events belonging to this Day.
     */
    public List<Event> getAllEvents(){
        ArrayList<Event> events = new ArrayList<>();

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        ResultSet rs = null;

        // Sets up the query
        String query = "SELECT * FROM Event WHERE dayId = ? AND userId = ?;";
        try {
            // Fills prepared statement and executes
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, id);
            pStatement.setInt(2, userId);

            //Executes the statement, gets the result set
            rs = pStatement.executeQuery();

            // If there are items in the result set, reconstructs the Days and saves them in a list
            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("userId");
                int moduleId = rs.getInt("moduleId");
                String name = rs.getString("name");
                String description = rs.getString("description");
                ScheduleTime startTime = ScheduleTime.stringToScheduleTime(rs.getString("startTime"));
                ScheduleTime endTime = ScheduleTime.stringToScheduleTime(rs.getString("endTime"));

                Event newEvent = new Event(id, userId, this.id, moduleId, name, description, startTime, endTime);
                events.add(newEvent);
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

        // Returns a list of event objects
        return events;
    }

    @Override
    public String toString(){
        return this.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
        return id == day.id && userId == day.userId && weekId == day.weekId && hoursSpent == day.hoursSpent && date.equals(day.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, weekId, date, hoursSpent);
    }
}
