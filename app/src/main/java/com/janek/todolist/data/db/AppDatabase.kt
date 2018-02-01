package com.janek.todolist.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.janek.todolist.data.models.TaskItem
import com.janek.todolist.data.models.TaskList

@Database(entities = arrayOf(TaskItem::class, TaskList::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskItemDao(): TaskItemDao
    abstract fun taskListDao(): TaskListDao
}