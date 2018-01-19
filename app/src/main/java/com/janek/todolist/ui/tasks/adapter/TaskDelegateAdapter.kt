package com.janek.todolist.ui.tasks.adapter

import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.janek.todolist.R
import com.janek.todolist.commons.adapter.ViewType
import com.janek.todolist.commons.adapter.ViewTypeDelegateAdapter
import com.janek.todolist.commons.extensions.inflate
import com.janek.todolist.commons.models.TaskItem

class TaskDelegateAdapter(
        private val onTaskComplete: (TaskItem) -> Unit,
        private val onTaskEditStart: (TaskItem) -> Unit
) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TaskViewHolder(parent.inflate(R.layout.task_list_item), onTaskComplete, onTaskEditStart)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as TaskViewHolder).bind(item as TaskItem)
    }

    class TaskViewHolder(
            itemView: View,
            private val onTaskComplete: (TaskItem) -> Unit,
            private val onTaskEditStart: (TaskItem) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val checkBox: CheckBox = itemView.findViewById(R.id.task_checkbox)
        private val taskText: TextView = itemView.findViewById(R.id.task_text)
        private var taskItem: TaskItem? = null

        init {
            checkBox.setOnCheckedChangeListener { _, checked ->
                onTaskComplete(taskItem!!)
                if (checked) {
                    taskText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    taskText.paintFlags = 0
                }
            }
            taskText.setOnClickListener { onTaskEditStart(taskItem!!) }
        }

        fun bind(task: TaskItem) {
            taskItem = task
            taskText.text = task.text
            checkBox.isChecked = task.done
        }
    }
}