package com.example.myapplication.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import com.example.myapplication.presentation.viewmodel.MainViewModel

@Composable
fun ResultScreen(vm: MainViewModel) {
    val results by vm.result.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Результаты:", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))

        results.forEach { label ->
            Text(text = label, style = MaterialTheme.typography.bodyLarge)
        }
    }
}