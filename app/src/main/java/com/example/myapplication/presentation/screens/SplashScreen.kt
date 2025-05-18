package com.example.myapplication.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Card(modifier = Modifier.height(100.dp).width(100.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Логотип приложения",
                    modifier = Modifier.size(200.dp) // Размер изображения
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

        }

        // Автоматический переход после задержки
        LaunchedEffect(Unit) {
            delay(1000) // 3 секунды задержки
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true } // Удаляем splash из back stack
            }
        }
    }}