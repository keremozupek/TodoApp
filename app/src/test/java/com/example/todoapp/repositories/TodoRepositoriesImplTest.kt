package com.example.todoapp.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.todoapp.room.TodoDao
import com.example.todoapp.UnitTestCoroutineRule
import com.example.todoapp.room.TodoEntity
import io.mockk.*
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.sql.Date

@ExperimentalCoroutinesApi
class TodoRepositoriesImplTest {

    private lateinit var todoRepositoryImpl :TodoRepositoryImpl
    private val todoDao: TodoDao = mockk(relaxed = true)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = UnitTestCoroutineRule()

    @Before
    fun setup(){
        todoRepositoryImpl = spyk (TodoRepositoryImpl(todoDao))
    }

    @Test
    fun insertTodo() = runBlockingTest {
        val todoEntity = TodoEntity(0, "Test", Date(100), 5, false)
        todoRepositoryImpl.insertTodo("Test",Date(100),5)
        coVerify(exactly = 1) { todoDao.insertTodo(todoEntity) }
    }

    @Test
    fun deleteAllTodo() = runBlockingTest {
        todoRepositoryImpl.deleteAllTodo()
        coVerify(exactly = 1) { todoDao.deleteAllTodo() }
    }

    @Test
    fun getTodosOrderByPriority() = runBlockingTest {
        todoRepositoryImpl.getTodosOrderByPriority()
        coVerify(exactly = 1) { todoDao.getTodosOrderByPriority() }
    }

    @Test
    fun getTodosOrderByDate() = runBlockingTest {
        todoRepositoryImpl.getTodosOrderByDate()
        coVerify(exactly = 1) { todoDao.getTodosOrderByDate() }
    }

    @Test
    fun getTodosOrderByTitle() = runBlockingTest {
        todoRepositoryImpl.getTodosOrderByTitle()
        coVerify(exactly = 1) { todoDao.getTodosOrderByTitle() }
    }

    @Test
    fun updateTodo() = runBlockingTest {
        val todoEntity = TodoEntity(0, "Test", Date(100), 5, false)
        todoRepositoryImpl.updateTodo(todoEntity)
        coVerify(exactly = 1) { todoDao.updateTodo(todoEntity) }
    }

    @Test
    fun getID() = runBlockingTest {
        todoRepositoryImpl.getIDs()
        coVerify(exactly = 1) { todoDao.getIDs() }
    }
}