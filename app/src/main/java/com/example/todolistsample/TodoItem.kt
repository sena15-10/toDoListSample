package com.example.todolistsample

data class TodoItem (
    val id: Int, // This will be needed when using a database.
    val taskName: String,
    var isCompleted: Boolean = false, // Represents the completion status
    var priority: Int //低　中　高　の値を格納する

    // We can add properties like priority or due date later.
)