package com.janek.todolist.ui.tasks

import com.janek.todolist.data.models.TaskItem
import io.reactivex.Observable

interface TasksView {
    fun render(tasks: List<TaskItem>)
    fun viewActions(): Observable<TaskViewAction>
}