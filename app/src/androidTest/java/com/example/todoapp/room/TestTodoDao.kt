package com.example.todoapp.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.example.todoapp.TestCoroutineRule
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.sql.Date
import javax.inject.Inject
import javax.inject.Named



@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class TestTodoDao {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Inject
    @Named("test_db")
    lateinit var database: TodoDatabase
    private lateinit var todoDao: TodoDao

    @Before
    fun setup() {
        hiltRule.inject()
        todoDao = database.todoDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun addTodo() = testCoroutineRule.testDispatcher.runBlockingTest {
        val todo = TodoEntity(1,"Test", Date(5),1,false)
        todoDao.insertTodo(todo)
        val allTodos = todoDao.readAllData().first()
        assertThat(allTodos).contains(todo)
    }

    @Test
    fun deleteTodo() = testCoroutineRule.testDispatcher.runBlockingTest {
        val todo = TodoEntity(1,"Test", Date(5),1,false)
        todoDao.insertTodo(todo)
        todoDao.deleteTodo(1)
        val allTodos = todoDao.readAllData().first()
        assertThat(allTodos).isEmpty()
    }

    @Test
    fun deleteAllTodo() = testCoroutineRule.testDispatcher.runBlockingTest {
        val todoOne = TodoEntity(1,"Test", Date(5),1,false)
        todoDao.insertTodo(todoOne)
        val todoTwo = TodoEntity(1,"Test", Date(5),1,false)
        todoDao.insertTodo(todoTwo)
        val todoThree = TodoEntity(1,"Test", Date(5),1,false)
        todoDao.insertTodo(todoThree)
        todoDao.deleteAllTodo()
        val allTodos = todoDao.readAllData().first()
        assertThat(allTodos).isEmpty()
    }


    @Test
    fun getOrderByPriority() = testCoroutineRule.testDispatcher.runBlockingTest {
        val todoOne = TodoEntity(1,"Test", Date(5),3,false)
        todoDao.insertTodo(todoOne)
        val todoTwo = TodoEntity(1,"Test", Date(5),5,false)
        todoDao.insertTodo(todoTwo)
        val todoThree = TodoEntity(1,"Test", Date(5),1,false)
        todoDao.insertTodo(todoThree)
        val todos = todoDao.getTodosOrderByPriority().first()
        assertThat(todos).contains(TodoEntity(1,"Test", Date(5),5,false))
    }

    @Test
    fun getOrderByDate() = testCoroutineRule.testDispatcher.runBlockingTest {
        val todoOne = TodoEntity(1,"Test", Date(5),5,false)
        todoDao.insertTodo(todoOne)
        val todoTwo = TodoEntity(1,"Test", Date(7),5,false)
        todoDao.insertTodo(todoTwo)
        val todoThree = TodoEntity(1,"Test", Date(3),5,false)
        todoDao.insertTodo(todoThree)

        val todos = todoDao.getTodosOrderByDate().first()
        assertThat(todos).contains(todoTwo)
    }

    @Test
    fun updateTodo() = testCoroutineRule.testDispatcher.runBlockingTest {
            val todo = TodoEntity(1,"Test", Date(5),1,false)
            todoDao.insertTodo(todo)
            todoDao.updateTodo(todo.copy(title = "Test123"))
            val todos = todoDao.readAllData().first()
            assertThat(todos).doesNotContain(todo)
        }
    }




