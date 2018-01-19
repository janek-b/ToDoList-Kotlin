package com.janek.todolist.ui.tasks

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.janek.todolist.R
import com.janek.todolist.commons.models.TaskItem
import com.janek.todolist.ui.tasks.adapter.TaskAdapter

import kotlinx.android.synthetic.main.activity_tasks.*

class TasksActivity : AppCompatActivity(), TasksView {

    private var presenter = TasksPresenter(this)

    private val taskAdapter = TaskAdapter({ task ->
        presenter.addTask(task)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

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
