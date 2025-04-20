package com.example.mywordstorage.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import com.example.mywordstorage.presentation.ui.theme.AppTheme

@Composable
fun UnlearnedSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(
            modifier = Modifier.scale(0.8f),
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = AppTheme.colors.negativeButtonColor,
                uncheckedThumbColor = AppTheme.colors.positiveButtonColor,
                checkedTrackColor = AppTheme.colors.backgroundColor,
                uncheckedTrackColor = AppTheme.colors.backgroundColor,
                checkedBorderColor = AppTheme.colors.textColor,
                uncheckedBorderColor = AppTheme.colors.textColor
            ),
            thumbContent = {
                Icon(
                    imageVector = if (checked) Icons.Filled.Close else Icons.Filled.Done,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                    tint = Color.White
                )
            }
        )
    }
}