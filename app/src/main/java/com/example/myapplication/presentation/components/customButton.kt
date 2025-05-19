package com.example.myapplication.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.AppColors

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    isOpacity: Boolean = true

) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .height(70.dp)
            .width(300.dp),

        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.color200.copy(
                alpha = if (isOpacity) 0.8f else 1.0f
            )
        ),


        elevation = ButtonDefaults.buttonElevation(  // Тень
            defaultElevation = 8.dp,
            pressedElevation = 4.dp,
            disabledElevation = 0.dp
        ),
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelMedium
        )
    }
}
