package com.example.taskly.ui.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(modifier: Modifier = Modifier) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val onNameChange = {newName: String ->
        name = newName
    }

    val onEmailChange = {newEmail: String ->
        email = newEmail
    }
    val onPasswordChange = {newPassword: String ->
        password = newPassword
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {Text("TaskLy")})
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(top = 50.dp, start = 40.dp, end = 40.dp)
        ) {
            CustomTextField(
                textState = name,
                title = "name"
            ) {
                onNameChange(it)
            }

            Spacer(Modifier.height(10.dp))
            CustomTextField(
                textState = email,
                title = "email"
            ) {
                onEmailChange(it)
            }

            Spacer(Modifier.height(10.dp))
            CustomTextField(
                textState = password,
                title = "password",
                visualTransformation = if (isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                trailingIcon = {
                    val icon = if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    IconButton(
                        onClick = {
                            isPasswordVisible = !isPasswordVisible
                        }
                    ) {
                        Icon(imageVector = icon, contentDescription = null)
                    }
                }
            ) {
                onPasswordChange(it)
            }

            Spacer(Modifier.height(50.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ElevatedButton(
                    onClick = {}
                ) {
                    Text("Sign Up")
                }
            }

        }
    }
}

@Composable
fun CustomTextField(
    textState: String,
    title: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable () -> Unit = {},
    onTextChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = textState,
        label = { Text(title) },
        onValueChange = {onTextChange(it)},
        modifier = Modifier.fillMaxWidth(),
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon
    )
}



























