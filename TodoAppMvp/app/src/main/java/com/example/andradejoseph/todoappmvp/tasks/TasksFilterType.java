package com.example.andradejoseph.todoappmvp.tasks;

/**
 * Created by ANDRADEJOSEPH on 5/16/2017.
 */

public enum TasksFilterType {
    /**
     * Do not filter tasks.
     */
    ALL_TASKS,

    /**
     * Filters only the active (not completed yet) tasks.
     */
    ACTIVE_TASKS,

    /**
     * Filters only the completed tasks.
     */
    COMPLETED_TASKS
}
