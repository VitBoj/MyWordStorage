package com.example.mywordstorage.presentation.components.buttons

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.mywordstorage.presentation.ui.theme.AppTheme
import com.example.mywordstorage.utils.ICON_BUTTON_SIZE

@Composable
fun ThemeToggleButton(onClick:()->Unit, isDark:Boolean) {
    IconButton(onClick =  onClick) {
        Icon(
            imageVector = if (!isDark) Icons.Default.DarkMode else Icons.Default.LightMode,
            contentDescription = "theme toggle button",
            tint = AppTheme.colors.textColor,
            modifier = Modifier.size(ICON_BUTTON_SIZE)
        )
    }
}
