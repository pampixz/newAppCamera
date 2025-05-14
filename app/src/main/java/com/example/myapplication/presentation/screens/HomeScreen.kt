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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.presentation.components.CustomButton
import com.example.myapplication.ui.theme.AppColors
import java.time.format.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    Scaffold(
        containerColor = AppColors.backgroundColor,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                // 1. Основное содержимое
                text = "Добро пожаловать",

                // 2. Модификаторы
                modifier = Modifier
                    .padding(16.dp)    // Отступы
                    .fillMaxWidth(),   // Ширина

                // 3. Цвет
                color = AppColors.textColor,  // Фиолетовый

                // 4. Размер и стиль шрифта
                fontFamily = FontFamily.SansSerif,  // Гарнитура
                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,  // Курсив

                // 5. Выравнивание и оформление
                textAlign = TextAlign.Center,       // Центрирование
                lineHeight = 24.sp,                 // Высота строки


                // 7. Стиль из темы (переопределяет отдельные параметры)
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.primary,
                    background = MaterialTheme.colorScheme.background
                )
            )
            Spacer(
                modifier = Modifier.height(24.dp)
            )
            CustomButton(
                text = "Начать распознавание",
                onClick = { navController.navigate("picker") })

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            CustomButton(text = "История", onClick = { navController.navigate("history") })

        }
    }
}
