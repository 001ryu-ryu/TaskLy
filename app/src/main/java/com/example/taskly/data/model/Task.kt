package com.example.taskly.data.model

import androidx.annotation.Keep
import com.google.firebase.database.PropertyName
import kotlinx.serialization.Serializable
@Keep
data class Task(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val timeStamp: Long = 0L,
    @get:PropertyName("isCompleted")
    @set:PropertyName("isCompleted")
    var isCompleted: Boolean = false
)
