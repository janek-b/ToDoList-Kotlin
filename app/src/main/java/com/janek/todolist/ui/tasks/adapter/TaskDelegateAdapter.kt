package com.janek.todolist.ui.tasks.adapter

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.CompoundButton
import com.janek.todolist.R
import com.janek.todolist.commons.adapter.ViewType
import com.janek.todolist.commons.adapter.ViewTypeDelegateAdapter
import com.janek.todolist.commons.extensions.inflate
import com.janek.todolist.commons.models.TaskItem

import kotlinx.android.synthetic.main.task_list_item.view.*

class TaskDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val viewHolder = TaskViewHolder(parent)

        with(viewHolder.itemView) {
            task_text.setOnCheckedChangeListener { _, checked ->
                if (checked) {
                    task_text.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    task_text.paintFlags = 0
                }
            }
        }
        
        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as TaskViewHolder).bind(item as TaskItem)
    }

    class TaskViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.task_list_item)) {
        fun bind(task: TaskItem) = with(itemView) {
            task_text.text = task.text
        }
    }
}