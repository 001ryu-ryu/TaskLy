package com.example.taskly.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation

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