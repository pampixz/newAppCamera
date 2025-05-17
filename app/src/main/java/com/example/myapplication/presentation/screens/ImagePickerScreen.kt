package com.example.myapplication.presentation.screens

import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.presentation.components.CustomButton
import com.example.myapplication.presentation.viewmodel.MainViewModel

@Composable
fun ImagePickerScreen(navController: NavController, vm: MainViewModel) {
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val inputStream = context.contentResolver.openInputStream(it)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream?.close()
            vm.analyze(bitmap)
            navController.navigate("result")
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
    ) {

            paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Spacer(modifier = Modifier.height(120.dp))

            Text(
                // 1. Основное содержимое
                text = "Выбор источника",
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),   // Ширина
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.weight(1f))
            CustomButton(onClick = {
                navController.navigate("camera")
            }, text = "Сделать фото")


            Spacer(modifier = Modifier.height(20.dp))

            CustomButton(onClick = {
                galleryLauncher.launch("image/*")
            }, text = "Выбрать из галереи")
            Spacer(modifier = Modifier.height(20.dp))
            CustomButton(text = "Назад", onClick = { navController.popBackStack() })
            Spacer(modifier = Modifier.height(30.dp))

        }
    }
}