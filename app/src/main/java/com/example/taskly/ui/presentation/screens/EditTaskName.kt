package com.example.taskly.ui.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.taskly.components.CustomButton
import com.example.taskly.components.CustomLinearLoader
import com.example.taskly.components.CustomTextField
import com.example.taskly.components.NavBackIcon
import com.example.taskly.data.authantication.Resource
import com.example.taskly.data.model.Task
import com.example.taskly.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskName(
    id: String,
    title: String,
    description: String,
    timeStamp: Long,
    taskViewModel: TaskViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val task = remember {
        Task(
            id = id,
            title = title,
            description = description,
            timeStamp = timeStamp
        )
    }
    val editTaskState = taskViewModel.addTaskState.collectAsState()
    var nameState by remember { mutableStateOf(task.title) }
    var descriptionState by remember { mutableStateOf(task.description) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Redefine $title") },
                navigationIcon = {
                    NavBackIcon { navHostController.popBackStack() }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextField(
                textState = nameState
            ) {
                nameState = it
            }
            Spacer(Modifier.height(8.dp))
            CustomTextField(
                textState = descriptionState
            ) {
                descriptionState = it
            }
            Spacer(Modifier.height(8.dp))
            CustomButton(
                title = "Save Changes"
            ) {
                taskViewModel.editTask(
                    task.copy(
                        title = nameState,
                        description = descriptionState
                    )
                )
            }

            editTaskState.value?.let {state ->
                when(state) {
                    is Resource.Failure -> {
                        Text("Failed to edit task: ${state.error}")
                    }
                    Resource.Loading -> {
                        CustomLinearLoader()
                    }
                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            navHostController.popBackStack()
                        }
                    }
                }
            }
        }
    }
}