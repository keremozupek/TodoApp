package com.example.todoapp.ui.home.floatingbutton

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.todoapp.UnitTestCoroutineRule
import com.example.todoapp.managers.TodoAlarmManager
import com.example.todoapp.repositories.TodoRepository
import com.example.todoapp.room.TodoEntity
import com.example.todoapp.ui.home.HomeViewModel
import io.mockk.*
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.sql.Date

@ExperimentalCoroutinesApi
class AddBottomSheetViewModelTest {

    private lateinit var viewModel: AddBottomSheetViewModel
    private val todoRepository: TodoRepository = mockk()
    private val alarmManager: TodoAlarmManager = mockk(relaxed = true)
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = UnitTestCoroutineRule()

    @Before
    fun setup() {
        savedStateHandle.set("id",1)
        viewModel = spyk(AddBottomSheetViewModel(todoRepository,alarmManager,savedStateHandle)){
            every { viewModelScope } returns TestCoroutineScope(TestCoroutineDispatcher())
        }
    }

    @Test
    fun `Given three parameters, when insertTodo, then insertTodo`(){
        val title = "test"
        val date = Date(100)
        val priority = 5
        viewModel.insertTodo(title,date,priority)
        coVerify(exactly = 1) { todoRepository.insertTodo(title,date,priority) }
    }

    @Test
    fun `Given title and alarmTime,when setAlarm, then setAlarm`(){
        val title = "test"
        val alarmTime : Long = 1000
        val argID: Int = savedStateHandle.get<Int>("id") ?: 0
        viewModel.setAlarm(title,alarmTime)
        verify(exactly = 1) { alarmManager.setAlarm(title,argID,alarmTime) }
    }



}