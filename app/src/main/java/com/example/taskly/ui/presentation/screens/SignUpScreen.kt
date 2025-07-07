package com.example.taskly.ui.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.taskly.data.authantication.Resource
import com.example.taskly.ui.navigation.Routes
import com.example.taskly.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(viewModel: AuthViewModel = hiltViewModel(), navHostController: NavHostController) {
    val signupFlow = viewModel.signupFlow.collectAsState()

    val context = LocalContext.current

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
                    onClick = {
                        viewModel.signup(
                            name = name,
                            email = email,
                            password = password
                        )
                    }
                ) {
                    Text("Sign Up")
                }
            }

            Spacer(Modifier.height(15.dp))
            signupFlow.value?.let {
                when(it) {
                    is Resource.Failure -> {
                        Text(
                            text = it.error.message!!,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    Resource.Loading -> {
                        Text(
                            text = "Please wait!",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            navHostController.navigate(Routes.Home) {
                                popUpTo(Routes.SignUp) {inclusive = true}
                            }
                        }
                    }
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



























