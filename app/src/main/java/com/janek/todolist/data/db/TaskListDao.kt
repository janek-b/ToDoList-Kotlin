package com.janek.todolist.data.db

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.janek.todolist.data.models.TaskList
import io.reactivex.Flowable

@Dao interface TaskListDao {

    @Query("select * from task_list")
    fun getAllTaskLists(): Flowable<List<TaskList>>

    @Query("select * from task_list where id = :id")
    fun getTaskList(id: Long): TaskList

    @Insert(onConflict = REPLACE)
    fun insert(taskList: TaskList)

    @Update(onConflict = REPLACE)
    fun update(taskList: TaskList)

    @Delete
    fun delete(taskList: TaskList)


}