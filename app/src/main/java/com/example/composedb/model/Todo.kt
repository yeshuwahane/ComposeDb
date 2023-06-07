package com.example.composedb.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Todo(
    val todoName: String,
    val todoDescription: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
    )
