package com.janek.todolist.ui.tasks.adapter

import android.support.v7.util.DiffUtil
import com.janek.todolist.commons.adapter.AdapterConstants
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

        if (oldItem.getViewType() == AdapterConstants.NEW &&
                newItem.getViewType() == AdapterConstants.NEW) {
            return true
        }
        if (oldItem.getViewType() == AdapterConstants.TASK &&
                newItem.getViewType() == AdapterConstants.TASK) {
            return (oldItem as TaskItem).id == (newItem as TaskItem).id
        }
        return oldItem.getViewType() == newItem.getViewType()

//        return oldList[oldItemPosition].getViewType() == newList[newItemPosition].getViewType() &&
//                (oldList[oldItemPosition] as TaskItem).id == (newList[newItemPosition] as TaskItem).id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return if (oldItem.getViewType() == AdapterConstants.NEW &&
                newItem.getViewType() == AdapterConstants.NEW) {
            true
        } else {
            (oldItem as TaskItem).text == (newItem as TaskItem).text &&
                    (oldItem as TaskItem).done == (newItem as TaskItem).done
        }

//        return (oldList[oldItemPosition] as TaskItem) == (newList[newItemPosition] as TaskItem)
    }

}