package com.example.taskly.components

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomButton(
    title: String,
    onClick: () -> Unit
) {
    ElevatedButton(
        onClick = onClick
    ) {
        Text(title)
    }
}
