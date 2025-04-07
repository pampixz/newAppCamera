package com.example.myapplication.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import com.example.myapplication.presentation.viewmodel.MainViewModel

@Composable
fun HistoryScreen(vm: MainViewModel) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text("История распознаваний", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        vm.history.forEachIndexed { index, list ->
            Text("Объект ${index + 1}: ${list.joinToString { it.text }}")
        }
    }
}