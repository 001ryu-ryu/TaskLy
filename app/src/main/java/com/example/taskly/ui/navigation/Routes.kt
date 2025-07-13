package com.example.taskly.ui.navigation

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
}