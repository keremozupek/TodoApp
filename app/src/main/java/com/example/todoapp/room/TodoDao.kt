package com.example.todoapp.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(todo: TodoEntity)

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun readAllData(): Flow<List<TodoEntity>>

    @Query("DELETE FROM todo_table WHERE id = :TodoId ")
    suspend fun deleteTodo(TodoId: Int)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTodo()

    @Query("SELECT * FROM todo_table ORDER BY priority DESC")
    fun getTodosOrderByPriority(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todo_table ORDER BY dueDate ASC")
    fun getTodosOrderByDate(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todo_table ORDER BY title ASC")
    fun getTodosOrderByTitle(): Flow<List<TodoEntity>>

    @Update
    suspend fun updateTodo(todo: TodoEntity)

    @Query("SELECT id FROM todo_table ORDER BY id ASC")
    suspend fun getIDs(): List<Int>
}
