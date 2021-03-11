package core.enums;

import java.time.LocalTime;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Enum representing time of day in the system.
 * Either Morning, Afternoon, Evening or Night
 */
public enum TimeOfDay {
    MORNING("Morning"),
    AFTERNOON("Afternoon"),
    EVENING("Evening"),
    NIGHT("Night");

    public final String VALUE;
    TimeOfDay(String value){
        VALUE = value;
    }

    @Override
    public String toString(){
        return VALUE;
    }

    /**
     * Method which takes the local time of the machine and returns a time of day it
     * corresponds to.
     *
     * @return TimeOfDay based on LocalTime of user
     */
    public static TimeOfDay getTimeOfDay(){
        // Gets current user's time
        LocalTime currentTime = LocalTime.now();

        // Times slicing the day into sequences
        final LocalTime MORNING_START = LocalTime.of(5, 0);
        final LocalTime AFTERNOON_START = LocalTime.of(12, 0);
        final LocalTime EVENING_START = LocalTime.of(18, 0);
        final LocalTime NIGHT_START = LocalTime.of(22, 0);

        // Morning
        if(currentTime.isAfter(MORNING_START) && currentTime.isBefore(AFTERNOON_START) || currentTime.equals(MORNING_START))
            return MORNING;
        // Afternoon
        else if(currentTime.isAfter(AFTERNOON_START) && currentTime.isBefore(EVENING_START) || currentTime.equals(AFTERNOON_START))
            return AFTERNOON;
        // Evening
        else if(currentTime.isAfter(EVENING_START) && currentTime.isBefore(NIGHT_START) || currentTime.equals(EVENING_START))
            return EVENING;
        // Night
        else if(currentTime.isAfter(NIGHT_START) || currentTime.isBefore(MORNING_START) || currentTime.equals(NIGHT_START))
            return NIGHT;
        // Something is seriously wrong
        else
            return null;
    }
}
