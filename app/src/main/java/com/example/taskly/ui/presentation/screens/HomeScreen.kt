package com.example.taskly.ui.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.taskly.ui.navigation.Routes
import com.example.taskly.ui.viewmodel.AuthViewModel

@Composable
fun HomeScreen(viewModel: AuthViewModel = hiltViewModel(), navHostController: NavHostController) {
    Scaffold(
        topBar = {
            Text("Welcome ${viewModel.currentUser?.displayName}")
        },
        floatingActionButton = {
            IconButton(
                onClick = {
                    viewModel.logout()
                    navHostController.navigate(Routes.LogIn) {
                        popUpTo(Routes.Home) {inclusive = true}
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.Logout,
                    contentDescription = null
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text("Empt Tasks")
        }
    }
}
