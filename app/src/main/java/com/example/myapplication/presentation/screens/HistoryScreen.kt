package com.example.myapplication.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.myapplication.presentation.components.CustomButton
import com.example.myapplication.presentation.viewmodel.MainViewModel
import com.example.myapplication.ui.theme.AppColors
import com.google.mlkit.vision.objects.DetectedObject

@Composable
fun HistoryScreen(navController: NavController, vm: MainViewModel) {
    val history = vm.history;
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        Box() {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
//                    .padding(16.dp)
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box() {


                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 60.dp)
                                .padding(horizontal = 10.dp)
                        ) {
                            items(history) { list ->
                                if (history.indexOf(list) == 0) {
                                    Spacer(modifier = Modifier.height(100.dp))
                                }
                                ListElementWithLabel(listOfElements = list.distinct(), index = history.indexOf(list))
                            }
                        }
                        Text(
                            "История распознаваний:",
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
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CustomButton(text = "Главное меню", onClick = {
                    navController.navigate("home") {
                        // Очищаем весь стек до home (включительно)
                        popUpTo("home") { inclusive = true }
                        // Предотвращаем добавление дубликатов
                        launchSingleTop = true
                    }
                })
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
fun ListElementWithLabel(listOfElements: List<DetectedObject.Label>, index: Int) {
    Column() {
        Box(
            modifier = Modifier
                .background(
                    color = AppColors.color600,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(horizontal = 10.dp)
                .padding(vertical = 20.dp)
                .fillMaxWidth(),

            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Распазнавание: $index", style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(10.dp))
                listOfElements.forEach { element ->
                    Text(
                        text = "${element.text}: ${element.confidence}",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}
