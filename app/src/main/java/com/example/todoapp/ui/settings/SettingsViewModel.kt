package com.example.todoapp.ui.settings


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.managers.TodoAlarmManager
import com.example.todoapp.repositories.TodoRepository
import com.example.todoapp.repositories.TodoRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val todoRepository: TodoRepository,
    private val alarmManager: TodoAlarmManager
) : ViewModel() {

    fun deleteTodo() {
        viewModelScope.launch {
            todoRepository.deleteAllTodo()
        }
    }

    fun clearNotifications() = viewModelScope.launch {
        todoRepository.getIDs().forEach {
            alarmManager.cancelAlarm(it)
        }
    }
}
