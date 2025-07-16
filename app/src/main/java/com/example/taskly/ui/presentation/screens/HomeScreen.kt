package com.example.taskly.ui.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddTask
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.taskly.components.CustomCircularLoader
import com.example.taskly.components.TaskCard
import com.example.taskly.data.authantication.Resource
import com.example.taskly.data.model.Task
import com.example.taskly.ui.navigation.Routes
import com.example.taskly.ui.navigation.Routes.*
import com.example.taskly.ui.viewmodel.AuthViewModel
import com.example.taskly.ui.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(authViewModel: AuthViewModel = hiltViewModel(),
               taskViewModel: TaskViewModel = hiltViewModel(),
               navHostController: NavHostController) {
    val taskState = taskViewModel.tasks.collectAsState()



    val swipeThreshold = 150f
    var offsetY by remember { mutableFloatStateOf(0f) }

    val listState = rememberLazyListState()

    val nestedScrollConnection = remember {
        object: NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Only care about downward scroll (y > 0) and when at top
                if (available.y > 0 && listState.firstVisibleItemIndex == 0 &&
                    listState.firstVisibleItemScrollOffset == 0) {
                    offsetY += available.y
                }
                return Offset.Zero
            }

            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource
            ): Offset {
                // check if threshold reached
                if (offsetY > swipeThreshold) {

                    navHostController.navigate(Routes.CompletedTasks) {
                        launchSingleTop = true // If I’m already on top of the stack, don’t add a new one.
                        popUpTo(Routes.Home)
                    }
                    offsetY = 0f
                }
                return Offset.Zero
            }

            override suspend fun onPreFling(available: Velocity): Velocity {
                // Reset if fling cancelled
                offsetY = 0f
                return Velocity.Zero
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {Text("Hi ${authViewModel.currentUser?.displayName}, your Tasks")}
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
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
                .nestedScroll(nestedScrollConnection)
        ) {
            if (taskState.value == null) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    CustomCircularLoader(
                        Modifier.padding(innerPadding)
                    )
                }
            } else {
                when (val state = taskState.value) {
                    is Resource.Success -> {
                        LazyColumn(state = listState) {
                            items(items = state.result.filter { !it.isCompleted },
                                key = {it.id
                                }) { task ->
                                TaskItem(
                                    task = task,
                                    onSwipeComplete = {
                                        taskViewModel.editTask(task.copy(isCompleted = true))
                                    },
                                    onDeleteClick = {
                                        taskViewModel.removeTask(task)
                                    }
                                ) {
                                    navHostController.navigate(
                                        EditTask(
                                            id = task.id,
                                            title = task.title,
                                            description = task.description,
                                            timeStamp = task.timeStamp
                                        )
                                    )
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

                    else -> {}
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    task: Task,
    onSwipeComplete: () -> Unit,
    onDeleteClick: () -> Unit,
    onClick: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        positionalThreshold = {totalDistance -> totalDistance * 0.5f},
        confirmValueChange = {value ->
            if (value == SwipeToDismissBoxValue.EndToStart || value == SwipeToDismissBoxValue.StartToEnd) {
                onSwipeComplete()
                true
            } else false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
        }
    ) {
        TaskCard(
            task = task,
            onDeleteClick = onDeleteClick,
            onClick = onClick
        )
    }

}








