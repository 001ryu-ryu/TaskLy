package com.example.taskly.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CustomLinearLoader() {
    LinearProgressIndicator(
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CustomCircularLoader(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier.fillMaxSize()
    )
}

