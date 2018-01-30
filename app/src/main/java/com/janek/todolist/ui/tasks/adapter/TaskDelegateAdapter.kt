package com.janek.todolist.ui.tasks.adapter

import android.content.Context
import android.graphics.Paint
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import com.janek.todolist.R
import com.janek.todolist.commons.adapter.ViewType
import com.janek.todolist.commons.adapter.ViewTypeDelegateAdapter
import com.janek.todolist.commons.extensions.inflate
import com.janek.todolist.commons.models.TaskItem
import com.janek.todolist.ui.tasks.TaskViewAction

class TaskDelegateAdapter(private val action: (TaskViewAction) -> Unit) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TaskViewHolder(parent.inflate(R.layout.task_list_item), action)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as TaskViewHolder).bind(item as TaskItem)
    }

    class TaskViewHolder(
            itemView: View,
            private val action: (TaskViewAction) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val checkBox: CheckBox = itemView.findViewById(R.id.task_checkbox)
        private val taskEdit: EditText = itemView.findViewById(R.id.task_edit_input)
        private val taskDelete: ImageButton = itemView.findViewById(R.id.task_delete)
        private var taskItem: TaskItem? = null

        init {
            checkBox.setOnCheckedChangeListener { _, checked ->
                action(TaskViewAction.Complete(taskItem!!, checked))
                taskEdit.paintFlags = taskEdit.paintFlags xor Paint.STRIKE_THRU_TEXT_FLAG
            }

            taskDelete.setOnClickListener {
                action(TaskViewAction.Delete(taskItem!!))
            }

//            taskEdit.imeOptions = EditorInfo.IME_ACTION_DONE
            taskEdit.setRawInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)

            taskEdit.setOnEditorActionListener { _, action, _ ->
                when(action) {
                    EditorInfo.IME_ACTION_DONE -> {
                        updateTask()
                        true
                    }
                    else -> false
                }
            }

            taskEdit.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    updateTask()
                }
            }

            // on keyboard close submit edit

        }

        fun bind(task: TaskItem) {
            taskItem = task
            taskEdit.setText(task.text)
            checkBox.isChecked = task.done

            if (task.text.isEmpty()) {
                showKeyboard()
            }
        }

        private fun updateTask() {
            val updatedText = taskEdit.text.toString()
            action(TaskViewAction.Edit(taskItem!!, updatedText))
            hideKeyboard()
        }

        private fun showKeyboard() {
            taskEdit.requestFocus()
            (taskEdit.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        private fun hideKeyboard() {
            taskEdit.clearFocus()
            (taskEdit.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(taskEdit.windowToken, 0)
        }
    }
}