package com.janek.todolist

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.janek.todolist.commons.extensions.inflate
import com.janek.todolist.data.models.TaskList

class TaskListAdapter(private val openList: (TaskList) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<TaskList>

    init {
        items = emptyList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return TaskListViewHolder(parent.inflate(R.layout.task_lists_item), openList)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        (holder as TaskListViewHolder).bind(items[position])
    }

    fun setItems(newItems: List<TaskList>) {
        items = newItems
        notifyDataSetChanged()
    }

    class TaskListViewHolder(itemView: View,
                             private val openList: (TaskList) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val listName: TextView = itemView.findViewById(R.id.task_list_name)
        private var taskList: TaskList? = null

        init {
            listName.setOnClickListener {
                openList(taskList!!)
            }
        }

        fun bind(list: TaskList) {
            listName.text = list.name
            taskList = list
        }

    }
}