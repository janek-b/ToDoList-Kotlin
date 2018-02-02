package com.janek.todolist.ui.tasks

import com.janek.todolist.data.models.TaskItem
import com.janek.todolist.data.models.TaskList
import io.reactivex.Observable

interface TasksView {
    fun render(list: TaskList, tasks: List<TaskItem>)
    fun viewActions(): Observable<TaskViewAction>
}