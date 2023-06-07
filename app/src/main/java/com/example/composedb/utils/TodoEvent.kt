package com.example.composedb.utils

import com.example.composedb.db.SortType
import com.example.composedb.model.Todo

sealed interface TodoEvent {
    object SaveTodo : TodoEvent
    object AddTodo: TodoEvent
    data class SetTodoTitle(val todoTitle: String):TodoEvent
    data class SetTodoDesc(val todoDesc: String): TodoEvent

    object ShowDialog: TodoEvent
    object HideDialog: TodoEvent

    data class DeleteTodo(val todo: Todo): TodoEvent
    data class SortTodo(val sortType: SortType): TodoEvent

}