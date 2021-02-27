package core;

import database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
 * Class to represent a Week in the system.
 */
public class Week {
    private final int id;
    private final int userId;
    private final int periodId;
    private final int weekNumber;
    private final LocalDate startDate;

    /**
     * Getter for week id.
     * @return id of the week
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the id of the user the week belongs to.
     * @return userId of the week
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter for the id of the period the week belongs to.
     * @return periodId of the week
     */
    public int getPeriodId() {
        return periodId;
    }

    /**
     * Getter for the week number of the week.
     * @return weekNumber of the week
     */
    public int getWeekNumber() {
        return weekNumber;
    }

    /**
     * Getter for the start date of the week.
     * @return date of the first day (Monday) of the week
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Constructor for creating a new Week instance.
     * Used when user is adding a new Period with Weeks.
     *
     * @param userId id of the user the week belongs to
     * @param periodId id of the period the week belongs to
     * @param weekNumber number of the week
     * @param startDate date of the Monday of the week
     */
    public Week (int userId, int periodId, int weekNumber, LocalDate startDate){
        this.id = 0;    // Week is not reconstructed from db/not already in db
        this.userId = userId;
        this.periodId = periodId;
        this.weekNumber = weekNumber;
        this.startDate = startDate;
    }

    /**
     * Constructor for creating a new Week instance.
     * Used when a Week instance is reconstructed from the database.
     *
     * @param id id of the week
     * @param userId id of the user the week belongs to
     * @param periodId id of the period the week belongs to
     * @param weekNumber number of the week
     * @param startDate date of the Monday of the week
     */
    public Week (int id, int userId, int periodId, int weekNumber, String startDate){
        this.id = id;
        this.userId = userId;
        this.periodId = periodId;
        this.weekNumber = weekNumber;
        this.startDate = LocalDate.parse(startDate);  // Converts string to a date
    }

    /**
     * Returns date range of the week for display in the UI.
     *
     * @return week date
     */
    public String getWeekDate(){
        String startMonth = startDate.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        String endMonth = startDate.plusDays(6).getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        String startDay = Integer.toString(startDate.getDayOfMonth());
        String endDay = Integer.toString(startDate.plusDays(6).getDayOfMonth());
        String year = Integer.toString(startDate.getYear());
        return year+" "+startMonth+" "+startDay+" - "+endMonth+" "+endDay;
    }

    /**
     * Method which goes through the database and returns the id of the week,
     * which is the last added week.
     *
     * @return id of the most recent added week. -1 if something is wrong.
     */
    protected static int getLastId() {
        int lastPeriod = -1;

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;

        // Sets up the query
        String query = "SELECT * FROM Week ORDER BY id DESC LIMIT 1;";
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
        // Returns the id of last week
        return lastPeriod;
    }

    /**
     * Method which given a list of weeks returns the last available week
     * which has hours recorded, thus finding the current week of the period
     * if the provided list is a list of all weeks in a period.
     * Assumes that weeks are sorted by their week number (like in database).
     *
     * @param weeks list of weeks (All weeks of period)
     * @return current week
     */
    public static Week getCurrentWeek(List<Week> weeks){
        // Selects first available week in case all weeks are new
        Week current = weeks.get(0);

        // Goes through all weeks and selects the most recent week with hours added
        for(Week week : weeks){
            if(week.getAllWeekHours()>0) current = week;
        }
        return current;
    }

    /**
     * Method which adds the week instance to the database along with
     * creation and addition to the database of all days belonging to this
     * week.
     */
    protected void constructWeek(){
        // Adds the week to the database
        this.addWeek();
        int weekId = Week.getLastId();

        // Creates all days of the Week and adds them to the database
        for(int i=0; i<7; i++){
            Day newDay = new Day(this.userId, weekId, this.startDate.plusDays(i));
            newDay.addDay();
        }
    }

    /**
     * Method which adds a Week to the database.
     */
    private void addWeek(){
        // Checks if week is not in the database
        if(id == 0){
            // Gets Database connection
            Connection connection = Database.getConnection();
            PreparedStatement pStatement = null;

            // Sets up the query
            String query = "INSERT INTO Week VALUES(null,?,?,?,?);";
            try {
                // Fills prepared statement and executes
                pStatement = connection.prepareStatement(query);
                pStatement.setInt(1, userId);
                pStatement.setInt(2, periodId);
                pStatement.setInt(3, weekNumber);
                pStatement.setString(4, startDate.toString());

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
     * Method which returns a day of belonging to the week based on
     * the requested day of week.
     *
     * @param dayOfWeek day to be returned
     * @return day belonging to this week
     */
    public Day getDay(DayOfWeek dayOfWeek){
        List<Day> daysOfWeek = this.getAllDays();
        Day day = null;

        // Selects the needed day
        switch (dayOfWeek){
            case MONDAY:
                day = daysOfWeek.get(0);
                break;
            case TUESDAY:
                day = daysOfWeek.get(1);
                break;
            case WEDNESDAY:
                day = daysOfWeek.get(2);
                break;
            case THURSDAY:
                day = daysOfWeek.get(3);
                break;
            case FRIDAY:
                day = daysOfWeek.get(4);
                break;
            case SATURDAY:
                day = daysOfWeek.get(5);
                break;
            case SUNDAY:
                day = daysOfWeek.get(6);
                break;
        }

        // Returns the day
        return day;
    }

    /**
     * Method which returns all days of the Week.
     *
     * @return List of all Days belonging to this Week.
     */
    public List<Day> getAllDays(){
        ArrayList<Day> days = new ArrayList<>();

        // Gets Database connection
        Connection connection = Database.getConnection();
        PreparedStatement pStatement = null;
        ResultSet rs = null;

        // Sets up the query
        String query = "SELECT * FROM Day WHERE weekId = ? AND userId = ?;";
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
                String date = rs.getString("date");
                int hoursSpent = rs.getInt("hoursSpent");

                Day newDay = new Day(id, userId, this.id, date, hoursSpent);
                days.add(newDay);
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

        // Returns a list of Day objects
        return days;
    }

    /**
     * Method which gets all days of the week and returns the cumulative
     * number of hours spent working.
     *
     * @return hours spent working during the week
     */
    public int getAllWeekHours(){
        int hours = 0;
        for(Day day : this.getAllDays()){
            hours += day.getHoursSpent();
        }
        return hours;
    }

    /**
     * Method which returns how many minutes per day on average during
     * this week are spent working.
     *
     * @return average minutes spent working per day this week
     */
    public int getDailyAverage(){
        // 7 days in a week
        return (int) Math.round((this.getAllWeekHours() * 60 / 7.0) * 1);
    }

    /**
     * Method which deletes a Week and all of its attached Days from the database.
     *
     * @return true if successful, false otherwise
     */
    public boolean deleteWeek(){
        int rowsAffected = 0;

        // Checks if Week exists in the database
        if(this.getId() != 0) {
            // Deletes all Days of the Week
            int counter = 0;
            List <Day> days = this.getAllDays();
            for(Day day : days){
                if(day.deleteDay()) counter++;
            }

            // If day deletion was successful, deletes the week
            if (counter == days.size()){

                // Gets Database connection
                Connection connection = Database.getConnection();
                PreparedStatement pStatement = null;

                // Sets up the query
                String query = "DELETE FROM Week WHERE id = ?;";
                try {
                    // Fills prepared statement and executes
                    pStatement = connection.prepareStatement(query);
                    pStatement.setInt(1, this.getId());

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
        }
        // If deletion was successful, returns true
        return rowsAffected != 0;
    }

    /**
     * Method which returns a list of dummy Day objects which represent
     * a Week. Used for filling a combo box.
     * NOT TO BE USED FOR ACTUAL WEEK CONSTRUCTION!
     *
     * @return List of Day objects representing a week.
     */
    public static List<Day> daysForComboBox(){
        // Creates array for days
        List<Day> days = new ArrayList<>();

        // Creates dummy week
        LocalDate date = LocalDate.of(2000, 07, 03);
        for(int i=0; i<7; i++){
            days.add(new Day(0, 0, date.plusDays(i)));
        }

        // Returns the week/days
        return days;
    }

    @Override
    public String toString(){
        return "Week " + weekNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Week week = (Week) o;
        return id == week.id && userId == week.userId && periodId == week.periodId && weekNumber == week.weekNumber && startDate.equals(week.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, periodId, weekNumber, startDate);
    }
}
