package com.janek.todolist.ui.tasks

import com.janek.todolist.commons.models.TaskItem

/**
 * Created by janek on 1/16/18.
 */

class TasksPresenter(private val view: TasksView) {

    private var tasks: MutableList<TaskItem> = mutableListOf(TaskItem("test1"),
            TaskItem("test2"),
            TaskItem("test3"))

    fun attach() {
        view.render(tasks)
    }

    fun addTask(task: TaskItem) {
        tasks.add(task)
        view.render(tasks)
    }
}