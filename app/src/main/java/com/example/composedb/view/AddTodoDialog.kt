package com.example.composedb.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composedb.TodoState
import com.example.composedb.utils.TodoEvent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodoDialog(
    state: TodoState,
    onEvent: (TodoEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    AlertDialog(
        modifier = Modifier,
        onDismissRequest = {
                           onEvent(TodoEvent.HideDialog)
                           },
        title = { Text(text = "Add New Todo")},
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)){

                TextField(
                    value = state.todoName,
                    onValueChange = {
                    onEvent(TodoEvent.SetTodoTitle(it))
                },
                    placeholder = {
                        Text(text = "Todo Titile")
                    }
                )
                TextField(
                    value = state.todoDescription,
                    onValueChange = {
                        onEvent(TodoEvent.SetTodoDesc(it))
                    },
                    placeholder = {
                        Text(text = "Todo Description")
                    }
                )
            }
        },
        confirmButton = {
            Box(modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
                ){
                Button(onClick = { onEvent(TodoEvent.SaveTodo) }) {
                    Text(text = "Save Todo")
                }
            }
        }
    )

}