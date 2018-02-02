package com.janek.todolist.ui.tasks.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.janek.todolist.R
import com.janek.todolist.commons.adapter.TaskListHeader
import com.janek.todolist.commons.adapter.ViewType
import com.janek.todolist.commons.adapter.ViewTypeDelegateAdapter
import com.janek.todolist.commons.extensions.inflate

class ListHeaderDelegateAdapter : ViewTypeDelegateAdapter {
    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ListHeaderViewHolder(parent.inflate(R.layout.task_list_header))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as ListHeaderViewHolder).bind(item as TaskListHeader)
    }

    class ListHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val taskListName: EditText = itemView.findViewById(R.id.task_list_name)

        fun bind(listHeader: TaskListHeader) {
            taskListName.setText(listHeader.listName)
        }
    }
}