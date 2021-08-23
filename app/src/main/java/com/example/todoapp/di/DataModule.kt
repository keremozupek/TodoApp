package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.repositories.TodoRepository
import com.example.todoapp.repositories.TodoRepositoryImpl
import com.example.todoapp.room.TodoDao
import com.example.todoapp.room.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DataModule {

    @ViewModelScoped
    @Provides
    fun provideTodoDb(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(
            context, TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @ViewModelScoped
    @Provides
    fun provideTodoDAO(todoDatabase: TodoDatabase): TodoDao {
        return todoDatabase.todoDao()
    }

    @ViewModelScoped
    @Provides
    fun provideTodoRepository(todoDao : TodoDao): TodoRepository {
        return TodoRepositoryImpl(todoDao)
    }

}
