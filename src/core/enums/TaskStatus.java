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

    YES("Yes"),             // Completed
    NO("No"),               // Not Completed
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
}
