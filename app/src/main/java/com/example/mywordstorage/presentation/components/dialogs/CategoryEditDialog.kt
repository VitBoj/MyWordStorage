package com.example.mywordstorage.presentation.components.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mywordstorage.data.local.entities.CategoryEntity
import com.example.mywordstorage.presentation.components.FormField
import com.example.mywordstorage.presentation.ui.theme.AppTheme

@Composable
fun CategoryEditDialog(
    category: CategoryEntity,
    onDismiss: () -> Unit,
    onDelete: () -> Unit,
    onEdit: (String) -> Unit
) {
    var categoryNewName by remember { mutableStateOf(category.name) }
    AlertDialog(
        containerColor = AppTheme.colors.backgroundColor,
        onDismissRequest = onDismiss,
        title = { Text(text = "Edit category", color = AppTheme.colors.textColor) },
        text = {
            FormField(
                text = categoryNewName,
                onValueChange = { categoryNewName = it },
                placeholder = "Category Name",
            )
        },
        confirmButton = {
            val isCategoryFieldEmpty = categoryNewName.isBlank()
            TextButton(
                onClick = {
                    if (!isCategoryFieldEmpty) {
                        onEdit(categoryNewName.trim())
                        onDismiss()
                    }
                },
                enabled = !isCategoryFieldEmpty
            ) {
                Text(
                    text = "Edit",
                    color = if (!isCategoryFieldEmpty) AppTheme.colors.positiveButtonColor
                    else Color.Gray
                )
            }
        },
        dismissButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(onClick = onDismiss) {
                    Text("Back", color = AppTheme.colors.textColor)
                }
                TextButton(onClick = onDelete) {
                    Text("Delete", color = AppTheme.colors.negativeButtonColor)
                }
            }
        }
    )
}
