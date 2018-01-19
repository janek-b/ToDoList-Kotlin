package com.janek.todolist.ui.tasks

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.janek.todolist.R
import com.janek.todolist.commons.db.AppDatabase
import com.janek.todolist.commons.models.TaskItem
import com.janek.todolist.ui.tasks.adapter.TaskAdapter

import kotlinx.android.synthetic.main.activity_tasks.*

class TasksActivity : AppCompatActivity(), TasksView {

    private lateinit var presenter: TasksPresenter
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        presenter = TasksPresenter(this,
                Room.databaseBuilder(applicationContext, AppDatabase::class.java, "todo-list").allowMainThreadQueries().build().taskItemDao())

        taskAdapter = TaskAdapter(
                { task -> presenter.addTask(task) },
                { task -> presenter.completeTask(task) },
                { task -> presenter.editTaskStart(task) },
                { task, text -> presenter.editTaskEnd(task, text)}
        )

        task_list.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = taskAdapter
        }

        presenter.attach()
    }

    override fun render(tasks: List<TaskItem>) {
        taskAdapter.setTasks(tasks)
    }

}
