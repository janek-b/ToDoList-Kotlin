package com.janek.todolist.data.models

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Relation

data class ListAndTasks(@Embedded var list: TaskList) {
    @Relation(parentColumn = "id", entityColumn = "list_id", entity = TaskItem::class)
    var tasks: List<TaskItem> = ArrayList()
}