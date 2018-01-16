package com.janek.todolist.ui.tasks.adapter

import android.support.v7.widget.RecyclerView
import android.view.View

import kotlinx.android.synthetic.main.task_list_item.view.*

class TaskListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(text: String) = with(itemView) {
        task_text.text = text
    }
}