package com.example.composedb.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.composedb.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert
    suspend fun insertTodo(todo: Todo)

    @Upsert
    suspend fun upsertTodo(todo: Todo)
    @Delete
   suspend fun deleteTodo(todo: Todo)

   @Query("SELECT * FROM todo ORDER BY todoName ASC")
   fun todoOrderedByAtoZ() : Flow<List<Todo>>


}