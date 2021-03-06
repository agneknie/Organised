package core.enums;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Enum representing task status in the system.
 * Either "Yes"(Completed), "No"(Not completed) or "Dropped"(Won't be completed)
 */
public enum TaskStatus {

    NO("No"),               // Not Completed
    YES("Yes"),             // Completed
    DROPPED("Dropped");     // Won't be Completed

    public final String VALUE;
    TaskStatus(String value) {
        VALUE = value;
    }

    @Override
    public String toString(){
        return VALUE;
    }

    /**
     * Method which takes a string and returns a corresponding task status
     *
     * @param taskStatusString TaskStatus as string
     * @return TaskStatus which corresponds to a string
     */
    public static TaskStatus stringToTaskStatus(String taskStatusString){
        for(TaskStatus taskStatus : TaskStatus.values()){
            if(taskStatusString.equals(taskStatus.VALUE)) return taskStatus;
        }
        throw new IllegalArgumentException("TaskStatus " +taskStatusString+ " does not exist.");
    }

    /**
     * Returns the next TaskStatus value, which follows after the current one.
     * If invalid TaskStatus, returns null.
     *
     * @return next/following TaskStatus value
     */
    public TaskStatus nextStatus(){
        switch (this){
            case NO:
                return TaskStatus.YES;
            case YES:
                return TaskStatus.DROPPED;
            case DROPPED:
                return TaskStatus.NO;
            // If something goes wrong, returns null
            default:
                return null;
        }
    }

    /**
     * Method which returns the color, which is associated with
     * the current TaskStatus.
     * If invalid TaskStatus, returns null
     *
     * @return color of TaskStatus
     */
    public String getColor(){
        // Associated colours
        final String COLOR_NO = "#E89E99";
        final String COLOR_YES = "#84D99B";
        final String COLOR_DROPPED = "#E0DE8D";

        // Returns a colour based on enum
        switch (this){
            case NO:
                return COLOR_NO;
            case YES:
                return COLOR_YES;
            case DROPPED:
                return COLOR_DROPPED;
            // If something goes wrong, returns null
            default:
                return null;
        }
    }
}
