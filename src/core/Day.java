package core;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class to represent a Day in the system
 */
public class Day {
    private final int id;
    private final int userId;
    private final int weekId;
    private final LocalDate date;
    private int hoursSpent;

    /**
     * Getter for id.
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for week id.
     * @return weekId of the week this day belongs to
     */
    public int getWeekId() {
        return weekId;
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
     * Setter for our spent studying/working this day
     * @param hoursSpent hours of day spent working
     */
    public void setHoursSpent(int hoursSpent) {
        this.hoursSpent = hoursSpent;
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
        this.hoursSpent = 0;    // Day just added, no hours spent yet
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
        // Converts string to a date
        this.date = LocalDate.parse(date);
    }
}
