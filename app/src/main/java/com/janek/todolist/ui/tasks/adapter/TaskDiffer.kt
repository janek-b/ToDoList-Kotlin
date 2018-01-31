package com.janek.todolist.ui.tasks.adapter

import android.support.v7.util.DiffUtil
import com.janek.todolist.commons.adapter.ViewType
import com.janek.todolist.commons.models.TaskItem

class TaskDiffer(
        private val oldList: List<ViewType>,
        private val newList: List<ViewType>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if (oldItem is TaskItem && newItem is TaskItem) {
            oldItem.id == newItem.id
        } else {
            oldItem.getViewType() == newItem.getViewType()
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if (oldItem is TaskItem && newItem is TaskItem) {
            oldItem.text == newItem.text && oldItem.done == newItem.done
        } else {
            oldItem == newItem
        }
    }

}