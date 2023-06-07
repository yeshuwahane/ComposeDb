package com.example.composedb

import com.example.composedb.db.SortType
import com.example.composedb.model.Todo

data class TodoState(
    val todos: List<Todo> = emptyList(),
    val todoName: String ="",
    val todoDescription:String ="",
    val isAddingTodo:Boolean = false,
    val sortType: SortType = SortType.TODO_NAME
)
