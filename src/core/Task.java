package core;

import core.enums.TaskStatus;

import java.util.Objects;

/**
 * Organised.
 * Copyright (c) 2021, Agne Knietaite
 * All rights reserved.
 *
 * This source code is licensed under the GNU General Public License, Version 3
 * found in the LICENSE file in the root directory of this source tree.
 *
 * Class to represent a Task in the system.
 */
public class Task {
    private final int id;
    private final int userId;
    private int moduleId;
    private int weekId;
    private String description;
    private TaskStatus status;

    /**
     * Getter for task id.
     * @return id of task
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for id of the user to whom the task belongs to.
     * @return userId of task
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Getter for id of module which is associated with the task.
     * @return moduleId of task
     */
    public int getModuleId() {
        return moduleId;
    }

    /**
     * Getter for id of week the task belongs to.
     * @return weekId of task
     */
    public int getWeekId() {
        return weekId;
    }

    /**
     * Getter for description of the task.
     * @return description of task
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for completion status of the task.
     * @return status of task
     */
    public TaskStatus getStatus() {
        return status;
    }

    /**
     * Setter for moduleId variable.
     * @param moduleId to set
     */
    public void setModuleId(int moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * Setter for weekId variable.
     * @param weekId to set
     */
    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }

    /**
     * Setter for description variable.
     * @param description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Setter for status variable.
     * @param status to set
     */
    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    /**
     * Constructor for a Task.
     * Used when a task is reconstructed from the database.
     *
     * @param id id of the task
     * @param userId id of the user the task belongs to
     * @param moduleId id of the module the task is associated with
     * @param weekId id of the week the task is associated with
     * @param description description of the task
     * @param status completion status of the task
     */
    public Task(int id, int userId, int moduleId, int weekId, String description, TaskStatus status) {
        this.id = id;
        this.userId = userId;
        this.moduleId = moduleId;
        this.weekId = weekId;
        this.description = description;
        this.status = status;
    }

    /**
     * Constructor for a Task.
     * Used when a new task is created by the user.
     *
     * @param userId id of the user the task belongs to
     * @param moduleId id of the module the task is associated with
     * @param weekId id of the week the task is associated with
     * @param description description of the task
     * @param status completion status of the task
     */
    public Task(int userId, int moduleId, int weekId, String description, TaskStatus status) {
        this.id = 0;    // Task is not reconstructed from db/not already in db
        this.userId = userId;
        this.moduleId = moduleId;
        this.weekId = weekId;
        this.description = description;
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id && userId == task.userId && moduleId == task.moduleId && weekId == task.weekId && description.equals(task.description) && status == task.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, moduleId, weekId, description, status);
    }
}
