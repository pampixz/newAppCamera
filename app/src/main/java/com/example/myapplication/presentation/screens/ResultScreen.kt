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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.navigation.NavController
import com.example.myapplication.presentation.components.CustomButton
import com.example.myapplication.presentation.viewmodel.MainViewModel
import com.example.myapplication.ui.theme.AppColors

@Composable
fun ResultScreen(navController: NavController, vm: MainViewModel) {
    val resultsNoSet by vm.result.collectAsState()
    val results = resultsNoSet.distinct()
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
                            items(results) { label ->
                                if (results.indexOf(label) == 0) {
                                    Spacer(modifier = Modifier.height(100.dp))
                                }
                                ListElement(label = label)
//                                if (results.indexOf(label) == (results.size - 1)) {
//                                    Spacer(modifier = Modifier.height(100.dp))
//                                }
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
                    .padding(paddingValues)
                    .padding(16.dp),
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
fun ListElement(label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .background(
                    color = AppColors.color600,
                    shape = RoundedCornerShape(8.dp)
                )
                .height(70.dp)
                .padding(horizontal = 10.dp)

                .fillMaxWidth()
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}