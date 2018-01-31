package com.janek.todolist.ui.tasks.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.janek.todolist.R
import com.janek.todolist.commons.adapter.CheckedTaskHeader
import com.janek.todolist.commons.adapter.ViewType
import com.janek.todolist.commons.adapter.ViewTypeDelegateAdapter
import com.janek.todolist.commons.extensions.inflate

/**
 * Created by janek on 1/30/18.
 */
class CheckedTaskHeaderDelegateAdapter(private val toggleExpand: () -> Unit) : ViewTypeDelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return CheckedHeaderViewHolder(parent.inflate(R.layout.task_list_checked_header), toggleExpand)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: ViewType) {
        (holder as CheckedHeaderViewHolder).bind((item as CheckedTaskHeader).taskCount)
    }

    class CheckedHeaderViewHolder(itemView: View, private val toggleExpand: () -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val headerTextView: TextView = itemView.findViewById(R.id.checked_items_header)
        private val expandImageView: ImageView = itemView.findViewById(R.id.expand_image_view)


        init {
            expandImageView.setOnClickListener { toggleExpand() }
        }

        fun bind(count: Int) {
            headerTextView.text = "$count Checked items"
        }
    }
}