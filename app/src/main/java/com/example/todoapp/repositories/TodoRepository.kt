package com.example.todoapp.repositories

import com.example.todoapp.room.TodoDao
import com.example.todoapp.room.TodoEntity
import kotlinx.coroutines.flow.Flow
import java.sql.Date
import javax.inject.Inject

class TodoRepository
@Inject constructor(
    private val todoDao: TodoDao

) {

    val readAllData: Flow<List<TodoEntity>> = todoDao.readAllData()

    suspend fun addTodo(title: String, date: Date, priority: Int) {
        todoDao.addTodo(TodoEntity(0, title, date, priority, false))
    }

    suspend fun deleteAllTodo() {
        todoDao.deleteAllTodo()
    }

    fun getTodosOrderByPriority(): Flow<List<TodoEntity>> {
        return todoDao.getTodosOrderByPriority()
    }

    fun getTodosOrderByDate(): Flow<List<TodoEntity>> {
        return todoDao.getTodosOrderByDate()
    }

    fun getTodosOrderByTitle(): Flow<List<TodoEntity>> {
        return todoDao.getTodosOrderByTitle()
    }

    suspend fun updateTodo(todoModel: TodoEntity) {
        todoDao.updateTodo(todoModel)
    }

    suspend fun getID(): List<Int> {
        return todoDao.getIDs()
    }
}
