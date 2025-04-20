package com.example.mywordstorage.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChecklistRtl
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mywordstorage.presentation.ui.theme.AppTheme
import com.example.mywordstorage.presentation.ui.theme.Red

@Composable
fun SelectMenu(
    selectedCount: Int,
    onDelete: () -> Unit,
    onClear: () -> Unit,
    onSelectAll: () -> Unit,
    onCopy: () -> Unit,
    onClose: () -> Unit
) {
    var isAllSelected by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            "$selectedCount item(s)",
            color = AppTheme.colors.textColor,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = { onDelete() }) {
            Icon(
                imageVector = Icons.Default.DeleteOutline,
                contentDescription = "delete",
                tint = Red
            )
        }
        IconButton(onClick = {
            if (isAllSelected) onClear() else onSelectAll()
            isAllSelected = !isAllSelected
        }) {
            Icon(
                imageVector = Icons.Default.ChecklistRtl,
                contentDescription = "select all",
                tint = AppTheme.colors.textColor
            )
        }
        IconButton(onClick = { onCopy() }) {
            Icon(
                imageVector = Icons.Default.ContentCopy,
                contentDescription = "copy to clipboard",
                tint = AppTheme.colors.textColor
            )
        }
        IconButton(onClick = { onClose() }) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "close menu",
                tint = AppTheme.colors.textColor
            )
        }
    }
}


