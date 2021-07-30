package com.example.todoapp.room

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "todo_table")
data class TodoEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val dueDate: Date,
    val priority: Int,
    var done: Boolean
)
