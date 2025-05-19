package com.example.myapplication.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.presentation.components.CustomButton
import com.example.myapplication.presentation.viewmodel.MainViewModel
import com.example.myapplication.ui.theme.AppColors
import com.example.myapplication.domain.model.RecognitionResult
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ResultScreen(navController: NavController, vm: MainViewModel) {
    val results by vm.result.collectAsState()
    val formatter = remember { SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 60.dp)
                                .padding(horizontal = 10.dp)
                        ) {
                            itemsIndexed(results) { index, result ->
                                if (index == 0) {
                                    Spacer(modifier = Modifier.height(100.dp))
                                }

                                ListElement(
                                    label = result.label,
                                    confidence = result.confidence,
                                    timestamp = formatter.format(result.timetamp)
                                )

                                if (index == results.lastIndex) {
                                    Spacer(modifier = Modifier.height(200.dp))
                                }
                            }
                        }
                        Text(
                            "Результаты:",
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .align(Alignment.TopCenter),
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
//                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CustomButton(
                    isOpacity = false,
                    text = "Главное меню", onClick = {
                    navController.navigate("home") {
                        popUpTo("home") { inclusive = true }
                        launchSingleTop = true
                    }
                })
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}


@Composable
fun ListElement(label: String, confidence: Float, timestamp: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .background(
                    color = AppColors.color600,
                    shape = RoundedCornerShape(8.dp)
                )
                .height(100.dp)
                .padding(horizontal = 10.dp)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    text = "Объект: $label",
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    style = MaterialTheme.typography.labelLarge,
                )
                Text(
                    text = "Точность: ${(confidence * 100).toInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                )
                Text(
                    text = "Дата сканирования: $timestamp",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.labelSmall,
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}
