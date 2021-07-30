package com.example.todoapp.ui.home.floatingbutton

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.managers.TodoAlarmManager
import com.example.todoapp.repositories.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.sql.Date
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class AddBottomSheetViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    private val alarmManager: TodoAlarmManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val argID: Int = savedStateHandle.get<Int>("id") ?: 0
    private val timeFormat = SimpleDateFormat("HH:mm")
    fun createTodo(title: String, date: Date, priority: Int) = viewModelScope.launch {
        todoRepository.addTodo(
            title,
            date,
            priority
        )
        timeFormat.format(java.util.Date())
    }

    fun setAlarm(title: String, alarmTime: Long) {
        alarmManager.setAlarm(title, argID, alarmTime)
    }
}
