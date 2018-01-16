package com.janek.todolist.ui.tasks

/**
 * Created by janek on 1/16/18.
 */

class TasksPresenter(private val view: TasksView) {

    private var tasks: MutableList<String> = mutableListOf("test1", "test2", "test3")

    fun attach() {
        view.render(tasks)
    }

    fun addTask(task: String) {
        tasks.add(task)
        view.render(tasks)
    }
}