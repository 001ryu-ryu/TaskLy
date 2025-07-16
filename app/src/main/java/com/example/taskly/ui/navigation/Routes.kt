package com.example.taskly.ui.navigation

import com.example.taskly.data.model.Task
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    object Home: Routes()
    @Serializable
    object SignUp: Routes()
    @Serializable
    object LogIn: Routes()

    @Serializable
    object AddTask: Routes()
    @Serializable
    object CompletedTasks: Routes()
    @Serializable
    data class EditTask(
        val id: String,
        val title: String,
        val description: String,
        val timeStamp: Long
    ): Routes()
}