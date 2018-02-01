package com.janek.todolist.ui.tasks

import android.arch.persistence.room.Room
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.janek.todolist.R
import com.janek.todolist.data.db.AppDatabase
import com.janek.todolist.data.models.TaskItem
import com.janek.todolist.ui.tasks.adapter.TaskAdapter
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

import kotlinx.android.synthetic.main.activity_tasks.*

class TasksActivity : AppCompatActivity(), TasksView {

    companion object {
        @JvmStatic fun createIntent(context: Context, listId: Long) : Intent {
            val intent = Intent(context, TasksActivity::class.java)
            intent.putExtra("listId", listId)
            return intent
        }
    }

    private lateinit var presenter: TasksPresenter
    private lateinit var taskAdapter: TaskAdapter
    private val actions: PublishSubject<TaskViewAction> = PublishSubject.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        val listId = intent.getLongExtra("listId", 0)

        presenter = TasksPresenter(this,
                listId,
                Room.databaseBuilder(applicationContext, AppDatabase::class.java, "todo-list").allowMainThreadQueries().build().taskItemDao())

        taskAdapter = TaskAdapter(
                { action -> actions.onNext(action)}
        )

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
