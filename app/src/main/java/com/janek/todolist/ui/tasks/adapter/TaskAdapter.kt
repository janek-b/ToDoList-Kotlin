package com.janek.todolist.ui.tasks.adapter

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.janek.todolist.commons.adapter.AdapterConstants
import com.janek.todolist.commons.adapter.ViewType
import com.janek.todolist.commons.adapter.ViewTypeDelegateAdapter

class TaskAdapter(val listener: NewTaskDelegateAdapter.onTaskAddedListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: ArrayList<ViewType>
    private var delegateAdapters = SparseArrayCompat<ViewTypeDelegateAdapter>()
    private val newTask = object : ViewType {
        override fun getViewType(): Int = AdapterConstants.NEW
    }

    init {
        delegateAdapters.put(AdapterConstants.NEW, NewTaskDelegateAdapter(listener))
        delegateAdapters.put(AdapterConstants.TASK, TaskDelegateAdapter())
        items = ArrayList()
        items.add(newTask)
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
        items.clear()
        items.addAll(newTasks)
        items.add(newTask)
        // TODO add better way of updating data set - diffUtil
        notifyDataSetChanged()
    }
}