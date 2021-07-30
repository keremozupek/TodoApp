package com.example.todoapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.todoapp.room.TodoEntity
import com.example.todoapp.repositories.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class OrderState {
    TITLE, PRIORITY, DATE, ID
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    private val orderStateFlow: MutableStateFlow<OrderState> = MutableStateFlow(OrderState.ID)
    val items = orderStateFlow.flatMapLatest {
        when(it) {
            OrderState.TITLE -> todoRepository.getTodosOrderByTitle()
            OrderState.PRIORITY -> todoRepository.getTodosOrderByPriority()
            OrderState.DATE -> todoRepository.getTodosOrderByDate()
            OrderState.ID -> todoRepository.readAllData
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    var getNavController: (() -> NavController)? = null

    fun setOrderStateByIndex(index: Int) {
        when (index) {
            0 -> {
                orderStateFlow.value = OrderState.TITLE
            }
            1 -> {
                orderStateFlow.value = OrderState.PRIORITY
            }
            else -> {
                orderStateFlow.value = OrderState.DATE
            }
        }
    }

    fun onAddButtonClicked() {
        if (items.value.isEmpty()) {
            val action = HomeFragmentDirections.actionHomeFragmentToAddBottomSheetFragment(0)
            getNavController?.invoke()?.navigate(action)
        } else {
            val action = HomeFragmentDirections.actionHomeFragmentToAddBottomSheetFragment(items.value.map { it.id }.maxOf { it })
            getNavController?.invoke()?.navigate(action)
        }
    }

    fun onDoneCheckboxClicked(it: TodoEntity) = viewModelScope.launch {
        todoRepository.updateTodo(it.copy(done = !it.done))
    }
}
