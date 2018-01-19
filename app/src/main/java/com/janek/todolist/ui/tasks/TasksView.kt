package com.janek.todolist.ui.tasks

import com.janek.todolist.commons.models.TaskItem

interface TasksView {
    fun render(tasks: List<TaskItem>)
}