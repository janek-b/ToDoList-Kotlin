package com.janek.todolist.ui.tasks.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.InputType
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.janek.todolist.R
import com.janek.todolist.commons.adapter.TaskListHeader
import com.janek.todolist.commons.adapter.ViewType
import com.janek.todolist.commons.adapter.ViewTypeDelegateAdapter
import com.janek.todolist.commons.extensions.inflate

class ListHeaderDelegateAdapter(private val nameEdit: (String) -> Unit) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return ListHeaderViewHolder(parent.inflate(R.layout.tasks_header), nameEdit)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as ListHeaderViewHolder).bind(item as TaskListHeader)
    }

    class ListHeaderViewHolder(itemView: View, private val nameEdit: (String) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val taskListName: EditText = itemView.findViewById(R.id.task_list_name)

        init {
            taskListName.setRawInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)

            taskListName.setOnEditorActionListener { _, action, _ ->
                when(action) {
                    EditorInfo.IME_ACTION_DONE -> {
                        updateListName()
                        true
                    }
                    else -> false
                }
            }

            taskListName.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    updateListName()
                }
            }
        }

        fun bind(listHeader: TaskListHeader) {
            taskListName.setText(listHeader.listName)

            if (taskListName.text.isEmpty()) {
                showKeyboard()
            }
        }

        private fun updateListName() {
            val updatedText = taskListName.text.toString()
            nameEdit(updatedText)
            hideKeyboard()
        }


        private fun showKeyboard() {
            taskListName.requestFocus()
            (taskListName.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }

        private fun hideKeyboard() {
            taskListName.clearFocus()
            (taskListName.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                    .hideSoftInputFromWindow(taskListName.windowToken, 0)
        }
    }
}