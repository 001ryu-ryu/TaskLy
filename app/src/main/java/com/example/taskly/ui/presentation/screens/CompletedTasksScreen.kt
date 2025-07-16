package com.example.taskly.ui.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.taskly.components.NavBackIcon
import com.example.taskly.components.TaskCard
import com.example.taskly.data.authantication.Resource
import com.example.taskly.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompletedTasksScreen(
    taskViewModel: TaskViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val tasks = taskViewModel.tasks.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {Text("Completed Tasks")},
                navigationIcon = { NavBackIcon() {navHostController.popBackStack()} }
            )
        }
    ) { innerPadding ->
        tasks.value?.let {state ->
            when(state) {
                is Resource.Failure -> {}
                Resource.Loading -> {}
                is Resource.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().padding(innerPadding)
                    ) {
                        items(state.result.filter { it.isCompleted }) {task ->
                            TaskCard(
                                task = task,
                                onDeleteClick = {}
                            ) { }
                        }
                    }
                }
            }
        }

    }
}

























