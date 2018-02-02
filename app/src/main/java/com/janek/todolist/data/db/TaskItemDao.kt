package com.janek.todolist.data.db

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import com.janek.todolist.data.models.ListAndTasks
import com.janek.todolist.data.models.TaskItem
import com.janek.todolist.data.models.TaskList
import io.reactivex.Flowable

@Dao interface TaskItemDao {
    @Query("select * from task_items")
    fun getAllTasks(): Flowable<List<TaskItem>>

    @Query("select * from task_items where id = :id")
    fun findTaskById(id: Long): TaskItem

    @Query("select * from task_items where list_id = :listId")
    fun getAllTasksInList(listId: Long): Flowable<List<TaskItem>>

    @Insert(onConflict = REPLACE)
    fun insertTask(task: TaskItem)

    @Update(onConflict = REPLACE)
    fun updateTask(task: TaskItem)

    @Delete
    fun deleteTask(task: TaskItem)


    @Query("select * from task_list")
    fun getAllLists(): Flowable<List<TaskList>>

    @Query("select * from task_list where id = :id")
    fun getList(id: Long): TaskList

    @Transaction
    @Query("SELECT * FROM task_list WHERE id = :id")
    fun getListAndTasks(id: Long): Flowable<ListAndTasks>

    @Insert(onConflict = REPLACE)
    fun insertList(list: TaskList)

    @Update(onConflict = REPLACE)
    fun updateList(list: TaskList)

    @Delete
    fun deleteList(list: TaskList)

}