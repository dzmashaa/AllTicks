package com.example.allticks.model

import com.google.firebase.firestore.DocumentId

data class Task(
    @DocumentId val id: String = "",
    val title: String = "",
    val priority: String = "None",
    val dueDate: String = "",
    val dueTime: String = "",
    val description: String = "",
    val flag: Boolean = false,
    val completed: Boolean = false,
    val createdAt: Long = System.currentTimeMillis(),
    val userId: String = ""
) {
    val taskPriority: TaskPriority
        get() = TaskPriority.fromString(priority)
}
