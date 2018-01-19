package com.janek.todolist.commons.db

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.janek.todolist.commons.models.TaskItem
import io.reactivex.Flowable

@Dao interface TaskItemDao {
    @Query("select * from task_items")
    fun getAllTasks(): Flowable<List<TaskItem>>

    @Query("select * from task_items where id = :id")
    fun findTaskById(id: Long): TaskItem

    @Insert(onConflict = REPLACE)
    fun insertTask(task: TaskItem)

    @Update(onConflict = REPLACE)
    fun updateTask(task: TaskItem)

    @Delete
    fun deleteTask(task: TaskItem)
}