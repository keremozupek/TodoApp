package com.example.todoapp.ui.home.settings

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.example.todoapp.UnitTestCoroutineRule
import com.example.todoapp.managers.TodoAlarmManager
import com.example.todoapp.repositories.FakeTodoRepository
import com.example.todoapp.repositories.TodoRepository
import com.example.todoapp.ui.home.HomeViewModel
import com.example.todoapp.ui.settings.SettingsViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.sql.Date

@ExperimentalCoroutinesApi
class SettingViewModelTest {

    private lateinit var viewModel: SettingsViewModel
    private val todoRepository: TodoRepository = mockk()
    private val alarmManager: TodoAlarmManager = mockk(relaxed = true)
    private lateinit var fakeViewModel: SettingsViewModel
    private val fakeTodoRepository: FakeTodoRepository = FakeTodoRepository()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = UnitTestCoroutineRule()

    @Before
    fun setup() {
        viewModel = spyk(SettingsViewModel(todoRepository,alarmManager)){
            every { viewModelScope } returns TestCoroutineScope(TestCoroutineDispatcher())
        }
        fakeViewModel = spyk(SettingsViewModel(fakeTodoRepository,alarmManager)){
            every { viewModelScope } returns TestCoroutineScope(TestCoroutineDispatcher())
        }
    }

    @Test
    fun `when deleteTodo, then deleteAllTodo`(){
        viewModel.deleteTodo()
        coVerify { todoRepository.deleteAllTodo() }
    }

    @Test
    fun `when clearNotification, then cancelAlarm`() = runBlockingTest {
        fakeTodoRepository.insertTodo("Test",Date(100),5)
        fakeViewModel.clearNotifications()
        coVerify { alarmManager.cancelAlarm(1) }
    }

}