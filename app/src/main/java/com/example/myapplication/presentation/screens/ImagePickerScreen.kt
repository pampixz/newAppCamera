package com.example.myapplication.presentation.screens

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            navController.navigate("camera")
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Сделать фото")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            galleryLauncher.launch("image/*")
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Выбрать из галереи")
        }
    }
}