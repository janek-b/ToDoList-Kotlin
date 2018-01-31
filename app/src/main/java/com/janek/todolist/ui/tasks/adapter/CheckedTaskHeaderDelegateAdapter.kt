package com.janek.todolist.ui.tasks.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import com.janek.todolist.R
import com.janek.todolist.commons.adapter.CheckedTaskHeader
import com.janek.todolist.commons.adapter.ViewType
import com.janek.todolist.commons.adapter.ViewTypeDelegateAdapter
import com.janek.todolist.commons.extensions.inflate

class CheckedTaskHeaderDelegateAdapter(private val toggleExpand: (Boolean) -> Unit) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CheckedHeaderViewHolder(parent.inflate(R.layout.task_list_checked_header), toggleExpand)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as CheckedHeaderViewHolder).bind((item as CheckedTaskHeader))
    }

    class CheckedHeaderViewHolder(itemView: View, private val toggleExpand: (Boolean) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val headerTextView: TextView = itemView.findViewById(R.id.checked_items_header)
        private val expandToggleButton: ToggleButton = itemView.findViewById(R.id.expandToggle)
        private val headerStringResource = itemView.context.getString(R.string.checked_item_count)

        init {
            expandToggleButton.setOnCheckedChangeListener { _, checked ->
                toggleExpand(checked)
            }
        }

        fun bind(header: CheckedTaskHeader) {
            expandToggleButton.isChecked = header.expanded
            headerTextView.text = String.format(headerStringResource, header.taskCount)
        }
    }
}