package com.example.composedb.view

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composedb.TodoState
import com.example.composedb.model.Todo
import com.example.composedb.utils.TodoEvent
import com.example.composedb.utils.TodoUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    context: Context,
    state: TodoState,
    onEvent: ((TodoEvent) -> Unit)

) {

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(TodoEvent.ShowDialog)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add todo")

            }
        },
        modifier = Modifier.padding(16.dp )
    ) { padding ->

        if (state.isAddingTodo){
            AddTodoDialog(state = state, onEvent = onEvent)
        }

        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
           items(state.todos){todo->
               TodoView(todo = todo,context,onEvent)
           }
        }

    }

}

@Composable
fun TodoView(todo: Todo,context: Context,onEvent: ((TodoEvent) -> Unit)) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp,10.dp)
                .clickable {
                    TodoUtils().makeToast(context, todo.todoName)
                }
        ) {
            Text(text = todo.todoName, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold)
            Text(text = todo.todoDescription, fontSize = 18.sp, fontWeight = FontWeight.Light)
        }
        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Todo",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .clickable {
                    onEvent(TodoEvent.DeleteTodo(todo))
                }
            )
    }

}