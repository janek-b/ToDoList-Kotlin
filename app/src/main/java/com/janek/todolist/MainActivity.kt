package com.janek.todolist

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.janek.todolist.data.db.AppDatabase
import com.janek.todolist.data.db.TaskItemDao
import com.janek.todolist.data.models.TaskList
import com.janek.todolist.ui.tasks.TasksActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val disposable = CompositeDisposable()

    private lateinit var taskItemDao: TaskItemDao
    private lateinit var taskListAdapter: TaskListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        taskItemDao = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "todo-list").allowMainThreadQueries().build().taskItemDao()

        taskListAdapter = TaskListAdapter(
                { taskList -> startActivity(TasksActivity.createIntent(this, taskList.id)) }
        )

        task_list_recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = taskListAdapter
        }

        new_list_button.setOnClickListener {
            val newListName = new_list_edit_text.text.toString()
            new_list_edit_text.text.clear()
            taskItemDao.insertList(TaskList(newListName))
        }
    }

    override fun onResume() {
        super.onResume()

        disposable.add(taskItemDao.getAllLists()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { taskListAdapter.setItems(it) }
        )
    }

    override fun onPause() {
        super.onPause()
        disposable.clear()
    }
}
