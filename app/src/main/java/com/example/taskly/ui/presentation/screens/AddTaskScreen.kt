package com.example.taskly.ui.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.taskly.components.CustomButton
import com.example.taskly.components.CustomTextField
import com.example.taskly.data.authantication.Resource
import com.example.taskly.data.model.Task
import com.example.taskly.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    viewModel: TaskViewModel = hiltViewModel(), navHostController: NavHostController
) {
    val taskState = viewModel.addTaskState.collectAsState()
    var taskTitle by remember { mutableStateOf("") }
    var taskDescription by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Task")},
                navigationIcon = {
                    IconButton(
                        onClick = { navHostController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(top = 50.dp, start = 40.dp, end = 40.dp)
        ) {
            CustomTextField(
                textState = taskTitle,
                title = "Add task",
                visualTransformation = VisualTransformation.None
            ) {
                taskTitle = it
            }
            Spacer(Modifier.height(8.dp))
            CustomTextField(
                textState = taskDescription,
                title = "about this task",
                visualTransformation = VisualTransformation.None
            ) {
                taskDescription = it
            }

            CustomButton(
                title = "Add task"
            ) {
                viewModel.addTask(
                    Task(
                        title = taskTitle,
                        description = taskDescription,
                        timeStamp = System.currentTimeMillis()
                    )
                )
            }

            taskState.value?.let {
                when(it) {
                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            navHostController.popBackStack()
                        }
                    }

                    is Resource.Failure -> {
                        Toast.makeText(
                            LocalContext.current, "Error adding task ${it.error.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is Resource.Loading -> {
                        Spacer(Modifier.height(8.dp))
                        Text("Adding task...")
                    }
                }
            }
        }
    }
}




























