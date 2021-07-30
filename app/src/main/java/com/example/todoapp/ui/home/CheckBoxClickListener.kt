package com.example.todoapp.ui.home

import com.example.todoapp.room.TodoEntity

interface CheckBoxClickListener {
    fun checkBoxClickListener(todoModel: TodoEntity)
}
