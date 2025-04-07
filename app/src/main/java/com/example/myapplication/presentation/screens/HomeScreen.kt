package com.example.myapplication.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Добро пожаловать!", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.navigate("picker") }) {
            Text("Начать распознавание")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("history") }) {
            Text("История")
        }
    }
}