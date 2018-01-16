package com.janek.todolist.ui.tasks.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.janek.todolist.R
import com.janek.todolist.commons.adapter.ViewType
import com.janek.todolist.commons.adapter.ViewTypeDelegateAdapter
import com.janek.todolist.commons.extensions.inflate

import kotlinx.android.synthetic.main.task_list_new_item.view.*

class NewTaskDelegateAdapter(val listener: onTaskAddedListener) : ViewTypeDelegateAdapter {

    interface onTaskAddedListener {
        fun onTaskAdd(text: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val viewHolder = NewTaskViewHolder(parent)
        with(viewHolder.itemView) {
            task_add_button.setOnClickListener {
                listener.onTaskAdd(task_text_input.text.toString())
                task_text_input.text.clear()
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {

    }

    class NewTaskViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.task_list_new_item))
}