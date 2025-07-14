package com.example.taskly.data.model

import kotlinx.serialization.Serializable

data class Task(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val timeStamp: Long = 0L
)
