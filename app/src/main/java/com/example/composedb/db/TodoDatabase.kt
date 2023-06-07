package com.example.composedb.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.composedb.model.Todo

@Database(
    entities = [Todo::class],
     version = 2
)
abstract class TodoDatabase: RoomDatabase() {
    abstract val dao : TodoDao
}