package com.janek.todolist

import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.janek.todolist.data.db.AppDatabase
import com.janek.todolist.data.models.TaskList
import com.janek.todolist.ui.tasks.TasksActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val taskListDao = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "todo-list").allowMainThreadQueries().build().taskListDao()

        val taskListAdapter = TaskListAdapter(
                { taskList -> startActivity(TasksActivity.createIntent(this, taskList.id)) }
        )

        task_list_recyclerview.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = taskListAdapter
        }

        taskListDao.getAllTaskLists()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { taskListAdapter.setItems(it) }

        new_list_button.setOnClickListener {
            val newListName = new_list_edit_text.text.toString()
            new_list_edit_text.text.clear()
            taskListDao.insert(TaskList(newListName))
        }
    }
}
