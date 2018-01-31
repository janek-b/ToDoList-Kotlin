package com.janek.todolist.commons.adapter

/**
 * Created by janek on 1/30/18.
 */
data class CheckedTaskHeader(val taskCount: Int) : ViewType {
    override fun getViewType(): Int = AdapterConstants.CHECKEDHEADER
}