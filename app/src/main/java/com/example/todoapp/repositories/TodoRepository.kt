package com.example.todoapp.repositories

import com.example.todoapp.room.TodoEntity
import kotlinx.coroutines.flow.Flow
import java.sql.Date

interface TodoRepository {

    val readAllData: Flow<List<TodoEntity>>

    suspend fun insertTodo(title: String, date: Date, priority: Int)

    suspend fun deleteAllTodo()

    fun getTodosOrderByPriority() : Flow<List<TodoEntity>>

    fun getTodosOrderByDate() : Flow<List<TodoEntity>>

    fun getTodosOrderByTitle() : Flow<List<TodoEntity>>

    suspend fun updateTodo(todoModel: TodoEntity)

    suspend fun getIDs() : List<Int>
}