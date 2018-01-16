package com.janek.todolist.commons.models

import com.janek.todolist.commons.adapter.AdapterConstants
import com.janek.todolist.commons.adapter.ViewType

data class TaskItem(val text: String) : ViewType {
    override fun getViewType(): Int = AdapterConstants.TASK
}