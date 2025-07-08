package com.example.taskly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.taskly.ui.navigation.NavApp
import com.example.taskly.ui.theme.TaskLyTheme
import com.example.taskly.utils.OnboardingUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val onboardingUtils by lazy { OnboardingUtils(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TaskLyTheme {

//                    if (onboardingUtils.isOnboardingComplete()) {
//                        SignUpScreen(Modifier.padding(innerPadding))
//                    } else {
//                        ShowOnboardingScreen()
//                    }

//                    SignUpScreen()
                NavApp()

            }
        }
    }

//    @Composable
//    fun ShowOnboardingScreen() {
//        val scope = rememberCoroutineScope()
//        OnBoardingScreen {
//            onboardingUtils.setOnboardingComplete()
//            scope.launch {
//                setContent {
//                    TaskLyTheme {
//                        SignUpScreen()
//                    }
//                }
//            }
//        }
//    }
}


















