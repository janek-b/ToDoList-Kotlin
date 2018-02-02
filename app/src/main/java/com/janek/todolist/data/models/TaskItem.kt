package com.janek.todolist.data.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import com.janek.todolist.commons.adapter.AdapterConstants
import com.janek.todolist.commons.adapter.ViewType

@Entity(tableName = "task_items")
data class TaskItem(@ColumnInfo(name = "task_text") var text: String,
                    @ColumnInfo(name = "list_id") var listId: Long,
                    @ColumnInfo(name = "task_done") var done: Boolean = false) : ViewType {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) var id: Long = 0

    override fun getViewType(): Int = AdapterConstants.TASK
}