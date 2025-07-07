package com.example.taskly.ui.presentation.onboarding

import com.example.taskly.R

sealed class OnBoardingModel(
    val image: Int,
    val title: String,
    val description: String
) {

    data object FirstPage : OnBoardingModel(
        image = R.drawable.on_board1,
        title = "Create todo",
        description = "Add your tasks"
    )

    data object SecondPage : OnBoardingModel(
        image = R.drawable.on_board2,
        title = "Share",
        description = "Share with your team"
    )

    data object ThirdPage : OnBoardingModel(
        image = R.drawable.on_board3,
        title = "Sync",
        description = "Keep up with the flow"
    )
}