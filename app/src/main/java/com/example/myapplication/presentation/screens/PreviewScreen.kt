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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myapplication.presentation.components.CustomButton
import com.example.myapplication.presentation.viewmodel.MainViewModel
import com.example.myapplication.ui.theme.AppColors
import java.time.format.TextStyle
import androidx.compose.foundation.Image
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp

@Composable
fun PreviewScreen(navController: NavHostController, vm: MainViewModel) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
        {
            vm.capturedImage?.let { bitmap ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                        ),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),

                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "Preview",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(3f / 4f)
//                                    .padding(16.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )

                            Spacer(modifier = Modifier.height(32.dp))

                            ButtonWidget(
                                onClick = {
                                    vm.analyze(bitmap)
                                    navController.navigate("result")
                                },
                                text = "Распознать"
                            )

                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    CustomButton(text = "Переснять", onClick = {
                        vm.clearImage()
                        navController.popBackStack()
                    })
                    Spacer(modifier = Modifier.height(30.dp))

                }
            } ?: run {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Фото не найдено")
                }
            }
        }
    }
}


@Composable
fun ButtonWidget(
    text: String,
    onClick: () -> Unit,
) {
//    val gradient = Brush.sweepGradient(
//        0.0f to Color.Red,
//        0.1f to Color(0xFFFFA500), // Оранжевый
//        0.3f to Color.Yellow,
//        0.5f to Color.Green,
//        0.7f to Color.Blue,
//        0.9f to Color(0xFF8A2BE2),  // Фиолетовый
//        1.0f to Color.Red,
//
//        )
    Button(

        onClick = onClick,
        modifier = Modifier
            .height(70.dp)
            .background(Color.Transparent)
            .width(300.dp),

        contentPadding = PaddingValues(),

        colors = ButtonDefaults.buttonColors(
            containerColor = AppColors.color50,
        ),


        elevation = ButtonDefaults.buttonElevation(  // Тень
            defaultElevation = 8.dp,
            pressedElevation = 4.dp,
            disabledElevation = 0.dp
        ),
    ) {
//        gradient = Brush.verticalGradient(
//            0.0f to Color.Green,
//            1.0f to Color.Cyan,
//        )
        Box(
            modifier = Modifier
                .background(color = AppColors.color300)
                .fillMaxSize()
                .fillMaxWidth(),
            contentAlignment = Alignment.Center


        ) {

            Text(
                text,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
