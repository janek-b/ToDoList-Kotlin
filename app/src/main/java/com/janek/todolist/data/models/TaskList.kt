package com.janek.todolist.data.models

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "task_list")
data class TaskList(@ColumnInfo(name = "list_name") var name: String) {
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true) val id: Long = 0
}