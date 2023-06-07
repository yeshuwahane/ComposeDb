package com.example.composedb.view

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composedb.TodoState
import com.example.composedb.db.SortType
import com.example.composedb.db.TodoDao
import com.example.composedb.model.Todo
import com.example.composedb.utils.TodoEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoViewModel(
    private val todoDao: TodoDao
): ViewModel() {

    private val _sortType = MutableStateFlow(SortType.TODO_NAME)
    private val _todo = _sortType
        .flatMapLatest {sortType ->
            when(sortType){
                SortType.TODO_NAME -> todoDao.todoOrderedByAtoZ()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _state = MutableStateFlow(TodoState())
    val state = combine(_state,_sortType,_todo){ state,sortType,todos ->
        state.copy(
            todos = todos,
             sortType = sortType

        )

    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), TodoState())




    fun onEvent(event: TodoEvent){
        when(event){
            is TodoEvent.DeleteTodo -> {
                viewModelScope.launch {
                    todoDao.deleteTodo(event.todo)
                }
            }
            TodoEvent.HideDialog -> {
                _state.update { it.copy(
                    isAddingTodo = false
                ) }
            }
            TodoEvent.SaveTodo -> {
                val todoName = state.value.todoName
                val todoDescription = state.value.todoDescription

                if (todoName.isBlank() || todoDescription.isBlank()){
                    return
                }
                val todo = Todo(todoName = todoName, todoDescription = todoDescription)
                viewModelScope.launch {
                    todoDao.upsertTodo(todo)
                }
                _state.update { it.copy(
                    isAddingTodo = false,
                    todoName = "",
                    todoDescription = ""
                ) }
            }

            TodoEvent.AddTodo ->{
                val todoName = state.value.todoName
                val todoDescription = state.value.todoDescription

                if (todoName.isBlank() || todoDescription.isBlank()){
                    return
                }

                val todo = Todo(todoName,todoDescription)
                viewModelScope.launch {
                    todoDao.insertTodo(todo)
                }
                _state.update {it.copy(
                    isAddingTodo = false
                ) }

            }




            is TodoEvent.SetTodoDesc -> {
                _state.update { it.copy(
                    todoDescription = event.todoDesc
                ) }
            }
            is TodoEvent.SetTodoTitle -> {
                _state.update { it.copy(
                    todoName = event.todoTitle
                ) }
            }
            TodoEvent.ShowDialog -> {
                _state.update { it.copy(
                    isAddingTodo = true
                ) }
            }

            is TodoEvent.SortTodo -> {
                _sortType.value = event.sortType
            }
        }
    }


    fun makeToast(context:Context,msg: String){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

}