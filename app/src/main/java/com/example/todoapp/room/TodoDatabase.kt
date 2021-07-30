package com.example.todoapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [TodoEntity::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
    companion object {
        const val DATABASE_NAME: String = "todo_table"
    }
}
