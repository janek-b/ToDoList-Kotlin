package com.janek.todolist.ui.tasks

import com.janek.todolist.data.db.TaskItemDao
import com.janek.todolist.data.models.TaskItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class TasksPresenter(private val view: TasksView,
                     private val taskItemDao: TaskItemDao) {
    private val disposable = CompositeDisposable()

    fun attach() {
        disposable.add(
                taskItemDao.getAllTasks()
                        .toObservable()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { view.render(it) }
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
        taskItemDao.insertTask(TaskItem(""))
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