package com.example.taskly.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.taskly.ui.presentation.screens.AddTaskScreen
import com.example.taskly.ui.presentation.screens.CompletedTasksScreen
import com.example.taskly.ui.presentation.screens.EditTaskName
import com.example.taskly.ui.presentation.screens.HomeScreen
import com.example.taskly.ui.presentation.screens.LogInScreen
import com.example.taskly.ui.presentation.screens.SignUpScreen

@Composable
fun NavApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.LogIn) {
        composable<Routes.SignUp> {
            SignUpScreen(navHostController = navController)
        }

        composable<Routes.LogIn> {
            LogInScreen(navHostController = navController)
        }

        composable<Routes.Home> {
            HomeScreen(navHostController = navController)
        }

        composable<Routes.AddTask>() {
            AddTaskScreen(navHostController = navController)
        }

        composable<Routes.EditTask> {
            val args = it.toRoute<Routes.EditTask>()
            EditTaskName(
                id = args.id,
                title = args.title,
                description = args.description,
                timeStamp = args.timeStamp,
                navHostController = navController
            )
        }

        composable<Routes.CompletedTasks> {
            CompletedTasksScreen(navHostController = navController)
        }
    }
}




















