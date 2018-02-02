package com.janek.todolist.commons.adapter

/**
 * Created by janek on 2/1/18.
 */
data class TaskListHeader(val listName: String = "") : ViewType {
    override fun getViewType(): Int = AdapterConstants.LISTHEADER
}