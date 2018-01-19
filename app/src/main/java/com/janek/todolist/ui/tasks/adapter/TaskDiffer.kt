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
        return oldList[oldItemPosition].getViewType() == newList[newItemPosition].getViewType() &&
                (oldList[oldItemPosition] as TaskItem).id == (newList[newItemPosition] as TaskItem).id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition] as TaskItem) == (newList[newItemPosition] as TaskItem)
    }

}