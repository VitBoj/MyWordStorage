package com.example.mywordstorage.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val LocalAppColors = staticCompositionLocalOf {
    AppColors(
        backgroundColor = Color.White,
        formColor = LightTurquoise,
        formFocusedColor = LightOrange,
        textColor = Color.DarkGray,
        positiveButtonColor = Turquoise,
        negativeButtonColor = Red,
        menuColor = Orange,
    )
}

private val LightAppColors = AppColors(
    backgroundColor = Color.White,
    formColor = LightTurquoise,
    formFocusedColor = LightOrange,
    textColor = Color.DarkGray,
    positiveButtonColor = Turquoise,
    negativeButtonColor = Red,
    menuColor = LightOrange,
)

private val DarkAppColors = AppColors(
    backgroundColor = DarkBackground,
    formColor = Color.DarkGray,
    formFocusedColor = Orange,
    textColor = Color.White,
    positiveButtonColor = Turquoise,
    negativeButtonColor = Red,
    menuColor = LightOrange
)

object AppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current
}

@Composable
fun MyWordStorageTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val appColors = if (darkTheme) DarkAppColors else LightAppColors
    val materialColorScheme = if (darkTheme) darkColorScheme() else lightColorScheme()

    CompositionLocalProvider(LocalAppColors provides appColors) {
        MaterialTheme(
            colorScheme = materialColorScheme,
            typography = AppTypography,
            content = content
        )
    }
}