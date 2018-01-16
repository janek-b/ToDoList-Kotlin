package com.janek.todolist.ui.tasks.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.janek.todolist.R

import com.janek.todolist.commons.extensions.inflate

/**
 * Created by janek on 1/16/18.
 */
class TaskAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var tasks: List<String>

    init {
        tasks = listOf("test1", "test2")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = parent.inflate(R.layout.task_list_item)
        return TaskListViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as TaskListViewHolder).bind(tasks[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

}