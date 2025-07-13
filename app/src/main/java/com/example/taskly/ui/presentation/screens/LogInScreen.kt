package com.example.taskly.ui.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Password
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.taskly.R
import com.example.taskly.components.CustomButton
import com.example.taskly.components.CustomTextField
import com.example.taskly.components.ExistingAccount
import com.example.taskly.data.authantication.Resource
import com.example.taskly.ui.navigation.Routes
import com.example.taskly.ui.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LogInScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val loginFlow = viewModel.loginFlow.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val onEmailChange = {newEmail: String ->
        email = newEmail
    }
    val onPasswordChange = {newPassword: String ->
        password = newPassword
    }

    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    // animation
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.login_animation))

    val progress by animateLottieCompositionAsState(
        isPlaying = true,
        composition = composition,
        iterations = LottieConstants.IterateForever,
        speed = 0.7f
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("TaskLy") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(top = 50.dp, start = 40.dp, end = 40.dp)
        ) {
            LottieAnimation(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally),
                composition = composition,
                progress = {progress}
            )
            CustomTextField(
                textState = email,
                title = emailError.ifBlank { "Email" },
                color = if (emailError.isNotEmpty()) Color.Red else Color.Unspecified,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Email,
                        contentDescription = null
                    )
                }
            ) {
                onEmailChange(it)
            }

            Spacer(Modifier.height(10.dp))
            CustomTextField(
                textState = password,
                title = passwordError.ifBlank { "password" },
                color = if (passwordError.isNotEmpty()) Color.Red else Color.Unspecified,
                visualTransformation = if (isPasswordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Outlined.Password,
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    val icon =
                        if (isPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
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

                CustomButton(
                    title = "Login",
                ) {
                    emailError = if (email.isBlank()) "Email is required" else ""
                    passwordError = if (email.isBlank()) "Password is required" else ""

                    if (emailError.isEmpty() && passwordError.isEmpty()) {
                        viewModel.login(
                            email = email,
                            password = password
                        )
                    }

                }
            }

            loginFlow.value?.let {
                when(it) {
                    is Resource.Failure -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(it.error.message!!,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center)
                        }

                    }
                    Resource.Loading -> {
                        Text("logging in")
                    }
                    is Resource.Success -> {
                        LaunchedEffect(Unit) {
                            navHostController.navigate(Routes.Home) {
                                popUpTo(Routes.LogIn) {inclusive = true}
                            }
                        }
                    }
                }
            }

            Spacer(Modifier.height(15.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ExistingAccount(
                    title = "New to TaskLy?",
                    buttonTitle = "Create an account"
                ) {
                    navHostController.navigate(Routes.SignUp)
                }
            }

        }
    }
}




















