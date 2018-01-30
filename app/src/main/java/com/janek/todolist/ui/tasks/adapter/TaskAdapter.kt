package com.janek.todolist.ui.tasks.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.janek.todolist.commons.adapter.AdapterConstants
import com.janek.todolist.commons.adapter.ViewType
import com.janek.todolist.commons.adapter.ViewTypeDelegateAdapter
import com.janek.todolist.commons.models.TaskItem
import com.janek.todolist.ui.tasks.TaskViewAction

class TaskAdapter(action: (TaskViewAction) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val newTask = object : ViewType {
        override fun getViewType(): Int = AdapterConstants.NEW
    }

    init {
        delegateAdapters.put(AdapterConstants.NEW, NewTaskDelegateAdapter(action))
        delegateAdapters.put(AdapterConstants.TASK, TaskDelegateAdapter(action))
        items = emptyList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, items[position])
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int = items[position].getViewType()

    fun setTasks(newTasks: List<ViewType>) {
        val newItems = listOf(*newTasks.toTypedArray(), newTask).sortedWith(Compare)
        val diff = DiffUtil.calculateDiff(TaskDiffer(items, newItems))
        items = newItems
        diff.dispatchUpdatesTo(this)
    }

    object Compare : Comparator<ViewType> {
        override fun compare(a: ViewType, b: ViewType): Int {
            if (a is TaskItem && b is TaskItem) {
                return if (!a.done && b.done) {
                    -1
                } else if (a.done && !b.done) {
                    1
                } else {
                    0
                }
            } else if (a is TaskItem) {
                return if (a.done) {
                    1
                } else {
                    -1
                }
            } else if (b is TaskItem) {
                return if (b.done) {
                    -1
                } else {
                    1
                }
            } else {
                return 0
            }
        }

    }
}