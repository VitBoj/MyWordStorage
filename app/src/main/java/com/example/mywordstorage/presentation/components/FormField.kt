package com.example.mywordstorage.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mywordstorage.presentation.ui.theme.AppTheme
import com.example.mywordstorage.presentation.ui.theme.AppTypography
import com.example.mywordstorage.presentation.ui.theme.Turquoise

@Composable
fun FormField(text: String, onValueChange: (String) -> Unit, placeholder: String) {
    TextField(
        value = text,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                text = placeholder,
                color = AppTheme.colors.textColor,
                style = AppTypography.bodySmall
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp)),
        colors = TextFieldDefaults.colors
            (
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            focusedContainerColor = AppTheme.colors.formFocusedColor,
            unfocusedContainerColor = AppTheme.colors.formColor,
            focusedTextColor = AppTheme.colors.textColor,
            unfocusedTextColor = AppTheme.colors.textColor,
            cursorColor = Turquoise
        ),
        textStyle = AppTypography.bodySmall,
    )
}