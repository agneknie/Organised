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
 * Class to represent a Period in the system.
 */
public class Period {
    private final int id;
    private final int userId;
    private final int associatedYear;
    private final String name;

    /**
     * Getter for the period id.
     * @return id of the period
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for id of the user the period belongs to.
     * @return userId of the period
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter for year number, which is associated with the period.
     * @return year number of the period
     */
    public int getAssociatedYear() {
        return associatedYear;
    }

    /**
     * Getter for period name.
     * @return name of the period.
     */
    public String getName() {
        return name;
    }

    /**
     * Constructor for Period.
     * Used when a Period instance is reconstructed from the database.
     *
     * @param id id of the Period
     * @param userId id of the user the period belongs to
     * @param associatedYear year number associated with the period
     * @param name name of the period
     */
    public Period(int id, int userId, int associatedYear, String name) {
        this.id = id;
        this.userId = userId;
        this.associatedYear = associatedYear;
        this.name = name;
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
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return id == period.id && userId == period.userId && associatedYear == period.associatedYear && name.equals(period.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, associatedYear, name);
    }
}
