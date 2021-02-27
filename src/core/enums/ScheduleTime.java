package core.enums;

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
 * Enum representing time available for event scheduling in the system.
 * Available times are: 09:00, 10:00, 11:00, 12:00, 13:00, 14:00, 15:00, 16:00, 17:00.
 * Last time (17:00) should not be available as event start time.
 */
public enum ScheduleTime {
    NINE("9:00"),
    TEN("10:00"),
    ELEVEN("11:00"),
    TWELVE("12:00"),
    THIRTEEN("13:00"),
    FOURTEEN("14:00"),
    FIFTEEN("15:00"),
    SIXTEEN("16:00"),
    SEVENTEEN("17:00");

    public final String VALUE;

    ScheduleTime(String value){
        VALUE = value;
    }

    @Override
    public String toString(){
        return VALUE;
    }

    /**
     * Method which takes a string and returns a corresponding ScheduleTime
     *
     * @param timeString  ScheduleTime as a string
     * @return ScheduleTime which corresponds to a string
     * */
    public static ScheduleTime stringToScheduleTime (String timeString) {
        for (ScheduleTime time : ScheduleTime.values()) {
            if(timeString.equals(time.VALUE)) return time;
        }
        throw new IllegalArgumentException("ScheduleTime " + timeString + " does not exist");
    }

    /**
     * Method which converts schedule time to an integer.
     * E.g. 9:00 to 9, 10:00 to 10.
     * @param scheduleTime ScheduleTime to convert
     * @return int representation of given ScheduleTime
     */
    public static int scheduleTimeToInt(ScheduleTime scheduleTime){
        if(scheduleTime == NINE){
            return Integer.parseInt(scheduleTime.toString().substring(0,1));
        }
        else{
            return Integer.parseInt(scheduleTime.toString().substring(0,2));
        }
    }

    /**
     * Method which takes two Schedule times, which are supposed to
     * represent start and end of the event and returns the number of
     * hours between this range.
     *
     * @param start start of range
     * @param end end of range
     * @return how long the range spans/event takes
     */
    public static int hoursBetweenTimes(ScheduleTime start, ScheduleTime end){
        return scheduleTimeToInt(end) - scheduleTimeToInt(start);
    }

    /**
     * Method which returns all possible event start times.
     * @return List of start times
     */
    public static List<ScheduleTime> getStartTimes(){
        List<ScheduleTime> startTimes = new ArrayList<>();
        startTimes.add(ScheduleTime.NINE);
        startTimes.add(ScheduleTime.TEN);
        startTimes.add(ScheduleTime.ELEVEN);
        startTimes.add(ScheduleTime.TWELVE);
        startTimes.add(ScheduleTime.THIRTEEN);
        startTimes.add(ScheduleTime.FOURTEEN);
        startTimes.add(ScheduleTime.FIFTEEN);
        startTimes.add(ScheduleTime.SIXTEEN);
        return startTimes;
    }

    /**
     * Method which returns all possible event end times.
     * @return List of end times
     */
    public static List<ScheduleTime> getEndTimes(){
        List<ScheduleTime> endTimes = new ArrayList<>();
        endTimes.add(ScheduleTime.TEN);
        endTimes.add(ScheduleTime.ELEVEN);
        endTimes.add(ScheduleTime.TWELVE);
        endTimes.add(ScheduleTime.THIRTEEN);
        endTimes.add(ScheduleTime.FOURTEEN);
        endTimes.add(ScheduleTime.FIFTEEN);
        endTimes.add(ScheduleTime.SIXTEEN);
        endTimes.add(ScheduleTime.SEVENTEEN);
        return endTimes;
    }
}
