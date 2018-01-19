package com.janek.todolist.ui.tasks

import com.janek.todolist.commons.models.TaskItem

/**
 * Created by janek on 1/16/18.
 */
interface TasksView {
    fun render(tasks: List<TaskItem>)
}