package com.janek.todolist.ui.tasks

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.janek.todolist.R
import com.janek.todolist.commons.db.AppDatabase
import com.janek.todolist.commons.models.TaskItem
import com.janek.todolist.ui.tasks.adapter.TaskAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

import kotlinx.android.synthetic.main.activity_tasks.*

class TasksActivity : AppCompatActivity(), TasksView {

    private lateinit var presenter: TasksPresenter
    private lateinit var taskAdapter: TaskAdapter
    private val actions: PublishSubject<TaskViewAction> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        presenter = TasksPresenter(this,
                Room.databaseBuilder(applicationContext, AppDatabase::class.java, "todo-list").allowMainThreadQueries().build().taskItemDao())

        taskAdapter = TaskAdapter(
                { actions.onNext(TaskViewAction.Add) },
                { task, complete -> actions.onNext(TaskViewAction.Complete(task, complete)) },
                { task, text -> actions.onNext(TaskViewAction.Edit(task, text)) },
                { task -> actions.onNext(TaskViewAction.Delete(task)) }
        )
//        taskAdapter = TaskAdapter(
//                { presenter.addTask() },
//                { task, complete -> presenter.completeTask(task, complete) },
//                { task, text -> presenter.editTask(task, text) },
//                { task -> presenter.deleteTask(task) }
//        )

        task_list.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = taskAdapter
        }

        presenter.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun render(tasks: List<TaskItem>) {
        taskAdapter.setTasks(tasks)
    }

    override fun viewActions(): Observable<TaskViewAction> {
        return actions
    }
}
