package com.example.taskly.data.model

data class Task(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val timeStamp: Long = 0L
)
