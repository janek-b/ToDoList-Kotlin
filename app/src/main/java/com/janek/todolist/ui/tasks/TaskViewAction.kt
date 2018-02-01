package com.janek.todolist.ui.tasks

import com.janek.todolist.data.models.TaskItem

/**
 * Created by janek on 1/28/18.
 */
sealed class TaskViewAction {
    object Add : TaskViewAction()
    data class Complete(val task: TaskItem, val complete: Boolean) : TaskViewAction()
    data class Edit(val task: TaskItem, val text: String) : TaskViewAction()
    data class Delete(val task: TaskItem) : TaskViewAction()
}