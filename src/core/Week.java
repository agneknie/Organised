package core;

import java.time.LocalDate;
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
    private int minutesLeft;

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
     * Getter for the minutes, that are left unturned into hours for this week.
     * @return minutesLeft of the week
     */
    public int getMinutesLeft() {
        return minutesLeft;
    }

    /**
     * Setter for the minutesLeft variable of the week instance.
     * @param minutesLeft minutesLeft to set
     */
    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
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
        this.minutesLeft = 0;   // No minutes left yet
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
     * @param minutesLeft minutes left unturned to hours for the week
     */
    public Week (int id, int userId, int periodId, int weekNumber, String startDate, int minutesLeft){
        this.id = id;
        this.userId = userId;
        this.periodId = periodId;
        this.weekNumber = weekNumber;
        this.startDate = LocalDate.parse(startDate);  // Converts string to a date
        this.minutesLeft = minutesLeft;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Week week = (Week) o;
        return id == week.id && userId == week.userId && periodId == week.periodId && weekNumber == week.weekNumber && minutesLeft == week.minutesLeft && startDate.equals(week.startDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, periodId, weekNumber, startDate, minutesLeft);
    }
}
