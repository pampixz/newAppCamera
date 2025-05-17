package com.example.myapplication.presentation.screens

import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.presentation.components.CustomButton
import com.example.myapplication.ui.theme.AppColors
import java.time.format.TextStyle

@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            ) {
            Spacer(modifier = Modifier.height(120.dp))
            Text(
                // 1. Основное содержимое
                text = "Добро пожаловать",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),   // Ширина
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            CustomButton(
                text = "Начать распознавание",

                onClick = {

                    navController.navigate("picker")
                })

            Spacer(modifier = Modifier.height(20.dp))
            CustomButton(text = "История", onClick = { navController.navigate("history") })

            Spacer(modifier = Modifier.height(30.dp))

        }
    }
}

