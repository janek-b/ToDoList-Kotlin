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

    fun completeTask(task: TaskItem, complete: Boolean) {
        task.done = complete
        taskItemDao.updateTask(task)
    }

    fun editTask(task: TaskItem, newText: String) {
        task.text = newText
        taskItemDao.updateTask(task)
    }
}