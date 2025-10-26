package com.example.todolistsample

data class TodoItem (
    val id: Int, // This will be needed when using a database.
    val taskName: String,
    var isCompleted: Boolean = false // Represents the completion status.
    // We can add properties like priority or due date later.
)