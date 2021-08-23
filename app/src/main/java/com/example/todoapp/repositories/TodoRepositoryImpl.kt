package com.example.todoapp.repositories

import com.example.todoapp.room.TodoDao
import com.example.todoapp.room.TodoEntity
import kotlinx.coroutines.flow.Flow
import java.sql.Date
import javax.inject.Inject

class TodoRepositoryImpl
@Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {

    override val readAllData: Flow<List<TodoEntity>> = todoDao.readAllData()

    override suspend fun insertTodo(title: String, date: Date, priority: Int) {
        todoDao.insertTodo(TodoEntity(0, title, date, priority, false))
    }

    override suspend fun deleteAllTodo() {
        todoDao.deleteAllTodo()
    }

    override fun getTodosOrderByPriority(): Flow<List<TodoEntity>> {
        return todoDao.getTodosOrderByPriority()
    }

    override fun getTodosOrderByDate(): Flow<List<TodoEntity>> {
        return todoDao.getTodosOrderByDate()
    }

    override fun getTodosOrderByTitle(): Flow<List<TodoEntity>> {
        return todoDao.getTodosOrderByTitle()
    }

    override suspend fun updateTodo(todoModel: TodoEntity) {
        todoDao.updateTodo(todoModel)
    }

    override suspend fun getIDs(): List<Int> {
        return todoDao.getIDs()
    }
}
