package com.janek.todolist.ui.tasks

import com.janek.todolist.commons.models.TaskItem
import io.reactivex.Observable

interface TasksView {
    fun render(tasks: List<TaskItem>)
    fun viewActions(): Observable<TaskViewAction>
}