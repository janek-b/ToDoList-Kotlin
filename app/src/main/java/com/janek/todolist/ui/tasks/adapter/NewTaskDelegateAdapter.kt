package com.janek.todolist.ui.tasks.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.janek.todolist.R
import com.janek.todolist.commons.adapter.ViewType
import com.janek.todolist.commons.adapter.ViewTypeDelegateAdapter
import com.janek.todolist.commons.extensions.inflate
import com.janek.todolist.commons.models.TaskItem

class NewTaskDelegateAdapter(private val onTaskAdd: (TaskItem) -> Unit) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return NewTaskViewHolder(parent.inflate(R.layout.task_list_new_item), onTaskAdd)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {

    }

    class NewTaskViewHolder(
            itemView: View,
            private val onTaskAdd: (TaskItem) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val newTaskText: TextView = itemView.findViewById(R.id.task_new_text)
        private val newTaskImage: ImageView = itemView.findViewById(R.id.task_new_image)

        init {
            newTaskText.setOnClickListener { onTaskAdd(TaskItem("")) }
            newTaskImage.setOnClickListener { onTaskAdd(TaskItem("")) }
        }

    }

}