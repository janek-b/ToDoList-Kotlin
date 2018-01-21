package com.janek.todolist.ui.tasks.adapter

import android.support.v7.widget.RecyclerView

import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.CheckBox
import android.widget.EditText
import com.janek.todolist.R
import com.janek.todolist.commons.adapter.ViewType
import com.janek.todolist.commons.adapter.ViewTypeDelegateAdapter
import com.janek.todolist.commons.extensions.inflate
import com.janek.todolist.commons.models.TaskItem

class EditTaskDelegateAdapter(
        private val onTaskEdit: (TaskItem, String) -> Unit
) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return EditTaskViewHolder(parent.inflate(R.layout.task_list_edit_item), onTaskEdit)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as EditTaskViewHolder).bind(item as TaskItem)
    }

    class EditTaskViewHolder(
            itemView: View,
            private val onTaskEdit: (TaskItem, String) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val checkBox: CheckBox = itemView.findViewById(R.id.task_edit_checkbox)
        private val taskEditText: EditText = itemView.findViewById(R.id.task_edit_input)
        private var taskItem: TaskItem? = null

        init {
            taskEditText.setOnEditorActionListener { _, action, _ ->
                when(action) {
                    EditorInfo.IME_ACTION_DONE -> {
                        onTaskEdit(taskItem!!, taskEditText.text.toString())
                        true
                    }
                    else -> false
                }
            }

            taskEditText.setOnFocusChangeListener { view, hasFocus ->
                if (!hasFocus) {
                    onTaskEdit(taskItem!!, taskEditText.text.toString())
                }
            }

        }

        fun bind(task: TaskItem) {
            taskItem = task
            checkBox.isChecked = task.done
            taskEditText.setText(task.text)
            taskEditText.requestFocus()
            taskEditText.setSelection(task.text.length)

        }
    }

}