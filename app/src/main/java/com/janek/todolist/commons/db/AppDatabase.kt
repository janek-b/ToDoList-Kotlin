package com.janek.todolist.commons.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.janek.todolist.commons.models.TaskItem

@Database(entities = arrayOf(TaskItem::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskItemDao(): TaskItemDao
}