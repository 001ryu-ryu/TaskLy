package com.example.taskly

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.taskly.ui.presentation.onboarding.OnBoardingScreen
import com.example.taskly.ui.presentation.screens.SignUpScreen
import com.example.taskly.ui.theme.TaskLyTheme
import com.example.taskly.utils.OnboardingUtils
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val onboardingUtils by lazy { OnboardingUtils(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskLyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    if (onboardingUtils.isOnboardingComplete()) {
//                        SignUpScreen(Modifier.padding(innerPadding))
//                    } else {
//                        ShowOnboardingScreen()
//                    }

                    SignUpScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }

    @Composable
    fun ShowOnboardingScreen() {
        val scope = rememberCoroutineScope()
        OnBoardingScreen {
            onboardingUtils.setOnboardingComplete()
            scope.launch {
                setContent {
                    TaskLyTheme {
                        SignUpScreen()
                    }
                }
            }
        }
    }
}


















