package com.janek.todolist.ui.tasks.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
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
            task_text_input.setOnEditorActionListener { _, action, _ ->
                when(action) {
                    EditorInfo.IME_ACTION_DONE -> {
                        listener.onTaskAdd(task_text_input.text.toString())
                        task_text_input.text.clear()
                        true
                    }
                    else -> false
                }
            }

            task_text_input.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    listener.onTaskAdd(task_text_input.text.toString())
                    task_text_input.text.clear()
                }
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {

    }

    class NewTaskViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(parent.inflate(R.layout.task_list_new_item))
}