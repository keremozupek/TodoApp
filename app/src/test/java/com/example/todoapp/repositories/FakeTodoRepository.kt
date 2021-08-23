package com.example.todoapp.repositories

import com.example.todoapp.room.TodoEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.sql.Date

class FakeTodoRepository : TodoRepository {

    private val todoList = mutableListOf<TodoEntity>()
    private val todoItems = MutableStateFlow(todoList)
    var id = 1

    override val readAllData: Flow<List<TodoEntity>>
        get() = todoItems

    private fun refreshStateFlow(){
        todoItems.value = todoList
    }

    override suspend fun insertTodo(title: String, date: Date, priority: Int) {
        if (todoList.isNotEmpty()){
            id = todoList.last().id + 1
        }
        todoList.add(TodoEntity(id,title,date,priority,false))
        refreshStateFlow()
    }

    override suspend fun deleteAllTodo() {
        todoList.clear()
        refreshStateFlow()
    }

    override fun getTodosOrderByPriority(): Flow<List<TodoEntity>> {
        TODO("Not yet implemented")
    }

    override fun getTodosOrderByDate(): Flow<List<TodoEntity>> {
        TODO("Not yet implemented")
    }

    override fun getTodosOrderByTitle(): Flow<List<TodoEntity>> {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodo(todoModel: TodoEntity) {
        todoList[todoModel.id] = todoModel
        refreshStateFlow()
    }

    override suspend fun getIDs(): List<Int> {
        val idList = mutableListOf<Int>()
        todoList.forEach {
            idList.add(it.id)
        }
        return idList
    }
}