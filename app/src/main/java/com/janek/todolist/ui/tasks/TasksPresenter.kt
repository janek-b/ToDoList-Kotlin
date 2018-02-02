package com.janek.todolist.ui.tasks

import android.util.Log
import com.janek.todolist.data.db.TaskItemDao
import com.janek.todolist.data.db.TaskListDao
import com.janek.todolist.data.models.TaskItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class TasksPresenter(private val view: TasksView,
                     private val listId: Long,
                     private val taskItemDao: TaskItemDao,
                     private val taskListDao: TaskListDao) {
    private val disposable = CompositeDisposable()

    fun attach() {
        val taskList = taskListDao.getTaskList(listId)
        disposable.add(
                taskItemDao.getAllTasksInList(listId)
                        .toObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { view.render(taskList.name, it) }
        )

        disposable.add(
                view.viewActions()
                        .subscribe {
                            when(it) {
                                TaskViewAction.Add -> addTask()
                                is TaskViewAction.Complete -> completeTask(it.task, it.complete)
                                is TaskViewAction.Edit -> editTask(it.task, it.text)
                                is TaskViewAction.Delete -> deleteTask(it.task)
                            }
                        }
        )
    }

    fun detach() {
        disposable.clear()
    }

    private fun addTask() {
        taskItemDao.insertTask(TaskItem("", listId))
    }

    private fun completeTask(task: TaskItem, complete: Boolean) {
        task.done = complete
        taskItemDao.updateTask(task)
    }

    private fun editTask(task: TaskItem, newText: String) {
        task.text = newText
        taskItemDao.updateTask(task)
    }

    private fun deleteTask(task: TaskItem) {
        taskItemDao.deleteTask(task)
    }
}