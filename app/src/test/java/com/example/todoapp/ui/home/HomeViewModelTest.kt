package com.example.todoapp.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.viewModelScope
import com.example.todoapp.UnitTestCoroutineRule
import com.example.todoapp.repositories.FakeTodoRepository
import com.example.todoapp.repositories.TodoRepository
import com.example.todoapp.room.TodoEntity
import io.mockk.*
import io.mockk.coVerify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.sql.Date

@ExperimentalCoroutinesApi
class HomeViewModelTest{

    private lateinit var viewModel: HomeViewModel
    private val todoRepository: TodoRepository = mockk()
    private lateinit var fakeViewModel: HomeViewModel
    private val fakeTodoRepository: FakeTodoRepository = FakeTodoRepository()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val testCoroutineRule = UnitTestCoroutineRule()


    @Before
    fun setup() {
        viewModel = spyk(HomeViewModel(todoRepository)){
            every { viewModelScope } returns TestCoroutineScope(TestCoroutineDispatcher())
        }
        fakeViewModel = spyk(HomeViewModel(fakeTodoRepository)){
            every { viewModelScope } returns TestCoroutineScope(TestCoroutineDispatcher())
        }
    }

    @Test
    fun `Given entity, when onDoneCheckboxClicked, then updateTodo change done parameter`(){
        val entity = TodoEntity(0,"test", Date(0),5,false)
        viewModel.onDoneCheckboxClicked(entity)
        coVerify(exactly = 1) { todoRepository.updateTodo(entity.copy(done = !entity.done)) }
    }

    @Test
    fun `Given index is zero, when setOrderStateByIndex, then items is getTodosOrderByTitle()`(){
        viewModel.setOrderStateByIndex(0)
        assertEquals(viewModel.orderStateFlow.value,OrderState.TITLE)
    }

    @Test
    fun `Given index is zero, when setOrderStateByIndex, then items is getTodosOrderByPriority()`(){
        viewModel.setOrderStateByIndex(1)
        assertEquals(viewModel.orderStateFlow.value,OrderState.PRIORITY)
    }

    @Test
    fun `Given index is zero, when setOrderStateByIndex, then items is getTodosOrderByDate()`(){
        viewModel.setOrderStateByIndex(2)
        assertEquals(viewModel.orderStateFlow.value,OrderState.DATE)
    }

    @Test
    fun `Given items value is empty,when onAddButtonClicked, then call navigate with action id zero`(){
        val action = HomeFragmentDirections.actionHomeFragmentToAddBottomSheetFragment(0)
        viewModel.onCreate()
        viewModel.onAddButtonClicked()
        coVerify(exactly = 1) { viewModel.navigate(action) }
    }

    @Test
    fun `Given items value is not empty,when onAddButtonClicked, then call navigate with action max id of items`() = runBlockingTest {
        fakeViewModel.onCreate()
        fakeTodoRepository.insertTodo("test",Date(300),5)
        val id = fakeViewModel.items.first().last().id
        val action = HomeFragmentDirections.actionHomeFragmentToAddBottomSheetFragment(id)
        fakeViewModel.onAddButtonClicked()
        coVerify(exactly = 1) { fakeViewModel.navigate(action) }
    }

    /*
    * Given index is zero, when setOrderStateByIndex, then items is getTodosOrderByTitle()
    * Given index is one, when setOrderStateByIndex, then items is getTodosOrderByPriority()
    * Given index is else, when setOrderStateByIndex, then items is getTodosOrderByDate()
    *
    * Given items.value is empty,when onAddButtonClicked, then call navigate with action id zero
    * !!Given items.value is not empty,when onAddButtonClicked, then call navigate with max action id
    *
    * */

}