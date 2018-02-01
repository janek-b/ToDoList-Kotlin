package com.janek.todolist.data.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.janek.todolist.commons.adapter.AdapterConstants
import com.janek.todolist.commons.adapter.ViewType

@Entity(tableName = "task_items",
        foreignKeys = arrayOf(ForeignKey(entity = TaskList::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("listId"),
                onDelete = ForeignKey.CASCADE)))
data class TaskItem(@ColumnInfo(name = "task_text") var text: String,
                    @ColumnInfo(name = "task_done") var done: Boolean = false,
                    @ColumnInfo(name = "list_id") val listId: Long) : ViewType {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) val id: Long = 0

    override fun getViewType(): Int = AdapterConstants.TASK
}