package com.janek.todolist.ui.tasks.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.janek.todolist.commons.adapter.*
import com.janek.todolist.data.models.TaskItem
import com.janek.todolist.ui.tasks.TaskViewAction

class TaskAdapter(action: (TaskViewAction) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<TaskItem>
    private var renderList: List<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private var checkedExpanded = true

    private val newTask = object : ViewType {
        override fun getViewType(): Int = AdapterConstants.NEW
    }

    private var listHeader = TaskListHeader()

    init {
        delegateAdapters.put(AdapterConstants.NEW, NewTaskDelegateAdapter(action))
        delegateAdapters.put(AdapterConstants.TASK, TaskDelegateAdapter(action))
        delegateAdapters.put(AdapterConstants.CHECKEDHEADER, CheckedTaskHeaderDelegateAdapter({
            expand -> toggleExpand(expand)
        }))
        delegateAdapters.put(AdapterConstants.LISTHEADER, ListHeaderDelegateAdapter())
        items = emptyList()
        renderList = emptyList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegateAdapters.get(viewType).onCreateViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegateAdapters.get(getItemViewType(position)).onBindViewHolder(holder, renderList[position])
    }

    override fun getItemCount(): Int = renderList.size

    override fun getItemViewType(position: Int): Int = renderList[position].getViewType()

    fun setTasks(listName: String, newTasks: List<TaskItem>) {
        items = newTasks
        if (listName != listHeader.listName) {
            listHeader = TaskListHeader(listName)
        }
        render()
    }

    private fun render() {
        val pendingItems = items.filter { !it.done }
        val checkedHeader = if (items.size > pendingItems.size) {
            CheckedTaskHeader(items.size - pendingItems.size, checkedExpanded)
        } else {
            null
        }

        val itemsToRender = if (checkedExpanded) items else pendingItems

        val newRenderList = listOfNotNull(*itemsToRender.toTypedArray(), newTask, checkedHeader, listHeader).sortedWith(Compare)
        val diff = DiffUtil.calculateDiff(TaskDiffer(renderList, newRenderList))
        renderList = newRenderList
        diff.dispatchUpdatesTo(this)
    }

    private fun toggleExpand(expand: Boolean) {
        checkedExpanded = expand
        render()
    }

    object Compare : Comparator<ViewType> {
        override fun compare(a: ViewType, b: ViewType): Int {
            if (a is TaskListHeader) {
                return -1
            } else if (b is TaskListHeader) {
                return 1
            } else if (a is TaskItem && b is TaskItem) {
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