package com.janek.todolist.ui.tasks

import com.janek.todolist.commons.adapter.AdapterConstants
import com.janek.todolist.commons.db.TaskItemDao
import com.janek.todolist.commons.models.TaskItem
import io.reactivex.android.schedulers.AndroidSchedulers

class TasksPresenter(private val view: TasksView,
                     private val taskItemDao: TaskItemDao) {

    fun attach() {
        taskItemDao.getAllTasks()
                .toObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.render(it) }
    }

    fun addTask(task: TaskItem) {
        taskItemDao.insertTask(task)
    }

    fun completeTask(task: TaskItem) {
        task.done = !task.done
        taskItemDao.updateTask(task)
    }

    fun editTaskStart(task: TaskItem) {
        task.type = AdapterConstants.EDIT
        taskItemDao.updateTask(task)
    }

    fun editTaskEnd(task: TaskItem, newText: String) {
        task.text = newText
        task.type = AdapterConstants.TASK
        taskItemDao.updateTask(task)
    }
}