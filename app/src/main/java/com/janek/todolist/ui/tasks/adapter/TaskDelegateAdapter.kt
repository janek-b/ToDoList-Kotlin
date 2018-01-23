package com.janek.todolist.ui.tasks.adapter

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.janek.todolist.R
import com.janek.todolist.commons.adapter.ViewType
import com.janek.todolist.commons.adapter.ViewTypeDelegateAdapter
import com.janek.todolist.commons.extensions.inflate
import com.janek.todolist.commons.models.TaskItem

class TaskDelegateAdapter(
        private val onTaskComplete: (TaskItem, Boolean) -> Unit,
        private val onTaskEdit: (TaskItem, String) -> Unit,
        private val onTaskDelete: (TaskItem) -> Unit
) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TaskViewHolder(parent.inflate(R.layout.task_list_item), onTaskComplete, onTaskEdit, onTaskDelete)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as TaskViewHolder).bind(item as TaskItem)
    }

    class TaskViewHolder(
            itemView: View,
            private val onTaskComplete: (TaskItem, Boolean) -> Unit,
            private val onTaskEdit: (TaskItem, String) -> Unit,
            private val onTaskDelete: (TaskItem) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val checkBox: CheckBox = itemView.findViewById(R.id.task_checkbox)
        private val taskText: TextView = itemView.findViewById(R.id.task_text)
        private val taskEdit: EditText = itemView.findViewById(R.id.task_edit_input)
        private val taskDelete: ImageButton = itemView.findViewById(R.id.task_delete)
        private var taskItem: TaskItem? = null

        init {
            checkBox.setOnCheckedChangeListener { _, checked ->
                onTaskComplete(taskItem!!, checked)

                if (checked && checked == taskItem?.done) {
                    taskText.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    taskText.paintFlags = 0
                }
            }

            taskText.setOnClickListener { startEdit() }

            taskDelete.setOnClickListener { onTaskDelete(taskItem!!) }

            taskEdit.setOnEditorActionListener { _, action, _ ->
                when(action) {
                    EditorInfo.IME_ACTION_DONE -> {
                        endEdit()
                        true
                    }
                    else -> false
                }
            }

            taskEdit.setOnFocusChangeListener { view, hasFocus ->
                if (!hasFocus) {
                    endEdit()
                }
            }

        }

        fun bind(task: TaskItem) {
            taskItem = task
            taskText.text = task.text
            checkBox.isChecked = task.done

            if (task.text.isEmpty()) {
                startEdit()
            }
        }

        private fun startEdit() {
            taskText.visibility = View.INVISIBLE
            taskEdit.visibility = View.VISIBLE

            taskEdit.setText(taskItem?.text)
            taskEdit.requestFocus()
            taskEdit.setSelection(taskItem!!.text.length)
            (taskEdit.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        private fun endEdit() {
            val updatedText = taskEdit.text.toString()
            taskText.text = updatedText
            taskText.visibility = View.VISIBLE
            taskEdit.visibility = View.INVISIBLE

            onTaskEdit(taskItem!!, updatedText)
            (taskEdit.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(taskEdit.windowToken, 0)
        }
    }
}