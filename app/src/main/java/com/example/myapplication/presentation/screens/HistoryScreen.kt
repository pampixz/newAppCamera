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
fun HistoryScreen(navController: NavController, vm: MainViewModel) {
    val history by vm.history.collectAsState()
    val formatter = remember { SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault()) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    "История распознаваний:",
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {

                    itemsIndexed(history) { index, result ->
                        RecognitionResultItem(
                            result = result,
                            formatter = formatter
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (index == history.lastIndex) {
                            Spacer(modifier = Modifier.height(200.dp))
                        }
                    }
                }


            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
//            Spacer(modifier = Modifier.height(16.dp))

                CustomButton(
                    isOpacity = false,
                    text = "Главное меню",
                    onClick = {
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
fun RecognitionResultItem(result: RecognitionResult, formatter: SimpleDateFormat) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = AppColors.color600,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = "Объект: ${result.label}",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Точность: ${(result.confidence * 100).toInt()}%",
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(vertical = 2.dp)
        )

        Text(
            text = "Дата сканирования: ${formatter.format(result.timetamp)}",
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(vertical = 2.dp)
        )
    }
}
