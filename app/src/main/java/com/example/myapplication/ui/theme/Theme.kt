package com.example.myapplication.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

private val DarkColorScheme = darkColorScheme(
    onBackground = AppColors.color50,
    background = AppColors.color50,

    primary = AppColors.color50,
    primaryContainer = AppColors.color50,
    secondary = Color.Gray.copy(alpha = 0.2f), //Цвет для card
//    tertiary = Color(0xFF018786),
    surface = AppColors.color50,
    onSurface = AppColors.color50,
    onPrimary = Color.White, //Для текста элементов в списке
//    onSecondary = Color.Black,
//    error = Color(0xFFB00020)


)

private val LightColorScheme = lightColorScheme(
    onBackground = AppColors.color50,
    background = AppColors.color50,

    primary = AppColors.color50,
    primaryContainer = AppColors.color50,
    secondary = Color.Gray.copy(alpha = 0.2f), //Цвет для card
//    tertiary = Color(0xFF018786),
    surface = AppColors.color50,
    onSurface = AppColors.color50,
    onPrimary = Color.White, //Для текста элементов в списке
//    onSecondary = Color.Black,
//    error = Color(0xFFB00020)


)


val UbuntuFontFamily = FontFamily(
    Font(R.font.ubuntu_regular, FontWeight.Normal),
    Font(R.font.ubuntu_bold, FontWeight.Bold),
    Font(R.font.ubuntu_medium, FontWeight.Medium),
    Font(R.font.ubuntu_light, FontWeight.Light),
    Font(R.font.ubuntu_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.ubuntu_bold_italic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.ubuntu_medium_italic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.ubuntu_light_italic, FontWeight.Light, FontStyle.Italic)
)


val AppTypography = Typography(

    displayLarge = TextStyle(
        fontFamily = UbuntuFontFamily,
        fontSize = 32.sp,
        color = AppColors.colorText.copy(alpha = 0.85f),
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    ),
    bodyLarge = TextStyle(
        fontFamily = UbuntuFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    //Заголовок в Box истории
    labelLarge = TextStyle(
        color = Color.White,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    labelMedium = TextStyle(
        color = AppColors.colorText,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    //Для элементов в списке результатов и исптории
    labelSmall = TextStyle(
        color = Color.White,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    )
)


@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
