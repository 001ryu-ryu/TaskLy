package com.example.taskly.ui.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
                                taskDescription = task.description,
                                onDeleteClick = {
                                    taskViewModel.removeTask(task)
                                }
                            ) {
                                navHostController.navigate(Routes.EditTask(
                                    id = task.id,
                                    title = task.title,
                                    description = task.description,
                                    timeStamp = task.timeStamp
                                ))
                            }
                        }
                    }
                }

                is Resource.Failure -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Cannot load tasks, please try enabling internet.")
                    }
                }

                is Resource.Loading -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(innerPadding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Loading...")
                    }
                }
            }
        }.run {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Cannot load tasks, please try enabling internet.")
            }
        }
    }
}

@Composable
fun TaskItem(
    taskTitle: String,
    taskDescription: String,
    onDeleteClick: () -> Unit,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .clickable(
                onClick = onClick
            )
    ) {
        Row {
            Column {
                Text(taskTitle)
                Text(taskDescription)
            }
            IconButton(
                onClick = onDeleteClick
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null
                )
            }

        }

    }
}








