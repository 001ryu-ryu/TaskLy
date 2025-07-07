package com.example.taskly.ui.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.taskly.ui.viewmodel.AuthViewModel

@Composable
fun HomeScreen(viewModel: AuthViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            Text("Welcome ${viewModel.currentUser?.displayName}")
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text("Empt Tasks")
        }
    }
}
