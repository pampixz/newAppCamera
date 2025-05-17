package com.example.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.myapplication.presentation.screens.*
import com.example.myapplication.presentation.viewmodel.MainViewModel

@Composable
fun NavGraph(navController: NavHostController, vm: MainViewModel) {
    NavHost(navController, startDestination = "splash") {
        composable("home") { HomeScreen(navController) }
        composable("splash") { SplashScreen(navController) }
        composable("picker") { ImagePickerScreen(navController, vm) }
        composable("camera") { CameraScreen(navController, vm) }
        composable("result") { ResultScreen(navController, vm) }
        composable("preview") {
            PreviewScreen(navController, vm)
        }

        composable("history") { HistoryScreen(navController, vm) }
    }
}
