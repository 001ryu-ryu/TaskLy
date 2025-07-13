package com.example.taskly.ui.presentation.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.taskly.data.authantication.Resource
import com.example.taskly.ui.navigation.Routes
import com.example.taskly.ui.viewmodel.AuthViewModel
import com.example.taskly.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(authViewModel: AuthViewModel = hiltViewModel(),
               taskViewModel: TaskViewModel = hiltViewModel(),
               navHostController: NavHostController) {
    val taskList = taskViewModel.tasks.collectAsState()
    LaunchedEffect(Unit) {
        taskViewModel.getAllTasks()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Welcome ${authViewModel.currentUser?.displayName}")}
            )

        },
        floatingActionButton = {
            IconButton(
                onClick = {
                    navHostController.navigate(Routes.AddTask)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.AddTask,
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        taskList.value?.let {
            when(it) {
                is Resource.Success -> {
                    LazyColumn(modifier = Modifier.padding(innerPadding)) {
                        items(it.result) {task ->
                            TaskItem(
                                taskTitle = task.title,
                                taskDescription = task.description
                            )
                        }
                    }
                }

                is Resource.Failure -> {
                    // show failure later
                }

                is Resource.Loading -> {
                    // show loading later
                }
            }
        }.run {
            Text("Null")
        }
    }
}

@Composable
fun TaskItem(
    taskTitle: String,
    taskDescription: String
) {
    ElevatedCard(
        modifier = Modifier.fillMaxSize()
            .padding(10.dp)
    ) {
        Text(taskTitle)
        Text(taskDescription)
    }
}








